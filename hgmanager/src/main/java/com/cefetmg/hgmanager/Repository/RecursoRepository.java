package com.cefetmg.hgmanager.Repository;

import com.cefetmg.hgmanager.Model.Departamento;
import com.cefetmg.hgmanager.Model.Enum.Estado;
import com.cefetmg.hgmanager.Model.Recurso;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecursoRepository extends org.springframework.data.jpa.repository.JpaRepository<Recurso, Long> {
    List<Recurso> findByDepartamento(Departamento departamento);

    List<Recurso> findByEstado(Estado estado);
}
