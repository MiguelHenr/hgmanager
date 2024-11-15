package com.cefetmg.hgmanager.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cefetmg.hgmanager.IDAO.IBaseDAO;

import java.util.List;
import java.util.Optional;

public abstract class BaseDAO<T, ID> implements IBaseDAO<T, ID> {

    // Recupera uma entidade do banco de dados através do ID dela.
    @Override
    public Optional<T> recuperarPorID(ID id) {
        return findById(id);
    }

    // Recupera TODAS as entidades que estão cadastradas no bando de dados
    @Override
    public List<T> recuperarTodos() {
        return findAll();
    }

    // Insere uma nova entidade no banco de dados
    @Override
    public T inserir(T entity) {
        return save(entity);
    }

    // Deleta uma entidade inserida no banco de dados, caso ela não exista nele, uma execeção é lançada
    @Override
    public boolean deletar(T entity) {
        try {
            delete(entity);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Deleta uma entidade do banco de dados, através do ID dela, caso ela não existe nele, uma exceção é lançada e é retornado false
    @Override
    public boolean deletarPorID(ID id) {
        try {
            deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Atualiza uma entidade ja criada no bando de dados
    @Override
    public T atualizar(T entity) {
        return save(entity);
    }

    // Encontra uma entidade no banco de dados, caso ela não exista nele, é retornado false
    @Override
    public boolean encontrarPorID(ID id) {
        return existsById(id);
    }
}
