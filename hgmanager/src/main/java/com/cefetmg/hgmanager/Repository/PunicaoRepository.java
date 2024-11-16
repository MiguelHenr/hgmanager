package com.cefetmg.hgmanager.Repository;

import com.cefetmg.hgmanager.Model.Punicao;
import org.springframework.stereotype.Repository;

@Repository
public interface PunicaoRepository extends org.springframework.data.jpa.repository.JpaRepository<Punicao, Long> {
}
