package com.cefetmg.hgmanager.Controller;

import com.cefetmg.hgmanager.Model.Departamento;
import com.cefetmg.hgmanager.Model.Enum.Estado;
import com.cefetmg.hgmanager.Model.Recurso;
import com.cefetmg.hgmanager.Service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Controller
public class TestController {

    @Autowired
    private TestService service;

    @GetMapping("/CadastraRec")
    public ModelAndView helloWorld() {
        return new ModelAndView("CadastrarRecurso");
    }
    @GetMapping("/ListaRec")
    public ModelAndView listar(){
        return new ModelAndView("ListaRecurso");
    }
}
