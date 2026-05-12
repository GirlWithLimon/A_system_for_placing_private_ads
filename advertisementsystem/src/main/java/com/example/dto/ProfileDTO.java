package com.example.dto;

public class ProfileDTO {
    private String secondName;
    private String name;
    private String fatherName;
    private String address;
    private Integer number;

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
}
