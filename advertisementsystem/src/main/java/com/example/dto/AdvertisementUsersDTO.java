package com.example.dto;

import com.example.model.User;

import java.util.List;

public class AdvertisementUsersDTO {
    private String title;
    private String category;
    private String description;
    private Double price;
    private boolean byestatus;
    private List<CommentsDTO> comments;


    AdvertisementUsersDTO(){}

    public AdvertisementUsersDTO(String title, String category, String description, Double price, Boolean byestatus) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.price = price;
        this.byestatus = byestatus;
    }

    public AdvertisementUsersDTO(String title, String category, Double price) {
        this.title = title;
        this.category = category;
        this.price = price;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public boolean getByeStatus() { return byestatus; }
    public void setByestatus(boolean byestatus) { this.byestatus = byestatus; }

    public List<CommentsDTO> getComments() { return comments; }
    public void setComments(List<CommentsDTO> comments) { this.comments = comments; }
}
