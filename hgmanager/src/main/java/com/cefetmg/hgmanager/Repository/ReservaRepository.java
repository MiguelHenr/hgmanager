package com.cefetmg.hgmanager.Repository;

import com.cefetmg.hgmanager.Model.Reserva;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservaRepository extends org.springframework.data.jpa.repository.JpaRepository<Reserva, Long> {
    @Query("SELECT r FROM Reserva r ORDER BY r.inicio DESC")
    public List<Reserva> findAllOrdered();
}