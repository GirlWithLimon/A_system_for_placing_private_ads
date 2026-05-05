package com.example.model;

import java.io.Serializable;

public enum AdvertisementStatus implements Serializable {
    AKTIVE("Активный"),
    SALE("Продан"),
    CANCELLED("Отменен");

    private final String displayStatus;

    AdvertisementStatus(String status) {
        this.displayStatus = status;
    }
    public String getDisplayStatus() {
        return displayStatus;
    }

    @Override
    public String toString() {
        return displayStatus;
    }
}