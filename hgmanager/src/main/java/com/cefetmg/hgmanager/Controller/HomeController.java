package com.cefetmg.hgmanager.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.cefetmg.hgmanager.Model.Usuario;
import com.cefetmg.hgmanager.Model.Enum.Cargo;
import com.cefetmg.hgmanager.Service.HeaderService;
import com.cefetmg.hgmanager.Service.UsuarioService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
    @Autowired
    private UsuarioService uService;

    @Autowired
    private HeaderService hService;

    @GetMapping("/")
    public String home(Model model, HttpSession session) {
        Usuario user = uService.retrieveValidatedUser(session);

        hService.setAttributes(model, session);

        return user.getTipoUsuario() == Cargo.TAE ? "forward:/solicitacoes"
                                        : "forward:/solicitar_emprestimo";
    }
}