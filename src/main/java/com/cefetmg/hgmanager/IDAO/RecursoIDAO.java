package com.cefetmg.hgmanager.IDAO;

import com.cefetmg.hgmanager.DTO.Recurso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecursoIDAO extends JpaRepository<Recurso, Long> {
}
