package com.cefetmg.hgmanager.Service;

import java.util.Date;
import java.util.List;

import com.cefetmg.hgmanager.Model.Reserva;
import com.cefetmg.hgmanager.Repository.ReservaRepository;
import com.cefetmg.hgmanager.Repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cefetmg.hgmanager.Model.Reclamacao;
import com.cefetmg.hgmanager.Model.Usuario;
import com.cefetmg.hgmanager.Repository.ReclamacaoRepository;

@Service
public class ReclamacaoService {

    @Autowired
    private ReclamacaoRepository reclamacaoRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    public void report(String comment, Long reservaId) {

        Reserva reserva = reservaRepository.findById(reservaId).get();
        Reclamacao model = new Reclamacao(comment, new Date(), reserva);
        reclamacaoRepository.save(model);

    }

    public List<Reclamacao> listarTodas() {
        return reclamacaoRepository.findAllOrdered();
    }

    public Reclamacao encontrar(long id) {
        return reclamacaoRepository.findById(id).get();
    }
}