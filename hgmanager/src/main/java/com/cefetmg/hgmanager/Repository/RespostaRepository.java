package com.cefetmg.hgmanager.Repository;

import com.cefetmg.hgmanager.Model.Resposta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RespostaRepository extends JpaRepository<Resposta, Long> {
}
