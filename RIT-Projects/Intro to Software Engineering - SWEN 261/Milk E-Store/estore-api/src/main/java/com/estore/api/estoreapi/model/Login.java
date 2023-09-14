package com.estore.api.estoreapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Login {
    @JsonProperty("username") private String username;
    @JsonProperty("password") private String password;

    public Login(@JsonProperty("username") String username, @JsonProperty("password") String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object object) {
        boolean result = false;
        if (object instanceof Login) {
            result = username == ((Login)object).username && password == ((Login)object).password;
        }
        return result;
    }

    @Override
    public int hashCode() {
        return username.hashCode() + password.hashCode();
    }
}
