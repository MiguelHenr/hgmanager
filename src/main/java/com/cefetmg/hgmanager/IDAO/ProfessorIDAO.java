package com.cefetmg.hgmanager.IDAO;

import com.cefetmg.hgmanager.DTO.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorIDAO extends JpaRepository<Professor, Long> {
}
