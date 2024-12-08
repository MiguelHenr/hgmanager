package com.cefetmg.hgmanager.Controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.cefetmg.hgmanager.Model.Reserva;
import com.cefetmg.hgmanager.Model.Usuario;
import com.cefetmg.hgmanager.Model.Enum.Cargo;
import com.cefetmg.hgmanager.Model.Enum.Status;
import com.cefetmg.hgmanager.Service.ReservaService;
import com.cefetmg.hgmanager.Service.UserValidationService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ReservaController {
    @Autowired
    private ReservaService reservaService;

    @Autowired
    private UserValidationService userService;

    @GetMapping("solicitacoes")
    public String getSol(Model model, HttpSession session) {
        Usuario usuario = userService.retrieveValidatedUser(session);

        if (usuario.getTipoUsuario() == Cargo.PROFESSOR)
            return getMySol(model,usuario);

        model.addAttribute("mine", false);

        return "solicitacoes";
    }

    @GetMapping("eu/solicitacoes")
    public String getMySol(Model model, HttpSession session) {
        Usuario usuario = userService.retrieveValidatedUser(session);

        return getMySol(model,usuario);
    }

    private String getMySol(Model model, Usuario usuario) {
        model.addAttribute("mine", true);

        return "solicitacoes";
    }

    @PostMapping("eu/my-requests")
    public String getEuMyRequests(Model model, HttpSession session) {
        return getMyRequests(model, session);
    }

    @PostMapping("my-requests")
    public String getMyRequests(Model model, HttpSession session) {
        Usuario usuario = userService.retrieveValidatedUser(session);

        model.addAttribute("mine", true);
        model.addAttribute("waiting", Status.AGUARDANDO);
        model.addAttribute("requests", reservaService.listarPorUsuario(usuario));

        return "frag/requests";
    }

    @PostMapping("requests")
    public String getRequests(Model model) {
        model.addAttribute("mine", false);
        model.addAttribute("waiting", Status.AGUARDANDO);
        model.addAttribute("requests", reservaService.listarTodas());

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

    private void approve(Reserva reserva) {
        reserva.setStatus(Status.APROVADA);

        reservaService.atualizar(reserva);
    }

    private void reject(Reserva reserva) {
        reserva.setStatus(Status.REJEITADA);

        reservaService.atualizar(reserva);
    }
}