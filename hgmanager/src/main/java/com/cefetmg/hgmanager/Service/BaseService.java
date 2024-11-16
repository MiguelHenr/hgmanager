package com.cefetmg.hgmanager.Service;

import com.cefetmg.hgmanager.Repository.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public abstract class BaseService<T, ID>{

    private final BaseRepository<T, ID> repository;

    @Autowired
    BaseService(BaseRepository<T, ID> repository) {
        this.repository = repository;
    }

    // Recupera uma entidade do banco de dados através do ID dela.
    public Optional<T> recuperarPorID(Long id) {
        return repository.findById(id);
    }

    // Recupera TODAS as entidades que estão cadastradas no bando de dados
    public List<T> recuperarTodos() {
        return repository.findAll();
    }

    // Insere uma nova entidade no banco de dados
    public T inserir(T entity) {
        return repository.save(entity);
    }

    // Deleta uma entidade inserida no banco de dados, caso ela não exista nele, uma execeção é lançada
    public boolean deletar(T entity) {
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
    public T atualizar(T entity) {
        return repository.save(entity);
    }

    // Encontra uma entidade no banco de dados, caso ela não exista nele, é retornado false
    public boolean encontrarPorID(Long id) {
        return repository.existsById(id);
    }
}
