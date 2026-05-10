package com.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public enum ProductsCategory implements Serializable {
    @JsonProperty("Машина")
    CAR("Машина"),
    @JsonProperty("Цветок")
    FLOWER("Цветок");

    private final String displayCategory;

    ProductsCategory(String category) {
        this.displayCategory = category;
    }
    public String getDisplayCategory() {
        return displayCategory;
    }

    @Override
    public String toString() {
        return displayCategory;
    }
}
