package com.cefetmg.hgmanager.Service;

import com.cefetmg.hgmanager.Repository.DepartamentoRepository;
import com.cefetmg.hgmanager.Repository.RespostaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RespostaService {
    @Autowired
    private RespostaRepository respostaRepository;

    public boolean verificar(){
        return respostaRepository.existsById(1);
    }
}
