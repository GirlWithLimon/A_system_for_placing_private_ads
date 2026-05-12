package com.example.exception;

public class MessageNotFoundException extends RuntimeException {

    public MessageNotFoundException(String message) {
        super(message);
    }

    public MessageNotFoundException(int id) {
        super("Сообщения для чата с ID " + id + " не найдены");
    }
}