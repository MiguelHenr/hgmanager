package com.cefetmg.hgmanager.Service;

import com.cefetmg.hgmanager.Model.Departamento;
import com.cefetmg.hgmanager.Model.Usuario;
import com.cefetmg.hgmanager.Repository.DepartamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartamentoService{

    @Autowired
    private DepartamentoRepository repository;

    // Recupera uma entidade do banco de dados através do ID dela.
    public Optional<Departamento> recuperarPorID(Long id) {
        return repository.findById(id);
    }

    // Recupera DepartamentoODAS as entidades que estão cadastradas no bando de dados
    public List<Departamento> recuperarTodos() {
        return repository.findAll();
    }

    // Insere uma nova entidade no banco de dados
    public Departamento inserir(Departamento entity) {
        return repository.save(entity);
    }

    // Deleta uma entidade inserida no banco de dados, caso ela não exista nele, uma execeção é lançada
    public boolean deletar(Departamento entity) {
        try {
            repository.delete(entity);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Deleta uma entidade do banco de dados, através do ID dela, caso ela não existe nele, uma exceção é lançada e é retornado false
    public boolean deletarPorID(Long id) {
        try {
            repository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Atualiza uma entidade ja criada no bando de dados
    public Departamento atualizar(Departamento entity) {
        return repository.save(entity);
    }

    // Encontra uma entidade no banco de dados, caso ela não exista nele, é retornado false
    public boolean encontrarPorID(Long id) {
        return repository.existsById(id);
    }

    public List<Departamento> listar() {
        try{
            return repository.findAll();
        }catch(Exception e){
            throw new NullPointerException();
        }
    }
}
