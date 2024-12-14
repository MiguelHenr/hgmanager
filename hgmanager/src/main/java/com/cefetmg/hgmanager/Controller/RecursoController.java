
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
import org.springframework.ui.ModelMap;
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
    @Autowired
    private RecursoService recursoService;


    @GetMapping("/Recurso/solicitar_emprestimo")
    public String carregarRecursos(Model model, HttpSession session) {
        List<Recurso> recursos = service.listarPorDisponibilidade();
        model.addAttribute("recursos", recursos);
        hService.setAttributes(model, session);

        return "solicitar_emprestimo";
    }

    @PostMapping("/recurso/cadastro_recurso")
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


    @DeleteMapping("/Recurso/deletar_recurso/{id}")
    public ResponseEntity<String> deletarRecurso(@PathVariable Long id) {
        try{
            service.apagarRecurso(id);
            return ResponseEntity.ok("deletado com sucesso");
        } catch (Exception e) {
            return  ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PutMapping("Recurso/atualizar_recurso/{id}/{estado}")
    public ResponseEntity<String> AtualizarRecurso(@PathVariable Long id, @PathVariable String estado) {
        try{
            service.atualizarEstado(id, Estado.valueOf(estado));
            return ResponseEntity.ok("");
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("Recurso/resgatar_recurso/{id}")
    public ResponseEntity<String> ResgatarRecurso(@PathVariable Long id){
        try{
            service.encontrarRecursoPorID(id);
            return ResponseEntity.ok("");
        }catch(Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("Recurso/resgatar_recurso/{departamento}")
    public ResponseEntity<String> ResgatarRecurso(@PathVariable Departamento departamento){
        try{
            service.listarPorDepartamento(departamento);
            return ResponseEntity.ok("");
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("Recurso/resgatar_recurso")
    public ResponseEntity<List<Recurso>> ResgatarRecurso(HttpSession session){

        try{
            System.out.println("entrou no resgatar recurso");
            return ResponseEntity.ok(listaRecursoDepartamento(session));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("Departamento/listar_departamento")
    public ResponseEntity<List<Departamento>> listarDepartamento(){
        try{
            return ResponseEntity.ok(departamentoService.listar());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    public List<Recurso> listaRecursoDepartamento(HttpSession session){
        Usuario usuario = usuarioService.retrieveValidatedUser(session);
        Departamento dep = departamentoService.encontrarPorIdUsuario(usuario.getId());
        return recursoService.listarPorDepartamento(dep);
    }

    @GetMapping("/cadastra_recurso")
    public ModelAndView helloWorld(ModelMap model, HttpSession session) {
        hService.setAttributes(model, session);

        return new ModelAndView("CadastrarRecurso");
    }
    @GetMapping("/Lista_recurso")
    public ModelAndView listar(ModelMap model, HttpSession session){
        hService.setAttributes(model, session);

        return new ModelAndView("ListaRecurso",model);
    }
}
