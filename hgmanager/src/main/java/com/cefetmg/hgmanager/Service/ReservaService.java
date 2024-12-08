package com.cefetmg.hgmanager.Service;

import java.util.List;

import com.cefetmg.hgmanager.Model.Departamento;
import com.cefetmg.hgmanager.Model.Enum.Estado;
import com.cefetmg.hgmanager.Model.Recurso;
import com.cefetmg.hgmanager.Model.Reserva;
import com.cefetmg.hgmanager.Model.Usuario;
import com.cefetmg.hgmanager.Repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cefetmg.hgmanager.Model.Reserva;
import com.cefetmg.hgmanager.Repository.ReservaRepository;
import jakarta.persistence.PersistenceException;
import java.util.List;

@Service
public class ReservaService {
    @Autowired
    private ReservaRepository repository;

    public Reserva inserir(Reserva reserva) {
        if(reserva.getFim() == null || reserva.getInicio() == null || reserva.getProfessor().getId() == null || reserva.getRecurso().getId() == null || reserva.getStatus() == null) {
            throw new NullPointerException();
        }
        return repository.save(reserva);
    }

    public List<Reserva> listarTodas() {
        return repository.findAllOrdered();
    }

    public List<Reserva> listarPorUsuario(Usuario usuario) {
        return repository.findAllByProfessorOrdered(usuario);
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

    public boolean encontrarPorID(Long id) {
        return repository.existsById(id);
    }

    public List<Reserva> listar() {
        try{
            return repository.findAll();
        }catch(Exception e){
            throw new PersistenceException();
        }
    }

    public Reserva encontrarPorRecurso(Recurso recurso) {
        try {
            return repository.findReservaByRecurso(recurso);
        }catch (Exception e){
            throw new PersistenceException();
        }
    }

    public List<Object[]> encontrarHorarioReservaPorRecurso(Long idRecurso){
        try {
            return repository.findReservaFromRecurso(idRecurso);
        }catch (Exception e){
            throw new PersistenceException();
        }
    }
}
