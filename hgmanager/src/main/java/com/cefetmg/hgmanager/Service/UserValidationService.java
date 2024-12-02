package com.cefetmg.hgmanager.Service;

import br.cefetmg.mockloginapi.dto.DepartamentoDTO;
import br.cefetmg.mockloginapi.service.DepartamentoInfo;
import com.cefetmg.hgmanager.Model.Departamento;
import com.cefetmg.hgmanager.Model.Enum.Cargo;
import com.cefetmg.hgmanager.Model.Usuario;
import com.cefetmg.hgmanager.Repository.DepartamentoRepository;
import com.cefetmg.hgmanager.Repository.UsuarioRepository;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import br.cefetmg.mockloginapi.service.UsuarioValidation;
import br.cefetmg.mockloginapi.dto.UsuarioDTO;
import br.cefetmg.mockloginapi.exceptions.IncorrectPasswordException;
import br.cefetmg.mockloginapi.exceptions.UserNotFoundException;

import java.util.Optional;

@Service
public class UserValidationService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private DepartamentoRepository departamentoRepository;

    public Usuario LoginValidate(String login, String password, HttpSession httpSession)
            throws UserNotFoundException, IncorrectPasswordException {

        UsuarioDTO dto = UsuarioValidation.validateLogin(login, password);

        httpSession.setAttribute("userId", dto.getId());

        return usuarioDtoToModel(dto);

    }

    public Usuario usuarioDtoToModel(UsuarioDTO dto) {

        String deptName = dto.getDepartamento();
        DepartamentoDTO dDto = DepartamentoInfo.getDTO(deptName);
        Departamento departamento = departamentoDtoToModel(dDto);

        Usuario model = usuarioRepository.findByCpf(dto.getCpf());

        if (model == null) {
            model = new Usuario();
            model.setCpf(dto.getCpf());
        }

        model.setNome(dto.getNome());
        model.setEmail(dto.getEmail());
        model.setFoto(dto.getFoto());
        model.setTipoUsuario(Cargo.ofName(dto.getTipoUsuario()));
        model.setDepartamento(departamento);

        usuarioRepository.save(model);
        return model;

    }

    public Departamento departamentoDtoToModel(DepartamentoDTO dto) {

        Departamento departamento = departamentoRepository.findByNome(dto.getNome());

        if (departamento!=null){
            departamentoRepository.save(departamento);
            return departamento;
        }

        departamento = new Departamento(
                dto.getTelefone(),
                dto.getNome(),
                dto.getEmail(),
                dto.getCampus(),
                null,
                null
        );

        departamentoRepository.save(departamento);
        return departamento;

    }

    public Usuario retrieveValidatedUser(HttpSession session) {
        Long id = (Long) session.getAttribute("userId");
        Optional<Usuario> model = usuarioRepository.findById(id);
        return model.orElse(null);
    }

}
