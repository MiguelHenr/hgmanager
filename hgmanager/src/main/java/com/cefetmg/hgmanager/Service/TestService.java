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


    public List<Recurso> ListarTodosRecursos() {
       try {


           System.out.println("entrou lista recurso");
           return recursoRepository.findAll();

       }catch (Exception e) {
           System.out.println("erro no ListarTodosrecursos, no arquivo TestService");
           System.out.println("\n\nerro que deu :" + e.getMessage());
           throw new RuntimeException(e);
       }
    };

    public Recurso inserirRecurso(Recurso recurso) {
        if(recurso.getDepartamento() == null || recurso.getDepartamento().getId() == null || recurso.getEstado() == null || recurso.getMarca() == null) {
            throw new NullPointerException();
        }else{
        return recursoRepository.save(recurso);
        }
    }

    public void deletarRecurso(Long id) {
        try {
            recursoRepository.deleteById(id);
            System.out.println("passuo deleteByID");
        } catch (Exception e) {
            System.out.println("deu erro aqui");
            System.out.println(e.getMessage());
            throw new NullPointerException();

        }
    }

    public Recurso atualizarRecurso(Recurso recurso) {
        try{
            return recursoRepository.save(recurso);
        }catch(Exception e){
            throw new NullPointerException();
        }
    }

    public boolean encontrarRecursoPorID(Long id) {
       try{
           return recursoRepository.existsById(id);
       }
       catch(Exception e){
           throw new NullPointerException();
        }
    }

    public List<Recurso> listarRecursosPorDepartamento(Departamento departamento) {
        try{
            return recursoRepository.findByDepartamento(departamento);
        }catch(Exception e){
            throw new NullPointerException();
        }
    }

    public List<Departamento> recuperarTodosDepartamentos() {
        try{
            return departamentoRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Departamento inserirDepartamento(Departamento entity) {
       try{
        return departamentoRepository.save(entity);
       }catch(Exception e){
           throw new NullPointerException();
       }
    }

    public Boolean atualizarEstado(Long id, Estado estado) {
        try{
            Recurso recurso = recursoRepository.findById(id).get();
            recurso.setEstado(estado);
            recursoRepository.save(recurso);
            return true;
        }catch(Exception e){
            return false;
        }
    }



    public List<Departamento> listarDepartamentos() {
        try{
            System.out.println("entrou em listarDepartamentos no TestService");
            System.out.println(departamentoRepository.findAll());
            return departamentoRepository.findAll();
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

}
