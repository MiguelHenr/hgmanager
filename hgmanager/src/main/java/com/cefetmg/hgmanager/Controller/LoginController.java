package com.cefetmg.hgmanager.Controller;

import com.cefetmg.hgmanager.Model.Usuario;
import com.cefetmg.hgmanager.Service.LoginService;

import br.cefetmg.mockloginapi.exceptions.InvalidLoginException;

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
    LoginService service;

    @GetMapping("/")
    public String skipIndex(Model model) {
        return validateLogin(model);
    }

    @GetMapping("/login")
    public String validateLogin(Model model) {

        model.addAttribute("loginStatus", false);
        
        return "index";

    }

    @PostMapping("/login")
    public ResponseEntity<?> validateLogin(@RequestBody Map<String, String> params, HttpSession httpSession, Model model) {

        String login = params.get("usuario");
        String password = params.get("senha");

        try {

            Usuario user = service.LoginValidate(login, password, httpSession);

            httpSession.setAttribute("userId", user.getId());
            return new ResponseEntity<>(user, HttpStatus.OK);

        }
        catch (Exception e) {

            HttpStatus status;

            if (!(e instanceof InvalidLoginException)) {

                model.addAttribute("loginErrorMessage", "Erro interno do Servidor! Tente novamente mais tarde.");
                status = HttpStatus.INTERNAL_SERVER_ERROR;
                return new ResponseEntity<>(e.getMessage(), status);

            }

            model.addAttribute("loginErrorMessage", e.getMessage());
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(e.getMessage(), status);

        }

    }

    @RequestMapping(value={"/profile_debug"}, method={RequestMethod.POST, RequestMethod.GET})
    public ModelAndView profileDebug(HttpSession session, ModelMap model) {

        long id;

        try {
            id = (long) session.getAttribute("userId");
        }
        catch(Exception e) {
            id = -1;
        }

        Usuario user = service.getUserDebug(id);

        if (user == null)
            return new ModelAndView("redirect:/index", model);
        model.addAttribute("userNome", user.getNome());
        model.addAttribute("userCpf", user.getCpf());
        model.addAttribute("userEmail", user.getEmail());
        model.addAttribute("userFuncao", user.getTipoUsuario());
        model.addAttribute("userDepartamento", user.getDepartamento().getNome());

        return new ModelAndView("debugProfile", model);

    }

}
