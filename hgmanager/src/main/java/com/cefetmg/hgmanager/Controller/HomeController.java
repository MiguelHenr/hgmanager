package com.cefetmg.hgmanager.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cefetmg.hgmanager.Model.Usuario;
import com.cefetmg.hgmanager.Model.Enum.Cargo;
import com.cefetmg.hgmanager.Service.UserValidationService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
    @Autowired
    UserValidationService service;

    @GetMapping("/")
    public String home(Model model, HttpSession session) {
        Usuario usr = service.retrieveValidatedUser(session);
        boolean tae = usr.getTipoUsuario() == Cargo.TAE;

        model.addAttribute("foto", usr.getFoto());
        model.addAttribute("nome", usr.getNome());
        model.addAttribute("tae", tae);

        return tae ? "forward:/solicitacoes" : "forward:/solicitar_emprestimo";
    }
}