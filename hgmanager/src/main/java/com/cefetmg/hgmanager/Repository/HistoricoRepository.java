package com.cefetmg.hgmanager.Repository;

import com.cefetmg.hgmanager.Model.Historico;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoricoRepository extends org.springframework.data.jpa.repository.JpaRepository<Historico, Long> {
}
