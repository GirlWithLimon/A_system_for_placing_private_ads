package com.example.model;

import java.io.Serializable;

public enum ProductsCategory implements Serializable {
    CAR("Машина"),
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
