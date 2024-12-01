package com.cefetmg.hgmanager.Controller;

import com.cefetmg.hgmanager.Model.Recurso;
import com.cefetmg.hgmanager.Service.RecursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class RecursoController {
    @Autowired
    private RecursoService recursoService;

    @GetMapping("/solicitar_emprestimo")
    public String carregarRecursos(Model model) {
        List<Recurso> recursos = recursoService.listarPorDisponibilidade();
        model.addAttribute("recursos", recursos);

        return "solicitar_emprestimo";
    }
}
