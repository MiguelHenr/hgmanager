package com.cefetmg.hgmanager.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

import com.cefetmg.hgmanager.Model.Usuario;
import com.cefetmg.hgmanager.Model.Enum.Cargo;

import jakarta.servlet.http.HttpSession;

@Service
public class HeaderService {
    @Autowired
    private UserValidationService service;

    public void setAttributes(Model model, HttpSession session) {
        setAttributes(model, service.retrieveValidatedUser(session));
    }

    public void setAttributes(ModelMap model, HttpSession session) {
        setAttributes(model, service.retrieveValidatedUser(session));
    }

    private void setAttributes(Model model, Usuario usuario) {
        model.addAttribute("foto", usuario.getFoto());
        model.addAttribute("nome", usuario.getNome());
        model.addAttribute("tae", usuario.getTipoUsuario() == Cargo.TAE);
    }

    private void setAttributes(ModelMap model, Usuario usuario) {
        model.addAttribute("foto", usuario.getFoto());
        model.addAttribute("nome", usuario.getNome());
        model.addAttribute("tae", usuario.getTipoUsuario() == Cargo.TAE);
    }
}