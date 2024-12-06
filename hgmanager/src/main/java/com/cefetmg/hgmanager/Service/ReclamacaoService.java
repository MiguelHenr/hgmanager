package com.cefetmg.hgmanager.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cefetmg.hgmanager.Model.Reclamacao;
import com.cefetmg.hgmanager.Model.Usuario;
import com.cefetmg.hgmanager.Repository.ReclamacaoRepository;

@Service
public class ReclamacaoService {
    @Autowired
    private ReclamacaoRepository repository;

    public List<Reclamacao> listarTodas() {
        return repository.findAllOrdered();
    }

    public Reclamacao encontrar(long id) {
        return repository.findById(id).get();
    }
}