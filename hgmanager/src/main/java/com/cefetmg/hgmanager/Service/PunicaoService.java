package com.cefetmg.hgmanager.Service;

import com.cefetmg.hgmanager.Model.Punicao;
import com.cefetmg.hgmanager.Model.Reclamacao;
import com.cefetmg.hgmanager.Repository.PunicaoRepository;
import com.cefetmg.hgmanager.Repository.RespostaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PunicaoService {

    @Autowired
    private PunicaoRepository repository;

    /*caso queira filtrar por um atributo especifico pode usar findBy+atributo(String atributo)
    por exemplo, filtrar departamento por nome
    ex:

    public Departamento filtrarPorNome(){
    findByNome(String nome)
    }
    */

    public Punicao inserir(Punicao punicao) {
        try {
            repository.save(punicao);
            return punicao;
        }catch(Exception e){
            throw new NullPointerException();
        }
    }


    public boolean deletar(Punicao punicao) {
        try {
            repository.delete(punicao);
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

    public Punicao atualizar(Punicao punicao) {
        return repository.save(punicao);
    }


    public boolean encontrarPorID(Long id) {
        return repository.existsById(id);//ta dando erro pelo tipo Long, mas o certo é ser Long mesmo tbm
    }

    public List<Punicao> listar() {
        try{
            return repository.findAll();
        }catch(Exception e){
            throw new NullPointerException();
        }
    }


}
