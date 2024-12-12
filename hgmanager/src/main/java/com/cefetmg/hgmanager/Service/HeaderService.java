package com.cefetmg.hgmanager.Service;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.cefetmg.hgmanager.Model.Usuario;
import com.cefetmg.hgmanager.Model.Enum.Cargo;

@Service
public class HeaderService {
    public void setAttributes(Model model, Usuario usuario) {
        model.addAttribute("foto", usuario.getFoto());
        model.addAttribute("nome", usuario.getNome());
        model.addAttribute("tae", usuario.getTipoUsuario() == Cargo.TAE);
    }
}