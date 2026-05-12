package com.example.exception;

public class ScoreNotFoundException extends RuntimeException {

    public ScoreNotFoundException(String message) {
        super(message);
    }

    public ScoreNotFoundException(int id) {
        super("Оценка с ID " + id + " не найдена");
    }
}