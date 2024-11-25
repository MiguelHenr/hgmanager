package com.cefetmg.hgmanager.Service;

import com.cefetmg.hgmanager.Model.Departamento;
import com.cefetmg.hgmanager.Model.Enum.Estado;
import com.cefetmg.hgmanager.Model.Recurso;
import com.cefetmg.hgmanager.Model.Reserva;
import com.cefetmg.hgmanager.Repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;
import java.util.List;

@Service
public class ReservaService {
    
    @Autowired
    ReservaRepository repository;

    public Reserva inserir(Reserva reserva) {
        if(reserva.getFim() == null || reserva.getIncio() == null || reserva.getProfessor().getId() == null || reserva.getRecurso().getId() == null || reserva.getStatus() == null) {
            throw new NullPointerException();
        }
        return repository.save(reserva);
    }

    public boolean deletar(Reserva reserva) {
        try {
            repository.delete(reserva);
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

    public Reserva atualizar(Reserva reserva) {
        return repository.save(reserva);
    }


    public boolean encontrarPorID(Long id) {
        return repository.existsById(id);
    }

    public List<Reserva> listar() {
        try{
            return repository.findAll();
        }catch(Exception e){
            throw new NullPointerException();
        }
    }

    public Reserva encontrarPorRecurso(Recurso recurso) {
        try {
            return repository.findReservaByRecurso(recurso);
        }catch (Exception e){
            throw new PersistenceException();
        }
    }
}
