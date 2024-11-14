package com.cefetmg.hgmanager.IDAO;

import com.cefetmg.hgmanager.DTO.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartamentoIDAO extends JpaRepository<Departamento, Long> {
}
