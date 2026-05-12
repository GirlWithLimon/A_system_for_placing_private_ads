package com.example.dto;

import com.example.model.Advertisement;
import com.example.model.User;

import java.time.LocalDate;

public class ScoreDTO {
    private int id;
    private String buyer;
    private String advertisement;
    private int score;
    private String comment;
    private LocalDate date;

    public ScoreDTO() {
    }

    public ScoreDTO(int id, String buyer, String advertisement, int score, String comment, LocalDate date) {
        this.id = id;
        this.buyer = buyer;
        this.advertisement = advertisement;
        this.score = score;
        this.comment = comment;
        this.date = date;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getBuyer() {
        return buyer;
    }
    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public String getAdvertisement() {
        return advertisement;
    }
    public void setAdvertisement(String advertisement) {
        this.advertisement = advertisement;
    }

    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }

}
