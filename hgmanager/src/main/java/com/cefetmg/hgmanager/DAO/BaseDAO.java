package com.cefetmg.hgmanager.DAO;

import com.cefetmg.hgmanager.IDAO.IBaseDAO;

import java.util.List;

public abstract class BaseDAO<T, ID> implements IBaseDAO<T, ID> {

    @Override
    public List<T> recuperarPorID(ID id) {
        return null;
    }

    @Override
    public List<BaseDAO> recuperarTodos(BaseDAO baseDAO) {
        return null;
    }

    @Override
    public boolean inserir(BaseDAO baseDAO) {
        return false;
    }

    @Override
    public boolean inserirPorID(ID id) {
        return false;
    }

    @Override
    public boolean deletar(BaseDAO baseDAO) {
        return false;
    }

    @Override
    public boolean deletarṔorID(ID id) {
        return false;
    }

    @Override
    public boolean atualizar(BaseDAO baseDAO) {
        return false;
    }

    @Override
    public boolean atualiarPorID(ID id) {
        return false;
    }
}
