package com.cefetmg.hgmanager.Repository;

import com.cefetmg.hgmanager.Model.Recurso;
import com.cefetmg.hgmanager.Model.Reserva;
import com.cefetmg.hgmanager.Model.Usuario;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    Page<Reserva> findAllByOrderByInicioDesc(Pageable pageable);

    Page<Reserva> findAllByProfessorOrderByInicioDesc(Usuario professor, Pageable pageable);

    Reserva findReservaByRecurso(Recurso recurso);

    @Query("SELECT r.inicio FROM Reserva r WHERE r.recurso.Id = :idRecurso")
    public List<String> findReservaFromRecurso(@Param("idRecurso") Long idRecurso);
}
