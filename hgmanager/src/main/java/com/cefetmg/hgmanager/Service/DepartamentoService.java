package com.cefetmg.hgmanager.Service;


import com.cefetmg.hgmanager.Model.Departamento;
import com.cefetmg.hgmanager.Repository.DepartamentoRepository;
import jakarta.persistence.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartamentoService {

    @Autowired
    DepartamentoRepository departamentoRepository;


    public List<Departamento> listar(){
        try{
            return departamentoRepository.findAll();
        }catch(Exception e){
            System.out.println("\nmensagem de erro:"+e.getMessage()+"\n");
            e.printStackTrace();
            throw new NullPointerException();
        }
    }

    public Departamento encontrarPorIdUsuario(Long idUsuario){
        try {
            return departamentoRepository.findByIdUsuario(idUsuario);
        } catch(PersistenceException e){
            System.out.println("\nmensagem de erro:"+e.getMessage()+"\n");
            e.printStackTrace();
            throw new PersistenceException();
        }
    }
}
