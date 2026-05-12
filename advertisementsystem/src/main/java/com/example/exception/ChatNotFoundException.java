package com.example.exception;

public class ChatNotFoundException extends RuntimeException {

    public ChatNotFoundException(String message) {
        super(message);
    }

    public ChatNotFoundException(int id) {
        super("Чат с ID " + id + " не найден");
    }
}