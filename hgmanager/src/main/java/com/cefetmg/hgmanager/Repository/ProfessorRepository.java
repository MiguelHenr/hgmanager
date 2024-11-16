package com.cefetmg.hgmanager.Repository;

import com.cefetmg.hgmanager.Model.Professor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessorRepository extends org.springframework.data.jpa.repository.JpaRepository<Professor, Long> {
}
