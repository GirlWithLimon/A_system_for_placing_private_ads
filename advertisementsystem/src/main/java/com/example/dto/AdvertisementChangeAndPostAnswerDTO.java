package com.example.dto;

import com.example.model.AdvertisementStatus;
import com.example.model.ProductsCategory;

public class AdvertisementChangeAndPostAnswerDTO {
    private String title;
    private ProductsCategory category;
    private String description;
    private Double price;
    private AdvertisementStatus status;

    public AdvertisementChangeAndPostAnswerDTO(){}
    public AdvertisementChangeAndPostAnswerDTO(String title, ProductsCategory category, Double price, AdvertisementStatus status){
        this.title = title;
        this.category = category;
        this.price = price;
        this.status = status;
    }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public ProductsCategory getCategory() { return category; }
    public void setCategory(ProductsCategory category) { this.category = category; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public AdvertisementStatus getStatus() { return status; }
    public void setStatus(AdvertisementStatus status) { this.status = status; }
}
