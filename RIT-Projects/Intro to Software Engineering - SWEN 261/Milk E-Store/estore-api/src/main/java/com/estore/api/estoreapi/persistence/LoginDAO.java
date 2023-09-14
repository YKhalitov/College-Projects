package com.estore.api.estoreapi.persistence;

import java.io.IOException;

import com.estore.api.estoreapi.model.Login;

public interface LoginDAO {
    String login(Login login) throws IOException;
    String signup(Login login) throws IOException;
    boolean resetPassword(String username) throws IOException;
}
