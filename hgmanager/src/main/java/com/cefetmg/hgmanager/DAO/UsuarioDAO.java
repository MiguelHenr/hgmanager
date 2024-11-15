package com.cefetmg.hgmanager.DAO;

import com.cefetmg.hgmanager.DTO.Usuario;
import com.cefetmg.hgmanager.IDAO.IUsuarioDAO;
import org.springframework.stereotype.Service;

@Service
public abstract class UsuarioDAO extends BaseDAO<Usuario, Long> implements IUsuarioDAO {

}