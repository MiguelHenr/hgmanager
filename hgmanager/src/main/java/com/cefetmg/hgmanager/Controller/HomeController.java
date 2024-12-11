package com.cefetmg.hgmanager.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cefetmg.hgmanager.Model.Usuario;
import com.cefetmg.hgmanager.Model.Enum.Cargo;
import com.cefetmg.hgmanager.Service.HeaderService;
import com.cefetmg.hgmanager.Service.UserValidationService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
    @Autowired
    private UserValidationService uService;

    @Autowired
    private HeaderService hService;

    @GetMapping("/")
    public String home(Model model, HttpSession session) {
        Usuario usr = uService.retrieveValidatedUser(session);

        hService.setAttributes(model, usr);

        return usr.getTipoUsuario() == Cargo.TAE ? "forward:/solicitacoes" 
                                        : "forward:/solicitar_emprestimo";
    }
}