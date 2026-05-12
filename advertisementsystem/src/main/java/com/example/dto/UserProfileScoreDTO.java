package com.example.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UserProfileScoreDTO {
    private String secondName;
    private String name;
    private String fatherName;
    private String address;
    private Integer number;
    private String seller;
    private Double score;

    public String getSecondName() { return secondName; }
    public void setSecondName(String secondName) { this.secondName = secondName; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getFatherName() { return fatherName; }
    public void setFatherName(String fatherName) { this.fatherName = fatherName; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public Integer getNumber() { return number; }
    public void setNumber(Integer number) { this.number = number; }

    public String getSeller() { return seller; }
    public void setSeller(String seller) { this.seller = seller; }

    public Double getScore() { return score; }
    public void setScore(Double score) { this.score = score; }
}
