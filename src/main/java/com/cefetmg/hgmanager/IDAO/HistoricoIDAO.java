package com.cefetmg.hgmanager.IDAO;

import com.cefetmg.hgmanager.DTO.Historico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoricoIDAO extends JpaRepository<Historico, Long> {
}
