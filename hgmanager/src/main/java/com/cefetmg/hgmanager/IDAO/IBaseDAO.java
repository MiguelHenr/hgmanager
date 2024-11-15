package com.cefetmg.hgmanager.IDAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface IBaseDAO<T, ID> extends JpaRepository<T, ID> {
    public List<T> recuperarTodos();

    public Optional<T> recuperarPorID(ID id);

    public T inserir(T t);

    public boolean encontrarPorID(ID id);

    public boolean deletar(T t);

    public boolean deletarPorID(ID id);

    public T atualizar(T t);
}
