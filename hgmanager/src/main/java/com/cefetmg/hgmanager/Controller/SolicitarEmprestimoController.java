package com.cefetmg.hgmanager.Controller;

import com.cefetmg.hgmanager.Model.Recurso;
import com.cefetmg.hgmanager.Service.RecursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Controller
public class SolicitarEmprestimoController {
    @Autowired
    private RecursoService recursoService;

    @GetMapping("/solicitar_emprestimo")
    public String carregarRecursos(Model model) {
        List<Recurso> recursos = recursoService.listarPorDisponibilidade();
        model.addAttribute("recursos", recursos);

        return "solicitar_emprestimo";
    }
}
