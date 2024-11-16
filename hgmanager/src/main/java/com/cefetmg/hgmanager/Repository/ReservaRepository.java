package com.cefetmg.hgmanager.Repository;

import com.cefetmg.hgmanager.Model.Reserva;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservaRepository extends org.springframework.data.jpa.repository.JpaRepository<Reserva, Long> {
}
