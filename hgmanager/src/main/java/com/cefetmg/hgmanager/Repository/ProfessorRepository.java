package com.cefetmg.hgmanager.Repository;

import com.cefetmg.hgmanager.Model.Professor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessorRepository extends BaseRepository<Professor, Long> {
}
