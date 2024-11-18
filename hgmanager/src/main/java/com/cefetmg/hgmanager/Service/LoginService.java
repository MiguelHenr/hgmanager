package com.cefetmg.hgmanager.Service;

import br.cefetmg.mockloginapi.dto.DepartamentoDTO;
import br.cefetmg.mockloginapi.service.DepartamentoInfo;
import com.cefetmg.hgmanager.Model.Departamento;
import com.cefetmg.hgmanager.Model.Usuario;
import com.cefetmg.hgmanager.Repository.DepartamentoRepository;
import com.cefetmg.hgmanager.Repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.cefetmg.mockloginapi.service.UsuarioValidation;
import br.cefetmg.mockloginapi.dto.UsuarioDTO;
import br.cefetmg.mockloginapi.exceptions.IncorrectPasswordException;
import br.cefetmg.mockloginapi.exceptions.UserNotFoundException;


@Service
public class LoginService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private DepartamentoRepository departamentoRepository;

    public Usuario LoginValidate(String login, String password) throws UserNotFoundException, IncorrectPasswordException {

        UsuarioDTO dto = UsuarioValidation.validateLogin(login, password);

        Usuario model = usuarioDtoToModel(dto);

        System.out.println("dto:");
        System.out.println("cpf = " + dto.getCpf());
        System.out.println("senha = " + dto.getSenha());
        System.out.println("model:");
        System.out.println("cpf = " + model.getCpf());
        System.out.println("senha = " + model.getSenha());

        return model;

    }

    public Usuario usuarioDtoToModel(UsuarioDTO dto) {

        String deptName = dto.getDepartamento();
        DepartamentoDTO dDto = DepartamentoInfo.getDTO(deptName);
        //Departamento departamento = departamentoDtoToModel(dDto);
        Departamento departamento = new Departamento();

        Usuario model = new Usuario(
                dto.getId(),
                dto.getNome(),
                dto.getEmail(),
                dto.getSenha(),
                dto.getCpf(),
                dto.getFoto(),
                dto.getTipoUsuario(),
                departamento
        );

        //usuarioRepository.save(model);
        return model;

    }

    public Departamento departamentoDtoToModel(DepartamentoDTO dto) {

        Departamento model = new Departamento(
                dto.getId(),
                dto.getTelefone(),
                dto.getNome(),
                dto.getEmail(),
                dto.getCampus()
        );

        //departamentoRepository.save(model);
        return model;

    }

}
