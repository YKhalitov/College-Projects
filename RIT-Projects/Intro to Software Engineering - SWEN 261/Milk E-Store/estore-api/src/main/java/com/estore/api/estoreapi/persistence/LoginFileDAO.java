package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.estore.api.estoreapi.model.HashedLogin;
import com.estore.api.estoreapi.model.Login;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class LoginFileDAO implements LoginDAO {
    Map<String,String> logins;  // Provides a local cache of the login pairs
                                // so that we don't need to read from the file
                                // each 
    private ObjectMapper objectMapper;  // Provides conversion between Milk
                                        // objects and JSON text format written
                                        // to the file
    private String filename; // Filename for logins

    public LoginFileDAO(@Value("${logins.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.objectMapper = objectMapper;
        this.filename = filename;
        load();
    }

    private boolean load() throws IOException {
        logins = new TreeMap<>();

        HashedLogin[] loginArray = objectMapper.readValue(new File(filename), HashedLogin[].class);
        for (HashedLogin login : loginArray) {
            logins.put(login.getUsername(), login.getPasswordHash());
        }

        return true;
    }

    private HashedLogin[] getHashedLogins() {
        ArrayList<HashedLogin> loginArrayList = new ArrayList<>();
        
        for (Map.Entry<String,String> entry : logins.entrySet()) {
            HashedLogin hl = new HashedLogin(entry.getKey(), entry.getValue());
            loginArrayList.add(hl);
        }

        HashedLogin[] loginArray = new HashedLogin[loginArrayList.size()];
        loginArrayList.toArray(loginArray);
        return loginArray;
    }

    private void save() throws IOException {
        HashedLogin[] loginArray = getHashedLogins();

        objectMapper.writeValue(new File(filename), loginArray);
    }

    @Override
    public String login(Login login) throws IOException {
        if (login.getUsername() == null || login.getPassword() == null || !logins.containsKey(login.getUsername())) {
            return null;
        }
        if (logins.get(login.getUsername()).equals(String.valueOf(login.getPassword().hashCode()))) {
            return login.getUsername();
        }
        return null;
    }

    public String signup(Login login) throws IOException {
        if (login.getUsername() == null || login.getPassword() == null) {
            return null;
        }
        if (!logins.containsKey(login.getUsername())) {
            logins.put(login.getUsername(), String.valueOf(login.getPassword().hashCode()));
            save();
            return login.getUsername();
        }
        return null;
    }

    @Override
    public boolean resetPassword(String username) throws IOException {
        if (username == null) {
            return false;
        }
        logins.remove(username);
        save();
        return true;
    }
}
