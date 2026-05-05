package com.example.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "advertisement")
public class Advertisement implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", nullable = false)
    private User seller;

    @Column(name="title",nullable = false)
    private String title;

    @Column(name="description")
    private String description;

    @Column(name="publicationdate", nullable = false)
    private LocalDate publicationDate;

    @Column(name="price", nullable = false)
    private double price;

    @Column (name = "byeStatus",nullable = false)
    private boolean byeStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private AdvertisementStatus status;

    public Advertisement(){}
    public Advertisement(String title, double price){
        this.title = title;
        this.publicationDate = LocalDate.now();
        this.price = price;
        this.byeStatus = false;
        this.status = AdvertisementStatus.AKTIVE;
    }
    public Advertisement(String title, String description, double price){
        this.title = title;
        this.description = description;
        this.publicationDate = LocalDate.now();
        this.price = price;
        this.byeStatus = false;
        this.status = AdvertisementStatus.AKTIVE;
    }

}
