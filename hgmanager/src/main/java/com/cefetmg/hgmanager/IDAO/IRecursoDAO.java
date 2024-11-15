package com.cefetmg.hgmanager.IDAO;

import com.cefetmg.hgmanager.DTO.Recurso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRecursoDAO extends IBaseDAO<Recurso, Long> {
}
