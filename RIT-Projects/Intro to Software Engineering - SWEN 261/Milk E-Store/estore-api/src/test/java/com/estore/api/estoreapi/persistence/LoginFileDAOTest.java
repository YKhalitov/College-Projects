package com.estore.api.estoreapi.persistence;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.estore.api.estoreapi.model.HashedLogin;
import com.estore.api.estoreapi.model.Login;
import com.estore.api.estoreapi.model.Milk;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;


@Tag("Persistence-tier")
public class LoginFileDAOTest {
    LoginFileDAO loginFileDAO;
    HashedLogin[] testLogins;
    ObjectMapper mockObjectMapper;

    /**
     * Create and inject a Mock Object Mapper to isolate the tests
     * from the underlying file
     * @throws IOException
     */
    @BeforeEach
    public void setupMilkFileDAO() throws IOException
    {
        mockObjectMapper = mock(ObjectMapper.class);
        testLogins = new HashedLogin[]{new HashedLogin("admin","92668751")}; // username: admin, password: admin

        // When the object mapper is supposed to read from the file
        // the mock object mapper will return the hero array above
        when(mockObjectMapper.readValue(new File("random.txt"), HashedLogin[].class)).thenReturn(testLogins);
        loginFileDAO = new LoginFileDAO("random.txt", mockObjectMapper);
    }

    @Test
    public void testLogin() throws IOException {
        String username = "admin";
        String password = "admin";

        String result = loginFileDAO.login(new Login(username, password));
        assertEquals("admin", result);
    }
    @Test
    public void testLoginWrongPassword() throws IOException {
        String username = "admin";
        String password = "admin1";

        String result = loginFileDAO.login(new Login(username, password));
        assertEquals(null, result);
    }
    @Test
    public void testLoginEmptyField() throws IOException {
        String username = "";
        String password = "admin";

        String result = loginFileDAO.login(new Login(username, password));
        assertEquals(null, result);
    }

    @Test
    public void testSignup() throws IOException {
        String username = "admin1";
        String password = "admin";

        String result = loginFileDAO.signup(new Login(username, password));
        assertEquals("admin1", result);
    }
    @Test
    public void testSignupExistingUsername() throws IOException {
        String username = "admin";
        String password = "admin";

        String result = loginFileDAO.signup(new Login(username, password));
        assertEquals(null, result);
    }
    @Test
    public void testSignupEmptyField() throws IOException {
        String username = null;
        String password = "admin";

        String result = loginFileDAO.signup(new Login(username, password));
        assertEquals(null, result);
    }

    @Test
    public void testResetPassword() throws IOException {
        String username = "admin";

        boolean result = loginFileDAO.resetPassword(username);
        assertEquals(true, result);
    }
    @Test
    public void testResetPasswordEmptyField() throws IOException {
        String username = null;

        boolean result = loginFileDAO.resetPassword(username);
        assertEquals(false, result);
    }
}
