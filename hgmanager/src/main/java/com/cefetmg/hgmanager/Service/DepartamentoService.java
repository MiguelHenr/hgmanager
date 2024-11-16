package com.cefetmg.hgmanager.Service;

import com.cefetmg.hgmanager.Model.Departamento;
import com.cefetmg.hgmanager.Repository.BaseRepository;
import org.springframework.stereotype.Service;

@Service
public class DepartamentoService extends BaseService<Departamento, Long>{

    DepartamentoService(BaseRepository<Departamento, Long> repository) {
        super(repository);
    }
}
