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

    @GetMapping("/dep")
    public ModelAndView helloWorld() {
        return new ModelAndView("ListaRecurso");
    }

    @GetMapping("/")
    public ModelAndView Index() {
        return new ModelAndView("Index");
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
            service.deletarRecurso(id);
            System.out.println(id);
            return ResponseEntity.ok("deletado com sucesso");

        } catch (Exception e) {
            return  ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PutMapping("Recurso/AtualizarRecurso/{id}/{estado}")
    public ResponseEntity<String> AtualizarRecurso(@PathVariable Long id, @PathVariable String estado) {
        try{
            service.atualizarEstado(id, Estado.valueOf(estado));
            return ResponseEntity.ok("");
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("Recurso/resgatarRecurso/{id}")
    public ResponseEntity<String> ResgatarRecurso(@PathVariable Long id){
        try{
            service.encontrarRecursoPorID(id);
            return ResponseEntity.ok("");
        }catch(Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
        }

   @GetMapping("Recurso/resgatarRecurso/{departamento}")
   public ResponseEntity<String> ResgatarRecurso(@PathVariable Departamento departamento){
        try{
            service.listarRecursosPorDepartamento(departamento);
            return ResponseEntity.ok("");
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
   }

   @GetMapping("Recurso/resgatarRecurso")
    public ResponseEntity<List<Recurso>> ResgatarRecurso(){
        try{
            System.out.println("entrou no resgatar recurso");
            return ResponseEntity.ok(service.ListarTodosRecursos());
        }catch (Exception e){
            return ResponseEntity.badRequest().body(null);
        }
   }

   @GetMapping("Departamento/listarDepartamento")
    public ResponseEntity<List<Departamento>> listarDepartamento(){
        try{
            System.out.println("entrou no listar departamento");
            return ResponseEntity.ok(service.listarDepartamentos());
        }catch (Exception e){
            return ResponseEntity.badRequest().body(null);
        }
   }


}
