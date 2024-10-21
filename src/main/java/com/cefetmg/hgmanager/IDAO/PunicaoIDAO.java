package com.cefetmg.hgmanager.IDAO;

import com.cefetmg.hgmanager.DTO.Punicao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PunicaoIDAO extends JpaRepository<Punicao, Long> {
}
