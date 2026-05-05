package com.example.model;

import java.io.Serializable;

public enum UserRole implements Serializable {
    User("Пользователь"),
    Admin("Администратор");

    private final String displayRole;

    UserRole(String role) {
        this.displayRole = role;
    }
    public String getDisplayRole() {
        return displayRole;
    }

    @Override
    public String toString() {
        return displayRole;
    }
}
