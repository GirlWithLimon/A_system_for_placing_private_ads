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
    @JoinColumn(name = "idseller", nullable = false)
    private User seller;

    @Column(name="title",nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name="category",nullable = false)
    private ProductsCategory category;

    @Column(name="description")
    private String description;

    @Column(name="publicationdate", nullable = false)
    private LocalDate publicationDate;

    @Column(name="price", nullable = false)
    private double price;

    @Column (name = "byestatus",nullable = false)
    private boolean byeStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private AdvertisementStatus status;

    public Advertisement(){}
    public Advertisement(int id, String title, ProductsCategory category, double price){
        this.id = id;
        this.title = title;
        this.category = category;
        this.publicationDate = LocalDate.now();
        this.price = price;
        this.byeStatus = false;
        this.status = AdvertisementStatus.AСTIVE;
    }
    public Advertisement(int id, String title, ProductsCategory category, String description, double price){
        this.id = id;
        this.title = title;
        this.category = category;
        this.description = description;
        this.publicationDate = LocalDate.now();
        this.price = price;
        this.byeStatus = false;
        this.status = AdvertisementStatus.AСTIVE;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public ProductsCategory getCategory() { return category; }
    public void setCategory(ProductsCategory category) { this.category = category; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDate getPublicationDate() { return publicationDate; }
    public void setPublicationDate(LocalDate publicationDate) { this.publicationDate = publicationDate; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public boolean getByeStatus() { return byeStatus; }
    public void setByeStatus(boolean byeStatus) { this.byeStatus = byeStatus; }

    public AdvertisementStatus getStatus() { return status; }
    public void setStatus(AdvertisementStatus status) { this.status = status; }
}
