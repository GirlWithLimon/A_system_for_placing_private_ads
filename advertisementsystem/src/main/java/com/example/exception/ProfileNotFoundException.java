package com.example.exception;

public class ProfileNotFoundException extends RuntimeException {

    public ProfileNotFoundException(String message) {
        super(message);
    }

    public ProfileNotFoundException(int id) {
        super("Профиль с ID " + id + " не найден");
    }
}