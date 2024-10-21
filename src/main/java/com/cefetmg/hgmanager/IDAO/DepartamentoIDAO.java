package com.cefetmg.hgmanager.IDAO;

import com.cefetmg.hgmanager.DTO.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartamentoIDAO extends JpaRepository<Departamento, Long> {
}
