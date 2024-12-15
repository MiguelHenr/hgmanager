package com.cefetmg.hgmanager.Repository;

import com.cefetmg.hgmanager.Model.Departamento;
import com.cefetmg.hgmanager.Model.Enum.Estado;
import com.cefetmg.hgmanager.Model.Recurso;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

@Repository
public interface RecursoRepository extends org.springframework.data.jpa.repository.JpaRepository<Recurso, Long> {

    @Query("SELECT r FROM Recurso r WHERE r.deletado = false AND r.departamento.id = :idDepartamento")
    Page<Recurso> findByDepartamento(@Param("idDepartamento") Long idDepartamento , Pageable pageable);

    List<Recurso> findByEstado(Estado estado);

    @Query(value = "SELECT r FROM Recurso r WHERE r.deletado = false AND r.Id NOT IN " +
            "(SELECT res.recurso.Id FROM Reserva res INNER JOIN Recurso r " +
            "ON res.recurso.Id = r.Id " +
            "GROUP BY 1 " +
            "HAVING COUNT(res.inicio) >= 126)  ")
    List<Recurso> listByAvailability();

    @Query("SELECT EXISTS(SELECT u.id FROM Usuario u INNER JOIN Recurso r " +
            "ON u.departamento.id = r.departamento.id " +
            "WHERE u.id = :idUsuario AND r.Id = :idRecurso)")
    boolean areUsuarioSameDepartamentoOfRecurso(@Param("idUsuario") Long idUsuario, @Param("idRecurso") Long idRecurso);

    @Modifying
    @Query("DELETE FROM Recurso r WHERE r.Id = :id")
    void deleteById(@Param("id") Long id);

    Long countByDepartamento(Departamento departamento);

    @Query("SELECT r FROM Recurso r WHERE r.deletado = false")
    Page<Recurso> ListaRecursoAtivos(Pageable pageable);

    @Query("UPDATE Recurso r SET r.deletado = true WHERE r.Id = :idRecurso")
    void tornarInativo(@Param("idRecurso") Long id);
}
