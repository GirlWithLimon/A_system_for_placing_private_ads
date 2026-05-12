package com.example.dto;

import com.example.model.AdvertisementStatus;
import com.example.model.ProductsCategory;

import java.time.LocalDate;

public class AdvertisementAnswerDTO {
    private int id;
    private String title;
    private ProductsCategory category;
    private String description;
    private Double price;
    private AdvertisementStatus status;
    private LocalDate publicationDate;
    private String seller;
    private boolean byestatus;

    public AdvertisementAnswerDTO(){}
    public AdvertisementAnswerDTO(int id,
                                  String title,
                                  ProductsCategory category,
                                  Double price,
                                  AdvertisementStatus status,
                                  LocalDate publicationDate,
                                  String seller){
        this.id = id;
        this.title = title;
        this.category = category;
        this.price = price;
        this.status = status;
        this.publicationDate = publicationDate;
        this.seller = seller;
    }
    public AdvertisementAnswerDTO(int id,
                                  String title,
                                  ProductsCategory category,
                                  String description,
                                  Double price,
                                  AdvertisementStatus status,
                                  LocalDate publicationDate,
                                  String seller){
        this.id = id;
        this.title = title;
        this.category = category;
        this.description = description;
        this.price = price;
        this.status = status;
        this.publicationDate = publicationDate;
        this.seller = seller;
    }
    public AdvertisementAnswerDTO(int id,
                                  String title,
                                  ProductsCategory category,
                                  String description,
                                  Double price,
                                  AdvertisementStatus status,
                                  LocalDate publicationDate,
                                  String seller,
                                  boolean byestatus){
        this.id = id;
        this.title = title;
        this.category = category;
        this.description = description;
        this.price = price;
        this.status = status;
        this.publicationDate = publicationDate;
        this.seller = seller;
        this.byestatus = byestatus;
    }
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

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

    public LocalDate getPublicationDate() { return publicationDate; }
    public void setPublicationDate(LocalDate publicationDate) { this.publicationDate = publicationDate; }

    public String getSeller() { return seller; }
    public void setSeller(String seller) { this.seller = seller; }

    public boolean getByeStatus() { return byestatus; }
    public void setByestatus(boolean byestatus) { this.byestatus = byestatus; }

}
