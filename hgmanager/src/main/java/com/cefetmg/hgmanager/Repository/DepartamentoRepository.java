package com.cefetmg.hgmanager.Repository;

import com.cefetmg.hgmanager.Model.Departamento;
import com.cefetmg.hgmanager.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public interface DepartamentoRepository extends JpaRepository<Departamento, Long> {
    public Departamento findByNome(String nome);

    public List<Departamento> findAll();

    @Query("SELECT d FROM Departamento d INNER JOIN d.usuarios u ON u.id = :idUsuario")
    public Departamento findByIdUsuario(@Param("idUsuario") Long idUsuario);
}
