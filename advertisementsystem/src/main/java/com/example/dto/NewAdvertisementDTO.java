package com.example.dto;

import com.example.model.AdvertisementStatus;
import com.example.model.ProductsCategory;

public class NewAdvertisementDTO {
    private String title;
    private ProductsCategory category;
    private String description;
    private Double price;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public ProductsCategory getCategory() { return category; }
    public void setCategory(ProductsCategory category) { this.category = category; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
}
