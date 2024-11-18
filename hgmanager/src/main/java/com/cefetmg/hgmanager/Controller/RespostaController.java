package com.cefetmg.hgmanager.Controller;

import com.cefetmg.hgmanager.Model.Reclamacao;
import com.cefetmg.hgmanager.Model.Resposta;
import com.cefetmg.hgmanager.Service.RespostaService;
import com.cefetmg.hgmanager.Service.ReclamacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/resposta")
public class RespostaController {
    @Autowired
    private ReclamacaoService reclamacaoService;
    private RespostaService respostaService;
    
    @PostMapping("/{id}/responder")
    public ResponseEntity<String> responderReclamacao(@PathVariable Long id, @RequestBody Resposta resposta) {

        try {
            boolean reclamacaoExiste = reclamacaoService.encontrarPorID(id);

            if (!reclamacaoExiste) {
                return ResponseEntity.status(404).body("Reclamação não encontrada.");
            }

            Reclamacao reclamacao = new Reclamacao(); 
            reclamacao.setId(id);
            resposta.setReclamacao(reclamacao);

            respostaService.inserir(resposta);

            return ResponseEntity.ok("Reclamação respondida com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao responder a reclamação.");
        }
    }
}
