package com.cefetmg.hgmanager.Controller;

import com.cefetmg.hgmanager.Model.Usuario;

import com.cefetmg.hgmanager.Service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
public class LoginController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    LoginService service;

    @PostMapping("/login")
    public ResponseEntity<?> validateLogin(@RequestBody Map<String, String> params, HttpServletRequest request, HttpServletResponse response) {

        String login, password;

        login = params.get("usuario");
        password = params.get("senha");

        try {
            Usuario user = service.LoginValidate(login, password);

            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

}
