package com.estore.api.estoreapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import com.estore.api.estoreapi.model.Login;
import com.estore.api.estoreapi.persistence.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Test the Milk Controller class
 * 
 * @author Spoiled Milk Team
 */
@Tag("Controller-tier")
public class LoginControllerTest {
    private LoginController loginController;
    private LoginDAO mockLoginDAO;

    @BeforeEach
    public void setupLoginController() {
        mockLoginDAO = mock(LoginDAO.class);
        loginController = new LoginController(mockLoginDAO);
    }

    @Test
    public void testResetPassword() throws IOException {
        String username = "admin";

        when(mockLoginDAO.resetPassword(username)).thenReturn(true);

        ResponseEntity<Boolean> response = loginController.resetPassword(username);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testResetPasswordFail() throws IOException {
        String username = "";

        when(mockLoginDAO.resetPassword(username)).thenReturn(false);

        ResponseEntity<Boolean> response = loginController.resetPassword(username);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
    
    @Test
    public void testLogin() throws IOException {
        String username = "admin";
        String password = "admin";

        when(mockLoginDAO.login(new Login(username, password))).thenReturn(username);

        ResponseEntity<Boolean> response = loginController.login(new Login(username, password));

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testLoginFail() throws IOException {
        String username = "";
        String password = "admin";

        when(mockLoginDAO.login(new Login(username, password))).thenReturn(null);

        ResponseEntity<Boolean> response = loginController.login(new Login(username, password));

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void testSignup() throws IOException {
        String username = "admin";
        String password = "admin";

        when(mockLoginDAO.signup(new Login(username, password))).thenReturn(username);

        ResponseEntity<Boolean> response = loginController.signup(new Login(username, password));

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testSignupFail() throws IOException {
        String username = "";
        String password = "admin";

        when(mockLoginDAO.signup(new Login(username, password))).thenReturn(null);

        ResponseEntity<Boolean> response = loginController.signup(new Login(username, password));

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }
}
