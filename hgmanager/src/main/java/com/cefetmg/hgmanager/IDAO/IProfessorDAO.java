package com.cefetmg.hgmanager.IDAO;

import com.cefetmg.hgmanager.DTO.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProfessorDAO extends IBaseDAO<Professor, Long> {
}
