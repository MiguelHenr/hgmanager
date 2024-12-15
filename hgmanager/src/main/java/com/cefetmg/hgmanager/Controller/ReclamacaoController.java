package com.cefetmg.hgmanager.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.cefetmg.hgmanager.Model.*;
import com.cefetmg.hgmanager.Repository.ReservaRepository;
import com.cefetmg.hgmanager.Service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.cefetmg.hgmanager.Model.Enum.Cargo;
import com.cefetmg.hgmanager.Service.UsuarioService;

import jakarta.servlet.http.HttpSession;

import com.cefetmg.hgmanager.Service.ReclamacaoService;
import com.cefetmg.hgmanager.Service.RespostaService;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ReclamacaoController {

    @Autowired
    private ReservaService reservaService;

    @Autowired
    private ReclamacaoService reclamacaoService;

    @Autowired
    private RespostaService respostaService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("registrar_reclamacao")
    public String report(@RequestParam(value = "id", required = true) String idToParse, ModelMap model) {

        long id = Long.parseLong(idToParse);

        Reserva reserva = reservaService.resgatarPorId(id);

        model.addAttribute("reservaRecursoDesc", reserva.getRecurso().getDescricao());
        model.addAttribute("reservaRecursoDepartamento", reserva.getRecurso().getDepartamento().getNome());
        model.addAttribute("reservaInicio", reserva.getInicio());
        model.addAttribute("reservaFim", reserva.getFim());
        model.addAttribute("reservaStatus", reserva.getStatus());

        return "RegistrarReclamacao";
    }

    @PostMapping("registrar_reclamacao")
    public ResponseEntity<?> report(@RequestBody Map<String, String> params, Model model, HttpSession session) {

        String comment = params.get("comment");
        Long reservaId = Long.parseLong(params.get("id"));
        reclamacaoService.report(comment, reservaId);

        return ResponseEntity.ok().build();

    }

    @GetMapping("reclamacoes")
    public String getComplaints(Model model, HttpSession session) {
        setUp(model,session);

        return "reclamacoes";
    }

    @PostMapping("responder")
    public ResponseEntity<?> responder(@RequestBody Map<String, String> params, HttpSession session) {
        long id = Long.parseLong(params.get("id"));
        String comentario = params.get("ans");
        Reclamacao rec = reclamacaoService.encontrar(id);
        Usuario usuario = usuarioService.retrieveValidatedUser(session);
        Resposta ans = new Resposta();

        ans.setReclamacao(rec);
        ans.setData(new Date());
        ans.setComentario(comentario);
        ans.setUsuario(usuario);

        respostaService.inserirResposta(ans);

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy HH:mm");
        String nome = usuario.getNome();

        Map<String, Object> responseBody = Map.of(
            "nome", usuario.getNome(),
            "com", comentario,
            "data", format.format(ans.getData()),
            "foto", Map.of(
                "src", usuario.getFoto(),
                "alt", "Foto de " + nome
            )
        );
        
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    private void setUp(Model model, Usuario usuario) {
        model.addAttribute("complaints", reclamacaoService.listarTodas());
        model.addAttribute("tae", usuario.getTipoUsuario() == Cargo.TAE);
        model.addAttribute("foto", usuario.getFoto());
        model.addAttribute("nome", usuario.getNome());
    }

    private void setUp(Model model, HttpSession session) {
        Usuario usuario = usuarioService.retrieveValidatedUser(session);
        
        setUp(model,usuario);
    }
}