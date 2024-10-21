package com.cefetmg.hgmanager.IDAO;

import com.cefetmg.hgmanager.DTO.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservaIDAO extends JpaRepository<Reserva, Long> {
}
