package com.cefetmg.hgmanager.IDAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IBaseDAO<T, ID> extends JpaRepository<T, ID> {
    public List<T> recuperarTodos(T t);

    public List<T> recuperarPorID(ID id);

    public boolean inserir(T t);

    public boolean inserirPorID(ID id);

    public boolean deletar(T t);

    public boolean deletarṔorID(ID id);

    public boolean atualizar(T t);

    public boolean atualizarPorID(ID id);
}
