package com.cefetmg.hgmanager.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cefetmg.hgmanager.Model.Reserva;
import com.cefetmg.hgmanager.Repository.ReservaRepository;

@Service
public class ReservaService {
    @Autowired
    private ReservaRepository repository;
    
    public List<Reserva> listarTodas() {
        return repository.findAllOrdered();
    }

    public boolean encontrar(long id) {
        return repository.existsById(id);
    }

    public Reserva resgatarPorId(long id) {
        return repository.findById(id).get();
    }

    public Reserva atualizar(Reserva reserva) {
        return repository.save(reserva);
    }
}