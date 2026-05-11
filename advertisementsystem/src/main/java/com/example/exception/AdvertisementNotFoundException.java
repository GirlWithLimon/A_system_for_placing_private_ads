package com.example.exception;

public class AdvertisementNotFoundException extends RuntimeException {

    public AdvertisementNotFoundException(String message) {
        super(message);
    }

    public AdvertisementNotFoundException(int id) {
        super("Книга с ID " + id + " не найдена");
    }
}