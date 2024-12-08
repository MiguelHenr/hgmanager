package com.cefetmg.hgmanager.Controller;

import com.cefetmg.hgmanager.Model.Usuario;
import com.cefetmg.hgmanager.Service.UserValidationService;

import br.cefetmg.mockloginapi.exceptions.IncorrectPasswordException;

import jakarta.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.ui.Model;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
public class LoginController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    UserValidationService service;

    @GetMapping("/login")
    public String validateLogin() {
        
        return "login";

    }

    @PostMapping("/login")
    public ResponseEntity<?> validateLogin(@RequestBody Map<String, String> params, HttpSession httpSession) {

        String login = params.get("usuario");
        String password = params.get("senha");

        try {

            Usuario user = service.LoginValidate(login, password, httpSession);

            httpSession.setAttribute("userId", user.getId());
            return new ResponseEntity<>(user, HttpStatus.OK);

        }
        catch (Exception e) {

            if (!(e instanceof IncorrectPasswordException))
                return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);

        }

    }

    @RequestMapping(value={"/profile_debug"}, method={RequestMethod.POST, RequestMethod.GET})
    public ModelAndView profileDebug(HttpSession session, ModelMap model) {

        Usuario user = service.retrieveValidatedUser(session);

        if (user == null)
            return new ModelAndView("redirect:/login", model);
        model.addAttribute("userNome", user.getNome());
        model.addAttribute("userCpf", user.getCpf());
        model.addAttribute("userEmail", user.getEmail());
        model.addAttribute("userFuncao", user.getTipoUsuario());
        model.addAttribute("userDepartamento", user.getDepartamento().getNome());

        return new ModelAndView("debugProfile", model);

    }

}
