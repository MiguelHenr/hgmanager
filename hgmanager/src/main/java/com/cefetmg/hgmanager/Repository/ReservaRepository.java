package com.cefetmg.hgmanager.Repository;

import com.cefetmg.hgmanager.Model.Recurso;
import com.cefetmg.hgmanager.Model.Reserva;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservaRepository extends org.springframework.data.jpa.repository.JpaRepository<Reserva, Long> {
    @Query("SELECT r FROM Reserva r ORDER BY r.inicio DESC")
    public List<Reserva> findAllOrdered();

    Reserva findReservaByRecurso(Recurso recurso);

    @Query("SELECT r.inicio, r.fim FROM Reserva r WHERE r.recurso.Id = :idRecurso")
    public List<Object[]> findReservaFromRecurso(@Param("idRecurso") Long idRecurso);
}
