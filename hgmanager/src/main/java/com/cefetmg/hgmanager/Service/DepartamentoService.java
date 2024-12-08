package com.cefetmg.hgmanager.Service;


import com.cefetmg.hgmanager.Model.Departamento;
import com.cefetmg.hgmanager.Repository.DepartamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
