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

    public Recurso inserirRecurso(Recurso recurso) {
        if(recurso.getDepartamento() == null || recurso.getDepartamento().getId() == null || recurso.getEstado() == null || recurso.getMarca() == null) {
            throw new NullPointerException();
        }else{
            return repository.save(recurso);
        }
    }

    public boolean deletar(Recurso recurso) {
        try {
            repository.delete(recurso);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void deletarRecurso(Long id) {
        try {
            repository.deleteById(id);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new NullPointerException();

        }
    }

    public Recurso atualizar(Recurso recurso) {
        return repository.save(recurso);
    }

    public boolean encontrarRecursoPorID(Long id) {
        try{
            return repository.existsById(id);
        }
        catch(Exception e){
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

    public List<Recurso> ListarTodosRecursos() {
        try {


            System.out.println("entrou lista recurso");
            return repository.findAll();

        }catch (Exception e) {
            System.out.println("erro no ListarTodosrecursos, no arquivo TestService");
            System.out.println("\n\nerro que deu :" + e.getMessage());
            throw new RuntimeException(e);
        }
    };

    public Recurso atualizarRecurso(Recurso recurso) {
        try{
            return repository.save(recurso);
        }catch(Exception e){
            throw new NullPointerException();
        }
    }

    public Boolean atualizarEstado(Long id, Estado estado) {
        try{
            Recurso recurso = repository.findById(id).get();
            recurso.setEstado(estado);
            repository.save(recurso);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    public Optional<Recurso> encontrarPorID(Long id) {
        return repository.findById(id);
    }

}
