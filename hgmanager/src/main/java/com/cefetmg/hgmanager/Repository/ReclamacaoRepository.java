package com.cefetmg.hgmanager.Repository;

import com.cefetmg.hgmanager.Model.Departamento;
import com.cefetmg.hgmanager.Model.Reclamacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReclamacaoRepository extends JpaRepository<Reclamacao, Long> {
}
