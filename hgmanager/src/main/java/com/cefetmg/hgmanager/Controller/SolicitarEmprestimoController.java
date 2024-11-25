package com.cefetmg.hgmanager.Controller;

import com.cefetmg.hgmanager.Model.Enum.Status;
import com.cefetmg.hgmanager.Model.Recurso;
import com.cefetmg.hgmanager.Model.Reserva;
import com.cefetmg.hgmanager.Model.Usuario;
import com.cefetmg.hgmanager.Repository.ReservaRepository;
import com.cefetmg.hgmanager.Repository.UsuarioRepository;
import com.cefetmg.hgmanager.Service.RecursoService;
import com.cefetmg.hgmanager.Service.ReservaService;
import jakarta.persistence.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.logging.Logger;

@Controller
public class SolicitarEmprestimoController {
    @Autowired
    private RecursoService recursoService;
    @Autowired
    private ReservaService reservaService;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/solicitar_emprestimo")
    public String carregarRecursos(Model model) {
        List<Recurso> recursos = recursoService.listarPorDisponibilidade();
        model.addAttribute("recursos", recursos);

        return "solicitar_emprestimo";
    }

    @PostMapping("/emprestimo_departamento_igual")
    public ResponseEntity<Boolean> confirmarEmprestimoDepartamentoIgual(@RequestBody Map<String, String> bodyRetorno) {
        try{
            Long id = (long) Integer.parseInt(bodyRetorno.get("dia").split("-")[1]);

            String dia = bodyRetorno.get("dia").split("-")[0].split("/")[0];
            String mes = bodyRetorno.get("dia").split("-")[0].split("/")[1];
            String ano = bodyRetorno.get("dia").split("-")[0].split("/")[2];

            String horasInicio = bodyRetorno.get("horario").split("-")[0].split(":")[0];
            String minutosInicio = bodyRetorno.get("horario").split("-")[0].split(":")[1];

            String horasFinal = bodyRetorno.get("horario").split("-")[1].split(":")[0];
            String minutosFinal = bodyRetorno.get("horario").split("-")[1].split(":")[1];

            Calendar calendarioEmprestimo = Calendar.getInstance();
            calendarioEmprestimo.set(Calendar.HOUR_OF_DAY, Integer.parseInt(horasInicio));
            calendarioEmprestimo.set(Calendar.MINUTE, Integer.parseInt(minutosInicio));
            calendarioEmprestimo.set(Calendar.SECOND, 0);
            calendarioEmprestimo.set(Calendar.MILLISECOND, 0);
            calendarioEmprestimo.set(Calendar.MONTH, Integer.parseInt(mes));
            calendarioEmprestimo.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dia));
            calendarioEmprestimo.set(Calendar.YEAR, Integer.parseInt(ano));

            Reserva reserva = new Reserva();

            Date dataEmprestimo = calendarioEmprestimo.getTime();
            reserva.setIncio(dataEmprestimo);

            calendarioEmprestimo.set(Calendar.HOUR_OF_DAY, Integer.parseInt(horasFinal));
            calendarioEmprestimo.set(Calendar.MINUTE, Integer.parseInt(minutosFinal));

            dataEmprestimo = calendarioEmprestimo.getTime();
            reserva.setFim(dataEmprestimo);

            reserva.setStatus(Status.APROVADA);

            Optional<Recurso> recurso = recursoService.encontrarPorID(id);

            reserva.setRecurso(recurso.get());

            // Adicionar professor
            Usuario usuario = usuarioRepository.findByCpf("666.666.666-66");
            reserva.setProfessor(usuario);

            reservaService.inserir(reserva);
            return ResponseEntity.ok(true);
        } catch (NullPointerException e) {
            return ResponseEntity.ok(false);
        }
    }

    @PostMapping("emprestimo_departamento_diferente")
    public ResponseEntity<Boolean> confirmarEmprestimoDepartamentoDiferente(@RequestBody Map<String, String> bodyRetorno) {
        try{
            Long id = (long) Integer.parseInt(bodyRetorno.get("dia").split("-")[1]);

            String dia = bodyRetorno.get("dia").split("-")[0].split("/")[0];
            String mes = bodyRetorno.get("dia").split("-")[0].split("/")[1];
            String ano = bodyRetorno.get("dia").split("-")[0].split("/")[2];

            String horasInicio = bodyRetorno.get("horario").split("-")[0].split(":")[0];
            String minutosInicio = bodyRetorno.get("horario").split("-")[0].split(":")[1];

            String horasFinal = bodyRetorno.get("horario").split("-")[1].split(":")[0];
            String minutosFinal = bodyRetorno.get("horario").split("-")[1].split(":")[1];

            Calendar calendarioEmprestimo = Calendar.getInstance();
            calendarioEmprestimo.set(Calendar.HOUR_OF_DAY, Integer.parseInt(horasInicio));
            calendarioEmprestimo.set(Calendar.MINUTE, Integer.parseInt(minutosInicio));
            calendarioEmprestimo.set(Calendar.SECOND, 0);
            calendarioEmprestimo.set(Calendar.MILLISECOND, 0);
            calendarioEmprestimo.set(Calendar.MONTH, Integer.parseInt(mes));
            calendarioEmprestimo.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dia));
            calendarioEmprestimo.set(Calendar.YEAR, Integer.parseInt(ano));

            Reserva reserva = new Reserva();

            Date dataEmprestimo = calendarioEmprestimo.getTime();
            reserva.setIncio(dataEmprestimo);

            calendarioEmprestimo.set(Calendar.HOUR_OF_DAY, Integer.parseInt(horasFinal));
            calendarioEmprestimo.set(Calendar.MINUTE, Integer.parseInt(minutosFinal));

            dataEmprestimo = calendarioEmprestimo.getTime();
            reserva.setFim(dataEmprestimo);

            reserva.setStatus(Status.AGUARDANDO);

            Optional<Recurso> recurso = recursoService.encontrarPorID(id);

            reserva.setRecurso(recurso.get());

            // Adicionar professor
            Usuario usuario = usuarioRepository.findByCpf("666.666.666-66");
            reserva.setProfessor(usuario);

            reservaService.inserir(reserva);
            return ResponseEntity.ok(true);
        } catch (NullPointerException e) {
            return ResponseEntity.ok(false);
        }
    }
}
