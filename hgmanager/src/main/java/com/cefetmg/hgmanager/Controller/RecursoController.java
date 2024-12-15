
package com.cefetmg.hgmanager.Controller;

import com.cefetmg.hgmanager.Model.Departamento;
import com.cefetmg.hgmanager.Model.Enum.Estado;
import com.cefetmg.hgmanager.Model.Recurso;
import com.cefetmg.hgmanager.Model.Usuario;
import com.cefetmg.hgmanager.Service.*;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Controller
public class RecursoController {
    private static final int PAGE_SIZE = 12;

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


    @GetMapping("/solicitar_emprestimo")
    public String carregarRecursos(Model model, HttpSession session) {
        List<Recurso> recursos = service.listarPorDisponibilidade();
        model.addAttribute("recursos", recursos);
        hService.setAttributes(model, session);

        return "solicitar_emprestimo";
    }

    @PostMapping("/recurso/cadastro_recurso")
    public ResponseEntity<String> CadastroRecurso(@RequestBody Recurso recurso) {

        try{
            service.inserirRecurso(recurso);
            return ResponseEntity
                    .ok("Recurso cadastrado com sucesso!");
        }catch(Exception e){
            return ResponseEntity
                    .badRequest()
                    .body("Erro: não foi possível cadastrar o recurso.");
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
    public ResponseEntity<String> ResgatarRecurso(@PathVariable Departamento departamento, Pageable pageable){
        try{
            service.listarPorDepartamento(departamento, pageable);
            return ResponseEntity.ok("");
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("Recurso/resgatar_recurso")
    public ResponseEntity<Page<Recurso>> ResgatarRecurso(HttpSession session, @RequestParam(defaultValue = "1") int page){

        try{
            System.out.println("entrou no resgatar recurso");
            return ResponseEntity.ok(listaRecursoDepartamento(session, page));
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

    public Page<Recurso> listaRecursoDepartamento(HttpSession session, int page){
        Usuario usuario = usuarioService.retrieveValidatedUser(session);
        Departamento dep = departamentoService.encontrarPorIdUsuario(usuario.getId());

        int paginas = recursoService.paginas(PAGE_SIZE, dep);
        Pageable pageable = PageRequest.of(page - 1, PAGE_SIZE);
        return recursoService.listarPorDepartamento(dep,pageable);
    }

    @GetMapping("/cadastra_recurso")
    public ModelAndView helloWorld(ModelMap model, HttpSession session) {
        hService.setAttributes(model, session);

        return new ModelAndView("CadastrarRecurso");
    }
    @GetMapping("/lista_recurso")
    public ModelAndView listar(ModelMap model, HttpSession session){
        hService.setAttributes(model, session);

        return new ModelAndView("ListaRecurso",model);
    }
}
