package com.cefetmg.hgmanager.Service;

import java.util.List;

import com.cefetmg.hgmanager.Model.Departamento;
import com.cefetmg.hgmanager.Model.Enum.Estado;
import com.cefetmg.hgmanager.Model.Recurso;
import com.cefetmg.hgmanager.Model.Reserva;
import com.cefetmg.hgmanager.Model.Usuario;
import com.cefetmg.hgmanager.Repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import jakarta.persistence.PersistenceException;

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

    public int paginas(int pageSize) {
        long totalRequests = repository.count();  
        return (int) Math.ceil((double) totalRequests / pageSize);
    }

    public int paginas(int pageSize, Departamento dpto) {
        long totalRequests = repository.countByRecursoDepartamento(dpto);  
        return (int) Math.ceil((double) totalRequests / pageSize);
    }

    public int paginas(int pageSize, Usuario usuario) {
        long totalRequests = repository.countByProfessor(usuario);  
        return (int) Math.ceil((double) totalRequests / pageSize);
    }

    public Page<Reserva> listarTodas(Pageable pageable) {
        return repository.findAllByOrderByInicioDesc(pageable);
    }

    public Page<Reserva> listarPorUsuario(Usuario usuario, Pageable pageable) {
        return repository.findAllByProfessorOrderByInicioDesc(usuario, pageable);
    }

    public Page<Reserva> listarPorDepartamento(Departamento departamento, Pageable pageable) {
        return repository.findAllByRecursoDepartamentoOrderByInicioDesc(departamento, pageable);
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

    public List<String> encontrarHorarioReservaPorRecurso(Long idRecurso){
        try {
            return repository.findReservaFromRecurso(idRecurso);
        }catch (Exception e){
            throw new PersistenceException();
        }
    }
}
