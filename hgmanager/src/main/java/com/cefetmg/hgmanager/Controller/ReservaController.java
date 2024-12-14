package com.cefetmg.hgmanager.Controller;

import java.util.*;

import com.cefetmg.hgmanager.Model.Departamento;
import com.cefetmg.hgmanager.Model.Recurso;
import com.cefetmg.hgmanager.Model.Usuario;
import com.cefetmg.hgmanager.Repository.UsuarioRepository;
import com.cefetmg.hgmanager.Service.HeaderService;
import com.cefetmg.hgmanager.Service.RecursoService;
import com.cefetmg.hgmanager.Service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.cefetmg.hgmanager.Model.Reserva;
import com.cefetmg.hgmanager.Model.Enum.Cargo;
import com.cefetmg.hgmanager.Model.Enum.Status;
import com.cefetmg.hgmanager.Service.ReservaService;

@Controller
public class ReservaController {
    private static final int PAGE_SIZE = 12;

    @Autowired
    private ReservaService reservaService;

    @Autowired
    private UsuarioService userService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RecursoService recursoService;

    @Autowired
    private UsuarioService validationService;

    @Autowired
    private HeaderService hService;

    @GetMapping("solicitacoes")
    public String getSol(Model model, HttpSession session) {
        Usuario usuario = userService.retrieveValidatedUser(session);

        if (usuario.getTipoUsuario() == Cargo.PROFESSOR)
            return getMySol(model,session);

        model.addAttribute("mine", false);
        model.addAttribute("waiting", Status.AGUARDANDO);
        model.addAttribute("pages", reservaService.paginas(PAGE_SIZE,usuario.getDepartamento()));
        hService.setAttributes(model, session);

        return "solicitacoes";
    }

    @GetMapping("eu/solicitacoes")
    public String getMySol(Model model, HttpSession session) {
        Usuario usuario = userService.retrieveValidatedUser(session);

        model.addAttribute("mine", true);
        model.addAttribute("waiting", Status.AGUARDANDO);
        model.addAttribute("pages", reservaService.paginas(PAGE_SIZE,usuario));
        hService.setAttributes(model, session);

        return "solicitacoes";
    }

    @PostMapping({"my-requests","eu/my-requests"})
    public String getMyRequests(Model model, HttpSession session, @RequestParam(defaultValue = "1") int page) {
        Usuario usuario = userService.retrieveValidatedUser(session);
        int paginas = reservaService.paginas(PAGE_SIZE,usuario);
        Pageable pageable = PageRequest.of(page - 1, PAGE_SIZE);

        model.addAttribute("mine", true);
        model.addAttribute("waiting", Status.AGUARDANDO);
        model.addAttribute("pages", paginas);
        model.addAttribute("requests", reservaService.listarPorUsuario(usuario,pageable));
        
        hService.setAttributes(model, session);

        return "frag/requests";
    }

    @PostMapping("requests")
    public String getRequests(Model model, HttpSession session, @RequestParam(defaultValue = "1") int page) {
        Departamento dpto = userService.retrieveValidatedUser(session).getDepartamento();
        int paginas = reservaService.paginas(PAGE_SIZE,dpto);
        Pageable pageable = PageRequest.of(page - 1, PAGE_SIZE);

        model.addAttribute("mine", false);
        model.addAttribute("waiting", Status.AGUARDANDO);
        model.addAttribute("pages", paginas);
        model.addAttribute("requests", reservaService.listarPorDepartamento(dpto,pageable));

        hService.setAttributes(model, session);

        return "frag/requests";
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
    public ResponseEntity<Boolean> confirmarEmprestimo(@RequestBody Map<String, String> bodyRetorno, HttpSession session) {
        try{
            Usuario usuarioSession = validationService.retrieveValidatedUser(session);

            Long idRecurso = Long.parseLong(bodyRetorno.get("idRecurso"));
            Long idProfessor = usuarioSession.getId();

            // Retornando datas de inicio e de fim do empréstimo, respectivamente
            Pair<Calendar, Calendar> calendariosInicioFimEmprestimo = reservaInicioFim(bodyRetorno);

            Reserva reservaModel = new Reserva();

            // Definindo data de inicio do empréstimo
            Date dataEmprestimo = calendariosInicioFimEmprestimo.a.getTime();
            reservaModel.setInicio(dataEmprestimo);

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
    public List<String> recuperarHorariosReservadosRecurso(@RequestParam("idRecurso") String idRecursoString, Model model) {
        Long idRecursoLong = Long.parseLong(idRecursoString);

        return reservaService.encontrarHorarioReservaPorRecurso(idRecursoLong);
    }

    private void approve(Reserva reserva) {
        reserva.setStatus(Status.APROVADA);

        reservaService.atualizar(reserva);
    }

    private void reject(Reserva reserva) {
        reserva.setStatus(Status.REJEITADA);

        reservaService.atualizar(reserva);
    }

    private Pair<Calendar, Calendar> reservaInicioFim(Map<String, String> bodyRetorno){
        String[] retornoDias = bodyRetorno.get("dia").split("-");
        String[] retornohoras = bodyRetorno.get("horario").split("-");

        String dia = retornoDias[0].split("/")[0];
        String mes = retornoDias[0].split("/")[1];
        String ano = retornoDias[0].split("/")[2];

        String horasInicio = retornohoras[0].split(":")[0];
        String minutosInicio = retornohoras[0].split(":")[1];

        String horasFinal = retornohoras[1].split(":")[0];
        String minutosFinal = retornohoras[1].split(":")[1];

        Calendar calendarioDataInicio = Calendar.getInstance();
        calendarioDataInicio.set(Calendar.HOUR_OF_DAY, Integer.parseInt(horasInicio));
        calendarioDataInicio.set(Calendar.MINUTE, Integer.parseInt(minutosInicio));
        calendarioDataInicio.set(Calendar.SECOND, 0);
        calendarioDataInicio.set(Calendar.MILLISECOND, 0);
        calendarioDataInicio.set(Calendar.MONTH, Integer.parseInt(mes) - 1);
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