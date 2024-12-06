package com.cefetmg.hgmanager.Controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cefetmg.hgmanager.Model.Reclamacao;
import com.cefetmg.hgmanager.Model.Resposta;
import com.cefetmg.hgmanager.Model.Usuario;
import com.cefetmg.hgmanager.Service.LoginService;
import com.cefetmg.hgmanager.Service.ReclamacaoService;
import com.cefetmg.hgmanager.Service.RespostaService;
import com.cefetmg.hgmanager.Service.TestService;

@Controller
public class ReclamacaoController {
    @Autowired
    private ReclamacaoService reclamacaoService;

    @Autowired
    private RespostaService respostaService;

    @Autowired
    private LoginService usuarioService;

    @GetMapping("reclamacoes")
    public String getComplaints(Model model) {
        setUp(model);

        return "reclamacoes";
    }

    @PostMapping("responder")
    public String responder(@RequestParam Long rid, 
                        @RequestParam String comentario, Model model){
        Reclamacao rec = reclamacaoService.encontrar(rid);
        //Usuario usuario = usuarioService.encontrar(7);
        Resposta ans = new Resposta();

        ans.setReclamacao(rec);
        ans.setData(new Date());
        ans.setComentario(comentario);
        //ans.setUsuario(usuario);

        respostaService.inserirResposta(ans);
        
        setUp(model);
        return "reclamacoes";
    }

    private void setUp(Model model) {
        model.addAttribute("complaints", reclamacaoService.listarTodas());
    }
}