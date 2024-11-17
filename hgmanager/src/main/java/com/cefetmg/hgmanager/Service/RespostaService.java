package com.cefetmg.hgmanager.Service;

import com.cefetmg.hgmanager.Model.Reclamacao;
import com.cefetmg.hgmanager.Model.Reserva;
import com.cefetmg.hgmanager.Model.Resposta;
import com.cefetmg.hgmanager.Model.Usuario;
import com.cefetmg.hgmanager.Repository.DepartamentoRepository;
import com.cefetmg.hgmanager.Repository.ReservaRepository;
import com.cefetmg.hgmanager.Repository.RespostaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class RespostaService {

    @Autowired
    private RespostaRepository repository;


    public Resposta inserir(Resposta resposta) {
        try {
            repository.save(resposta);
            return resposta;
        }catch(Exception e){
            throw new NullPointerException();
        }
    }


    public boolean deletar(Resposta resposta) {
        try {
            repository.delete(resposta);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean deletarPorID(Long id) {
        try {
            repository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Resposta atualizar(Resposta resposta) {
        return repository.save(resposta);
    }


    public boolean encontrarPorID(Long id) {
        return repository.existsById(id);
    }

    public List<Resposta> listar() {
        try{
            return repository.findAll();
        }catch(Exception e){
            throw new NullPointerException();
        }
    }

    public List<Resposta> listarDe(Reclamacao reclamacao) {
        try {
            return repository.findByReclamacao(reclamacao);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
}
