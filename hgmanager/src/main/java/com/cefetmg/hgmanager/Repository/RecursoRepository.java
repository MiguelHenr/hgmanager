package com.cefetmg.hgmanager.Repository;

import com.cefetmg.hgmanager.Model.Departamento;
import com.cefetmg.hgmanager.Model.Enum.Estado;
import com.cefetmg.hgmanager.Model.Recurso;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecursoRepository extends org.springframework.data.jpa.repository.JpaRepository<Recurso, Long> {
    List<Recurso> findByDepartamento(Departamento departamento);

    List<Recurso> findByEstado(Estado estado);

    @Query(value = "SELECT r FROM Recurso r WHERE r.Id NOT IN " +
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

    void delete(Recurso recurso);
}
