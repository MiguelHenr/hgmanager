package com.cefetmg.hgmanager.Service;

import com.cefetmg.hgmanager.Model.Reclamacao;
import com.cefetmg.hgmanager.Model.Reserva;
import com.cefetmg.hgmanager.Model.Resposta;
import com.cefetmg.hgmanager.Model.Usuario;
import com.cefetmg.hgmanager.Repository.DepartamentoRepository;
import com.cefetmg.hgmanager.Repository.ReclamacaoRepository;
import com.cefetmg.hgmanager.Repository.ReservaRepository;
import com.cefetmg.hgmanager.Repository.RespostaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class RespostaService {

    @Autowired
    private RespostaRepository respostaRepository;

    @Autowired
    private ReclamacaoRepository reclamacaoRepository;


    public boolean encontrarReclamacaoPorID(Long id) {
        return reclamacaoRepository.existsById(id);//ta dando erro pelo tipo Long, mas o certo é ser Long mesmo tbm
    }

    public Resposta inserirResposta(Resposta resposta) {
        try {
            respostaRepository.save(resposta);
            return resposta;
        }catch(Exception e){
            throw new NullPointerException();
        }
    }

}
