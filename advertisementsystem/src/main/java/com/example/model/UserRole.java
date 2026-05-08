package com.example.model;

import java.io.Serializable;

public enum UserRole implements Serializable {
    USER("Пользователь"),
    ADMIN("Администратор");

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
