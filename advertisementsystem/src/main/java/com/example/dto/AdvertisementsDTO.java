package com.example.dto;

public class AdvertisementsDTO {
    private int id;
    private String title;
    private String category;
    private Double price;
    private String seller;
    private Double score;

    AdvertisementsDTO(){}

    public AdvertisementsDTO(int id, String title, String category, Double price, String seller, Double score) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.price = price;
        this.seller = seller;
        this.score = score;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public String getSeller() { return seller; }
    public void setSeller(String seller) { this.seller = seller; }

    public Double getScore() { return score; }
    public void setScore(Double score) { this.score = score; }


}
