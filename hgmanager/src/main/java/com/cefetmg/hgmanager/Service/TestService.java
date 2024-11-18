package com.cefetmg.hgmanager.Service;

import com.cefetmg.hgmanager.Model.Departamento;
import com.cefetmg.hgmanager.Model.Enum.Estado;
import com.cefetmg.hgmanager.Model.Recurso;
import com.cefetmg.hgmanager.Repository.DepartamentoRepository;
import com.cefetmg.hgmanager.Repository.RecursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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

    public Recurso atualizarRecurso(Recurso recurso) {
        return recursoRepository.save(recurso);
    }

    public boolean encontrarRecursoPorID(Long id) {
        return recursoRepository.existsById(id);
    }

    public List<Recurso> listarRecursosPorDepartamento(Departamento departamento) {
        try{
            return recursoRepository.findByDepartamento(departamento);
        }catch(Exception e){
            throw new NullPointerException();
        }
    }

    public List<Departamento> recuperarTodosDepartamentos() {
        return departamentoRepository.findAll();
    }

    public Departamento inserirDepartamento(Departamento entity) {
        return departamentoRepository.save(entity);
    }

}
