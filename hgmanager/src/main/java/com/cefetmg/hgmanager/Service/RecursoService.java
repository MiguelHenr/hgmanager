package com.cefetmg.hgmanager.Service;

import com.cefetmg.hgmanager.Model.Departamento;
import com.cefetmg.hgmanager.Model.Enum.Estado;
import com.cefetmg.hgmanager.Model.Recurso;
import com.cefetmg.hgmanager.Repository.RecursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecursoService {

    @Autowired
    private RecursoRepository repository;

    public Recurso inserir(Recurso recurso) {
        if(recurso.getDepartamento() == null || recurso.getDepartamento().getId() == null || recurso.getEstado() == null || recurso.getMarca() == null) {
            throw new NullPointerException();
        }
        return repository.save(recurso);
    }

    public boolean deletar(Recurso recurso) {
        try {
            repository.delete(recurso);
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

    public Recurso atualizar(Recurso recurso) {
        return repository.save(recurso);
    }


    public Optional<Recurso> encontrarPorID(Long id) {
        return repository.findById(id);
    }

    public List<Recurso> listar() {
        try{
            return repository.findAll();
        }catch(Exception e){
            throw new NullPointerException();
        }
    }

    public List<Recurso> listarPorDepartamento(Departamento departamento) {
        try{
            return repository.findByDepartamento(departamento);
        }catch(Exception e){
            throw new NullPointerException();
        }
    }

    public List<Recurso> listarPorEstado(Estado estado) {
        try{
            return repository.findByEstado(estado);
        }catch (Exception e){
            throw new NullPointerException();
        }
    }

    public List<Recurso> listarPorDisponibilidade() {
        return repository.listarPorDisponibilidade();
    }

}
