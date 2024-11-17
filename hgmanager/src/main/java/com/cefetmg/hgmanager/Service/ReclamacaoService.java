package com.cefetmg.hgmanager.Service;


import com.cefetmg.hgmanager.Model.Reclamacao;
import com.cefetmg.hgmanager.Model.Reserva;
import com.cefetmg.hgmanager.Model.Resposta;
import com.cefetmg.hgmanager.Model.Usuario;
import com.cefetmg.hgmanager.Repository.ReclamacaoRepository;
import com.cefetmg.hgmanager.Repository.ReservaRepository;
import com.cefetmg.hgmanager.Repository.RespostaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReclamacaoService {

    @Autowired
    private ReclamacaoRepository repository;


    public Reclamacao inserir(Reclamacao reclamacao) {
        try {
            repository.save(reclamacao);
            return reclamacao;
        }catch(Exception e){
            throw new NullPointerException();
        }
    }


    public boolean deletar(Reclamacao reclamacao) {
        try {
            repository.delete(reclamacao);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean deletarPorID(Long Id) {
        try {
            repository.deleteById(Id);//ta dando erro pelo tipo Long, mas o certo é ser Long mesmo
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Reclamacao atualizar(Reclamacao reclamacao) {
        return repository.save(reclamacao);
    }


    public boolean encontrarPorID(Long id) {
        return repository.existsById(id);//ta dando erro pelo tipo Long, mas o certo é ser Long mesmo tbm
    }

    public List<Reclamacao> listar() {
        try{
            return repository.findAll();
        }catch(Exception e){
            throw new NullPointerException();
        }
    }

}
