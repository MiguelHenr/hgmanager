package com.cefetmg.hgmanager.Repository;

import com.cefetmg.hgmanager.Model.Recurso;
import com.cefetmg.hgmanager.Model.Reserva;
import com.cefetmg.hgmanager.Model.Usuario;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservaRepository extends org.springframework.data.jpa.repository.JpaRepository<Reserva, Long> {
    @Query("SELECT r FROM Reserva r ORDER BY r.inicio DESC")
    List<Reserva> findAllOrdered();

    @Query("SELECT r FROM Reserva r WHERE r.professor = :p ORDER BY r.inicio DESC")
    List<Reserva> findAllByProfessorOrdered(@Param("p") Usuario professor);

    Reserva findReservaByRecurso(Recurso recurso);
}
