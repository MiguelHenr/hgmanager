package com.cefetmg.hgmanager.Controller;

import java.util.*;

import com.cefetmg.hgmanager.Model.Recurso;
import com.cefetmg.hgmanager.Model.Usuario;
import com.cefetmg.hgmanager.Repository.UsuarioRepository;
import com.cefetmg.hgmanager.Service.RecursoService;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.cefetmg.hgmanager.Model.Reserva;
import com.cefetmg.hgmanager.Model.Enum.Status;
import com.cefetmg.hgmanager.Service.ReservaService;

@Controller
public class ReservaController {
    @Autowired
    private ReservaService reservaService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RecursoService recursoService;

    @GetMapping("solicitacoes")
    public String getSol(Model model) {
        setUp(model);

        return "solicitacoes";
    }

    @PostMapping("requests")
    public String getRequests(Model model) {
        setUp(model);

        return "solicitacoes :: reqs";
    }

    @PostMapping("update-sol")
    public ResponseEntity<?> update(@RequestBody Map<String, String> params) {
        long id = Long.parseLong(params.get("id"));
        String action = params.get("action");

        if (!reservaService.encontrar(id))
            return ResponseEntity.status(400).body("Bad request: " + id);
        Reserva res = reservaService.resgatarPorId(id);

        if (action.equalsIgnoreCase("approve"))
            approve(res);
        else
            reject(res);

        return ResponseEntity.ok().body("Solicitação atualizada com sucesso");
    }

    @PostMapping("/confirmar_emprestimo")
    public ResponseEntity<Boolean> confirmarEmprestimo(@RequestBody Map<String, String> bodyRetorno) {
        try{
            Long idRecurso = Long.parseLong(bodyRetorno.get("idRecurso"));
            Long idProfessor = Long.parseLong(bodyRetorno.get("idProfessor"));

            // Retornando datas de inicio e de fim do empréstimo, respectivamente
            Pair<Calendar, Calendar> calendariosInicioFimEmprestimo = reservaInicioFim(bodyRetorno);

            Reserva reservaModel = new Reserva();

            // Definindo data de inicio do empréstimo
            Date dataEmprestimo = calendariosInicioFimEmprestimo.a.getTime();
            reservaModel.setInicio(dataEmprestimo);
            System.out.println("Parei Inicio");

            // Definindo data de fim do empréstimo
            dataEmprestimo = calendariosInicioFimEmprestimo.b.getTime();
            reservaModel.setFim(dataEmprestimo);

            // Definindo recurso
            Optional<Recurso> recurso = recursoService.encontrarPorID(idRecurso);
            reservaModel.setRecurso(recurso.get());

            // Definindo professor
            Optional<Usuario> usuario = usuarioRepository.findById(idProfessor);
            reservaModel.setProfessor(usuario.get());

            // Definindo status da reserva
            estadoReserva(idProfessor, idRecurso, reservaModel);

            // Inserindo reserva no banco de dados
            reservaService.inserir(reservaModel);

            return ResponseEntity.ok(true);
        } catch (NullPointerException e) {
            return ResponseEntity.ok(false);
        }
    }

    @GetMapping("/recuperar_horarios_recurso")
    @ResponseBody
    public List<Object[]> recuperarHorariosReservadosRecurso(@RequestParam("idRecurso") String idRecursoString, Model model) {
        Long idRecursoLong = Long.parseLong(idRecursoString);

        List<Object[]> test = reservaService.encontrarHorarioReservaPorRecurso(idRecursoLong);

        for (int i = 0; i < test.size(); i++) {
            System.out.println("Inicio: " + test.get(i)[0]);
            System.out.println("Fim: " + test.get(i)[1]);
        }

        return test;
    }

    private void setUp(Model model) {
        model.addAttribute("waiting", Status.AGUARDANDO);
        model.addAttribute("requests", reservaService.listarTodas());
    }

    private void approve(Reserva reserva) {
        reserva.setStatus(Status.APROVADA);

        reservaService.atualizar(reserva);
    }

    private void reject(Reserva reserva) {
        reserva.setStatus(Status.RECUSADA);

        reservaService.atualizar(reserva);
    }

    private Pair<Calendar, Calendar> reservaInicioFim(Map<String, String> bodyRetorno){
        String dia = bodyRetorno.get("dia").split("-")[0].split("/")[0];
        String mes = bodyRetorno.get("dia").split("-")[0].split("/")[1];
        String ano = bodyRetorno.get("dia").split("-")[0].split("/")[2];

        String horasInicio = bodyRetorno.get("horario").split("-")[0].split(":")[0];
        String minutosInicio = bodyRetorno.get("horario").split("-")[0].split(":")[1];

        String horasFinal = bodyRetorno.get("horario").split("-")[1].split(":")[0];
        String minutosFinal = bodyRetorno.get("horario").split("-")[1].split(":")[1];

        Calendar calendarioDataInicio = Calendar.getInstance();
        calendarioDataInicio.set(Calendar.HOUR_OF_DAY, Integer.parseInt(horasInicio));
        calendarioDataInicio.set(Calendar.MINUTE, Integer.parseInt(minutosInicio));
        calendarioDataInicio.set(Calendar.SECOND, 0);
        calendarioDataInicio.set(Calendar.MILLISECOND, 0);
        calendarioDataInicio.set(Calendar.MONTH, Integer.parseInt(mes));
        calendarioDataInicio.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dia));
        calendarioDataInicio.set(Calendar.YEAR, Integer.parseInt(ano));

        Calendar calendarioDataFim = (Calendar) calendarioDataInicio.clone();
        calendarioDataFim.set(Calendar.HOUR_OF_DAY, Integer.parseInt(horasFinal));
        calendarioDataFim.set(Calendar.MINUTE, Integer.parseInt(minutosFinal));

        return new Pair<>(calendarioDataInicio, calendarioDataFim);
    }

    private void estadoReserva(Long idProfessor, Long idRecurso, Reserva reserva){
        if(recursoService.usuarioMesmoDepartamentoRecurso(idProfessor, idRecurso)){
            approve(reserva);
        }
        reserva.setStatus(Status.AGUARDANDO);
    }
}