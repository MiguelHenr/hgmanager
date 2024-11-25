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
import com.cefetmg.hgmanager.Model.Enum.Status;
import com.cefetmg.hgmanager.Service.ReservaService;

@Controller
public class ReservaController {
    @Autowired
    private ReservaService reservaService;

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

    private void setUp(Model model) {
        model.addAttribute("waiting", Status.AGUARDANDO);
        model.addAttribute("requests", reservaService.listarTodas());
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