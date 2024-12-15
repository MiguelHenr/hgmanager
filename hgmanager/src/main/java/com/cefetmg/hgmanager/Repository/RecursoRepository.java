package com.cefetmg.hgmanager.Repository;

import com.cefetmg.hgmanager.Model.Departamento;
import com.cefetmg.hgmanager.Model.Enum.Estado;

import jakarta.transaction.Transactional;

import com.cefetmg.hgmanager.Model.Recurso;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

@Repository
public interface RecursoRepository extends org.springframework.data.jpa.repository.JpaRepository<Recurso, Long> {

    @Query("SELECT r FROM Recurso r WHERE r.deletado = false AND r.departamento.id = :idDepartamento")
    List<Recurso> findByDepartamento(@Param("idDepartamento") Long idDepartamento);

    List<Recurso> findByEstado(Estado estado);

    @Query(value = "SELECT r FROM Recurso r WHERE r.deletado = false AND r.Id NOT IN " +
            "(SELECT res.recurso.Id FROM Reserva res INNER JOIN Recurso r " +
            "ON res.recurso.Id = r.Id " +
            "GROUP BY 1 " +
            "HAVING COUNT(res.inicio) >= 126)  ")
    List<Recurso> listByAvailability();

    @Modifying
    @Query("DELETE FROM Recurso r WHERE r.Id = :id")
    void deleteById(@Param("id") Long id);


    @Query("SELECT r FROM Recurso r WHERE r.deletado = false")
    List<Recurso> ListaRecursoAtivos();

    @Query("UPDATE Recurso r SET r.deletado = true WHERE r.Id = :idRecurso")
    @Modifying
    @Transactional
    void tornarInativo(@Param("idRecurso") Long id);
}
