package com.cefetmg.hgmanager.IDAO;

import com.cefetmg.hgmanager.DTO.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioIDAO extends JpaRepository<Usuario, Long> {
}
