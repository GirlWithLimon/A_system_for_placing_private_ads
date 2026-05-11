package com.example.dto;

public class AdvertisementsUsersDTO {
    private String title;
    private String category;
    private Double price;

    public AdvertisementsUsersDTO(String title, String category, Double price) {
        this.title = title;
        this.category = category;
        this.price = price;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
}
