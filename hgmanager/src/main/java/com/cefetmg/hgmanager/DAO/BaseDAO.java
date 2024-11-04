package com.cefetmg.hgmanager.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cefetmg.hgmanager.IDAO.IBaseDAO;

import java.util.List;

public abstract class BaseDAO<T, ID> implements IBaseDAO<T, ID> {
    @Override
    public List<T> recuperarPorID(ID id) {
        return findById(id);
    }

    @Override
    public List<BaseDAO> recuperarTodos(BaseDAO baseDAO) {
        return findAll(BaseDAO);
    }

    @Override
    public boolean inserir(BaseDAO baseDAO) {
        return save(baseDAO);
    }

    @Override
    public boolean deletar(BaseDAO baseDAO) {
        if(delete(baseDAO))
            return true;
        return false;
    }

    @Override
    public boolean deletarṔorID(ID id) {
        if(deleteAllById(id))
            return true;
        return false;    
    }

    @Override
    public boolean atualizar(BaseDAO baseDAO) {
        if(save(baseDAO))
            return true;
        return false;
    }
}
