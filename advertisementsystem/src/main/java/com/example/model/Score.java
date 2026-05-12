package com.example.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "score")
public class Score implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idseller", nullable = false)
    private User seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idbuyer", nullable = false)
    private User buyer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idadvertisement", nullable = false)
    private Advertisement advertisement;

    @Column(name = "score", nullable = false)
    private int score;

    @Column(name="comment",nullable = false)
    private String comment;

    @Column(name="date",nullable = false)
    private LocalDate date;

    public Score(){}
    public Score(User seller, User buyer, Advertisement advertisement, int score, String comment){
        this.seller = seller;
        this.buyer = buyer;
        this.advertisement = advertisement;
        this.score = score;
        this.comment = comment;
        this.date = LocalDate.now();
    }
    public Score(int id,User seller, User buyer, Advertisement advertisement, int score, String comment){
        this.id = id;
        this.seller = seller;
        this.buyer = buyer;
        this.advertisement = advertisement;
        this.score = score;
        this.comment = comment;
        this.date = LocalDate.now();
    }


    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public User getSeller() { return seller; }
    public void setSeller(User seller) { this.seller = seller; }

    public User getBuyer() { return buyer; }
    public void setBuyer(User buyer) { this.buyer = buyer; }

    public Advertisement getAdvertisement() { return advertisement; }
    public void setAdvertisement(Advertisement advertisement) { this.advertisement = advertisement; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
}
