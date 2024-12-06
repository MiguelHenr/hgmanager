package com.cefetmg.hgmanager.Controller;

import com.cefetmg.hgmanager.Model.Usuario;

import com.cefetmg.hgmanager.Service.LoginService;

import br.cefetmg.mockloginapi.exceptions.IncorrectPasswordException;

import jakarta.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
public class LoginController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    LoginService service;

    @GetMapping("/")
    public String skipIndex(Model model) {
        return validateLogin(model);
    }

    @GetMapping("/login")
    public String validateLogin(Model model) {

        model.addAttribute("loginError", false);
        model.addAttribute("loginErrorMessage", "STATUS 200 OK");
        return "index";

    }

    @PostMapping("/login")
    public ResponseEntity<?> validateLogin(@RequestBody Map<String, String> params, HttpSession httpSession, Model model) {
        String login = params.get("usuario");
        String password = params.get("senha");

        try {

            Usuario user = service.LoginValidate(login, password, httpSession);

            model.addAttribute("loginError", false);
            model.addAttribute("loginErrorMessage", "STATUS 200 OK");

            return new ResponseEntity<>(user, HttpStatus.OK);

        }
        catch (Exception e) {

            HttpStatus status;

            System.out.println("erro!!!! " + e.getMessage());
            model.addAttribute("loginError", true);

            if (!(e instanceof IncorrectPasswordException)) {

                model.addAttribute("loginErrorMessage", "Erro interno do Servidor! Tente novamente mais tarde.");
                status = HttpStatus.INTERNAL_SERVER_ERROR;
                return new ResponseEntity<>(e.getMessage(), status);

            }

            model.addAttribute("loginErrorMessage", e.getMessage());
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(e.getMessage(), status);

        }

    }

    @PostMapping("/debugProfile")
    public ResponseEntity<?> redirectPage(HttpSession session, Model model) {

        return null;
    }

}
