package com.cefetmg.hgmanager.Service;

import com.cefetmg.hgmanager.Model.Departamento;
import com.cefetmg.hgmanager.Model.Enum.Estado;
import com.cefetmg.hgmanager.Model.Recurso;
import com.cefetmg.hgmanager.Repository.DepartamentoRepository;
import com.cefetmg.hgmanager.Repository.RecursoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class TestService {

    @Autowired
    private DepartamentoRepository departamentoRepository;

    @Autowired
    private RecursoRepository recursoRepository;

    public Recurso inserirRecurso(Recurso recurso) {
        if(recurso.getDepartamento() == null || recurso.getDepartamento().getId() == null || recurso.getEstado() == null || recurso.getMarca() == null) {
            throw new NullPointerException();
        }
        return recursoRepository.save(recurso);
    }

    public boolean deletarRecurso(Recurso recurso) {
        try {
            recursoRepository.delete(recurso);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean deletarRecursoPorID(Long id) {
        try {
            recursoRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Recurso atualizarRecurso(Recurso recurso) {
        return recursoRepository.save(recurso);
    }

    public boolean encontrarRecursoPorID(Long id) {
        return recursoRepository.existsById(id);
    }

    public List<Recurso> listarRecursos() {
        try{
            return recursoRepository.findAll();
        }catch(Exception e){
            throw new NullPointerException();
        }
    }

    public List<Recurso> listarPorDepartamento(Departamento departamento) {
        try{
            return recursoRepository.findByDepartamento(departamento);
        }catch(Exception e){
            throw new NullPointerException();
        }
    }

    public List<Recurso> listarRecursoPorEstado(Estado estado) {
        try{
            return recursoRepository.findByEstado(estado);
        }catch (Exception e){
            throw new NullPointerException();
        }
    }

    // Recupera uma entidade do banco de dados através do ID dela.
    public Optional<Departamento> recuperarDepartamentoPorID(Long id) {
        return departamentoRepository.findById(id);
    }

    // Recupera DepartamentoODAS as entidades que estão cadastradas no bando de dados
    public List<Departamento> recuperarTodosDepartamentos() {
        return departamentoRepository.findAll();
    }

    // Insere uma nova entidade no banco de dados
    public Departamento inserirDepartamento(Departamento entity) {
        return departamentoRepository.save(entity);
    }

    // Deleta uma entidade inserida no banco de dados, caso ela não exista nele, uma execeção é lançada
    public boolean deletarDepartamento(Departamento departamento) {
        try {
            departamentoRepository.delete(departamento);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Deleta uma entidade do banco de dados, através do ID dela, caso ela não existe nele, uma exceção é lançada e é retornado false
    public boolean deletarDepartamentoPorID(Long id) {
        try {
            departamentoRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Atualiza uma entidade ja criada no bando de dados
    public Departamento atualizarDepartamento(Departamento entity) {
        return departamentoRepository.save(entity);
    }

    // Encontra uma entidade no banco de dados, caso ela não exista nele, é retornado false
    public boolean encontrarDepartamentoPorID(Long id) {
        return departamentoRepository.existsById(id);
    }

    public List<Departamento> listarDepartamentos() {
        try{
            return departamentoRepository.findAll();
        }catch(Exception e){
            throw new NullPointerException();
        }
    }

}
