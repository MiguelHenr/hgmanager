package com.cefetmg.hgmanager.Controller;

import com.cefetmg.hgmanager.Model.Departamento;
import com.cefetmg.hgmanager.Service.DepartamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private DepartamentoService dao;

    @GetMapping("/")
    public String helloWorld() {
        Departamento departamento = new Departamento();
        departamento.setCampus("CII");
        departamento.setNome("CEF");
        departamento.setEmail("cefetmg@gmail.com");
        departamento.setTelefone("319123128");

        dao.inserir(departamento);

        return "Hello World";
    }
}
