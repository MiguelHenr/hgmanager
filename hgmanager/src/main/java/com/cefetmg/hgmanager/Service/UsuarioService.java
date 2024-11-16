package com.cefetmg.hgmanager.Service;

import com.cefetmg.hgmanager.Model.Usuario;
import com.cefetmg.hgmanager.Repository.BaseRepository;
import com.cefetmg.hgmanager.Repository.DepartamentoRepository;
import com.cefetmg.hgmanager.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService extends BaseService<Usuario, Long> {

    UsuarioService(BaseRepository<Usuario, Long> repository) {
        super(repository);
    }

}