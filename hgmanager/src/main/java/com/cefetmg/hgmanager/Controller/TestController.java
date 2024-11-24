package com.cefetmg.hgmanager.Controller;

import com.cefetmg.hgmanager.Model.Departamento;
import com.cefetmg.hgmanager.Model.Enum.Estado;
import com.cefetmg.hgmanager.Model.Recurso;
import com.cefetmg.hgmanager.Service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class TestController {

    @Autowired
    private TestService service;

    @GetMapping("/dep")
    public ModelAndView helloWorld() {
        return new ModelAndView("CadastrarRecurso");
    }

    @GetMapping("/cadastroDepartamento")
    public String CadastroDepartamento() {
        Departamento departamento = new Departamento();
        departamento.setCampus("CII");
        departamento.setNome("CEF");
        departamento.setEmail("cefetmg@gmail.com");
        departamento.setTelefone("319123128");

        if(service.inserirDepartamento(departamento) != null) {
            System.out.println("inseriu");
        }else
            System.out.println("não inseriu");
        return service.recuperarTodosDepartamentos().toString();
    }

    @PostMapping("/Recurso/cadastroRecurso")
    public ResponseEntity<String> CadastroRecurso(@RequestBody Recurso recurso) {
        System.out.println("entrou cadastroRecurso");

        if (service.inserirRecurso(recurso) == null) {
            System.out.println("Erro ao cadastrar o recurso");
            return ResponseEntity
                    .badRequest()
                    .body("Erro: não foi possível cadastrar o recurso.");
        } else {
            System.out.println("Cadastro realizado com sucesso");
            return ResponseEntity
                    .ok("Recurso cadastrado com sucesso!");
        }
    }


    @DeleteMapping("/Recurso/deletarRecurso/{id}")
    public ResponseEntity<String> deletarRecurso(@PathVariable Long id) {
        try{
            service.encontrarRecursoPorID(id);
            return (ResponseEntity<String>) ResponseEntity.ok();
        } catch (Exception e) {
            return (ResponseEntity<String>) ResponseEntity.badRequest();
        }

    }

    @DeleteMapping("Recurso/deletarRecurso")
    public ResponseEntity<String> deletarRecurso(@RequestBody Recurso recurso) {
        try{
            service.deletarRecurso(recurso);
            return (ResponseEntity<String>) ResponseEntity.ok();
        }catch (Exception e) {
            return (ResponseEntity<String>) ResponseEntity.badRequest();
        }
    }

    @PutMapping("Recurso/AtualizarRecurso")
    public ResponseEntity<String> AtualizarRecurso(@RequestBody Recurso recurso) {
        try{
            service.atualizarRecurso(recurso);
            return (ResponseEntity<String>) ResponseEntity.ok();
        }catch (Exception e){
            return (ResponseEntity<String>) ResponseEntity.badRequest();
        }
    }

    @GetMapping("Recurso/resgatarRecurso/{id}")
    public ResponseEntity<String> ResgatarRecurso(@PathVariable Long id){
        try{
            service.encontrarRecursoPorID(id);
            return (ResponseEntity<String>) ResponseEntity.ok();
        }catch(Exception e){
            return (ResponseEntity<String>) ResponseEntity.badRequest();
        }
        }

   @GetMapping("Recurso/resgatarRecurso/{departamento}")
   public ResponseEntity<String> ResgatarRecurso(@PathVariable Departamento departamento){
        try{
            service.listarRecursosPorDepartamento(departamento);
            return (ResponseEntity<String>) ResponseEntity.ok();
        }catch (Exception e){
            return (ResponseEntity<String>) ResponseEntity.badRequest();
        }
   }

}
