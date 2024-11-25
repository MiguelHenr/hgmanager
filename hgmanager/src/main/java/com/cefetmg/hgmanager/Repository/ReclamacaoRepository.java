package com.cefetmg.hgmanager.Repository;

import com.cefetmg.hgmanager.Model.Departamento;
import com.cefetmg.hgmanager.Model.Reclamacao;
import com.cefetmg.hgmanager.Model.Reserva;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReclamacaoRepository extends JpaRepository<Reclamacao, Long> {
    @Query("SELECT r FROM Reclamacao r ORDER BY r.data DESC")
    public List<Reclamacao> findAllOrdered();
}
