package com.cefetmg.hgmanager.IDAO;

import com.cefetmg.hgmanager.DTO.Tae;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITaeDAO extends IBaseDAO<Tae, Long> {
}
