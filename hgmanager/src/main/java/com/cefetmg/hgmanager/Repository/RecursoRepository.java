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
            "(SELECT s.recurso.Id FROM Reserva s WHERE s.fim >= CURRENT_TIMESTAMP )")
    List<Recurso> listarPorDisponibilidade();

    @Modifying
    @Query("DELETE FROM Recurso r WHERE r.Id = :id")
    void deleteById(@Param("id") Long id);

    void delete(Recurso recurso);
}
