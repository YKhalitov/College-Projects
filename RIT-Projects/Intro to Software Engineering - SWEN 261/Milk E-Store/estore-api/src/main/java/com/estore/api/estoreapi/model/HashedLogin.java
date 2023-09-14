package com.estore.api.estoreapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HashedLogin {
    @JsonProperty("username") private String username;
    @JsonProperty("password-hash") private String passwordHash;

    public HashedLogin(String username, String passwordHash) {
        this.username = username;
        this.passwordHash = passwordHash;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    @Override
    public boolean equals(Object object) {
        boolean result = false;
        if (object instanceof HashedLogin) {
            result = username.equals(((HashedLogin)object).username) && passwordHash.equals(((HashedLogin)object).passwordHash);
        }
        return result;
    }

    @Override
    public int hashCode() {
        return username.hashCode() + passwordHash.hashCode();
    }
}
