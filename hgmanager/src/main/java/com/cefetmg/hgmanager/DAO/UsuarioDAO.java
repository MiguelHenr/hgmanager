package com.cefetmg.hgmanager.DAO;

import com.cefetmg.hgmanager.DTO.Usuario;
import com.cefetmg.hgmanager.IDAO.UsuarioIDAO;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioDAO extends BaseDAO<UsuarioDAO, Long> implements UsuarioIDAO {
    
}
