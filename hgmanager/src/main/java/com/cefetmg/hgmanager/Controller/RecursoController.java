
package com.cefetmg.hgmanager.Controller;

import com.cefetmg.hgmanager.Model.Departamento;
import com.cefetmg.hgmanager.Model.Enum.Estado;
import com.cefetmg.hgmanager.Model.Recurso;
import com.cefetmg.hgmanager.Model.Usuario;
import com.cefetmg.hgmanager.Service.*;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Controller
public class RecursoController {

    @Autowired
    private RecursoService service;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private DepartamentoService departamentoService;

    @Autowired
    private HeaderService hService;

    @GetMapping("/solicitar_emprestimo")
    public String carregarRecursos(Model model, HttpSession session) {
        List<Recurso> recursos = service.listarPorDisponibilidade();
        model.addAttribute("recursos", recursos);
        hService.setAttributes(model, session);

        return "solicitar_emprestimo";
    }

    @PostMapping("/Recurso/cadastroRecurso")
    public ResponseEntity<String> CadastroRecurso(@RequestBody Recurso recurso) {

        if (service.inserirRecurso(recurso) == null) {
            return ResponseEntity
                    .badRequest()
                    .body("Erro: não foi possível cadastrar o recurso.");
        } else {
            return ResponseEntity
                    .ok("Recurso cadastrado com sucesso!");
        }
    }


    @DeleteMapping("/Recurso/deletarRecurso/{id}")
    public ResponseEntity<String> deletarRecurso(@PathVariable Long id) {
        try{
            service.deletarRecurso(id);
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
            service.listarPorDepartamento(departamento);
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
            return ResponseEntity.ok(departamentoService.listar());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
