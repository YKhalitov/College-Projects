package com.estore.api.estoreapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estore.api.estoreapi.model.Login;
import com.estore.api.estoreapi.persistence.LoginDAO;

import java.io.IOException;
import java.util.logging.Logger;

@RestController
@RequestMapping("/login")
public class LoginController {
    LoginDAO loginDAO;
    private static final Logger LOG = Logger.getLogger(LoginController.class.getName());

    public LoginController(LoginDAO loginDAO) {
        this.loginDAO = loginDAO;
    }

    @PostMapping("/login")
    public ResponseEntity<Boolean> login(@RequestBody Login loginData) throws IOException {
        LOG.info("POST /login");
        String result = loginDAO.login(loginData);
        if (result != null) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<Boolean> signup(@RequestBody Login loginData) throws IOException {
        LOG.info("POST /signup");
        String result = loginDAO.signup(loginData);
        if (result != null) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Boolean> resetPassword(@PathVariable String username) throws IOException {
        LOG.info("DELETE /login/"+username);
        if (loginDAO.resetPassword(username)) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
    }
}

