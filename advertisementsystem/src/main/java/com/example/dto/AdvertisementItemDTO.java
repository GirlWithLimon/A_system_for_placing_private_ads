package com.example.dto;

import java.util.List;

public class AdvertisementItemDTO {
    private int id;
    private String title;
    private String description;
    private String category;
    private Double price;
    private String seller;
    private Double score;
    private List<CommentsDTO> comments;


    AdvertisementItemDTO(){}

    public AdvertisementItemDTO(int id, String title, String description, String category, Double price, String seller, Double score) {
        this.id=id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.price = price;
        this.seller = seller;
        this.score = score;
    }

    public AdvertisementItemDTO(int id, String title, String category, Double price, String seller, Double score) {
        this.id=id;
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

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public String getSeller() { return seller; }
    public void setSeller(String seller) { this.seller = seller; }

    public Double getScore() { return score; }
    public void setScore(Double score) { this.score = score; }

    public List<CommentsDTO> getComments() { return comments; }
    public void setComments(List<CommentsDTO> comments) { this.comments = comments; }
}
