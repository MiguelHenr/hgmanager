package com.cefetmg.hgmanager.Controller;

import com.cefetmg.hgmanager.Model.Departamento;
import com.cefetmg.hgmanager.Model.Recurso;
import com.cefetmg.hgmanager.Service.DepartamentoService;
import com.cefetmg.hgmanager.Service.RecursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {

    @Autowired
    private DepartamentoService dao;

    @Autowired
    private RecursoService recursoService;

    @GetMapping("/")
    public String helloWorld() {
        Departamento departamento = new Departamento();
        departamento.setCampus("CII");
        departamento.setNome("CEF");
        departamento.setEmail("cefetmg@gmail.com");
        departamento.setTelefone("319123128");

        dao.inserir(departamento);

        return dao.recuperarTodos().toString();
    }

    @GetMapping("/cadastroDepartamento")
    public String CadastroDepartamento() {
        Departamento departamento = new Departamento();
        departamento.setCampus("CII");
        departamento.setNome("CEF");
        departamento.setEmail("cefetmg@gmail.com");
        departamento.setTelefone("319123128");

        dao.inserir(departamento);

        return dao.recuperarTodos().toString();
    }

    @PostMapping("/Recurso/cadastroRecurso")
    public ResponseEntity<String> CadastroRecurso(@RequestBody Recurso recurso) {

        if(recursoService.inserir(recurso) == null) {
            System.out.println("retornar erro aqui(não sei qual");
            return (ResponseEntity<String>) ResponseEntity.badRequest();
        }else {
            System.out.println("deu certo");
            return (ResponseEntity<String>) ResponseEntity.ok();
        }

    }

    @DeleteMapping("/Recurso/deletarRecurso/{id}")
    public ResponseEntity<String> deletarRecurso(@PathVariable Long id) {
        try{
            recursoService.encontrarPorID(id);
            return (ResponseEntity<String>) ResponseEntity.ok();
        } catch (Exception e) {
            return (ResponseEntity<String>) ResponseEntity.badRequest();
        }

    }

    @DeleteMapping("Recurso/deletarRecurso")
    public ResponseEntity<String> deletarRecurso(@RequestBody Recurso recurso) {
        try{
            recursoService.deletar(recurso);
            return (ResponseEntity<String>) ResponseEntity.ok();
        }catch (Exception e) {
            return (ResponseEntity<String>) ResponseEntity.badRequest();
        }
    }

    @PutMapping("Recurso/AtualizarRecurso")
    public ResponseEntity<String> AtualizarRecurso(@RequestBody Recurso recurso) {
        try{
            recursoService.atualizar(recurso);
            return (ResponseEntity<String>) ResponseEntity.ok();
        }catch (Exception e){
            return (ResponseEntity<String>) ResponseEntity.badRequest();
        }
    }

    @GetMapping("Recurso/resgatarRecurso/{id}")
    public ResponseEntity<String> ResgatarRecurso(@PathVariable Long id){
        try{
            recursoService.encontrarPorID(id);
            return (ResponseEntity<String>) ResponseEntity.ok();
        }catch(Exception e){
            return (ResponseEntity<String>) ResponseEntity.badRequest();
        }
        }

   @GetMapping("Recurso/resgatarRecurso/{departamento}")
   public ResponseEntity<String> ResgatarRecurso(@PathVariable Departamento departamento){
        try{
            recursoService.listarPorDepartamento(departamento);
            return (ResponseEntity<String>) ResponseEntity.ok();
        }catch (Exception e){
            return (ResponseEntity<String>) ResponseEntity.badRequest();
        }
   }

}
