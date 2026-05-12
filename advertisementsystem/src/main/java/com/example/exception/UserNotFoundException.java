package com.example.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(int id) {
        super("Пользователь с ID " + id + " не найден");
    }
}