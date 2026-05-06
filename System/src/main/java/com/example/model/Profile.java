package com.example.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "profile")
public class Profile implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="secondname",nullable = false)
    private String secondName;

    @Column(name="name",nullable = false)
    private String name;

    @Column(name="fathername")
    private String fatherName;

    @Column(name="adress")
    private String adress;

    @Column(name="number", nullable = false)
    private Integer number;

    @OneToOne (mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private User user;

    Profile(){}
    Profile(String secondName, String name, int number){
        this.secondName = secondName;
        this.name = name;
        this.number=number;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getSecondName() { return secondName; }
    public void setSecondName(String secondName) { this.secondName = secondName; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getFatherName() { return fatherName; }
    public void setFatherName(String fatherName) { this.fatherName = fatherName; }

    public String getAdress() { return adress; }
    public void setAdress(String adress) { this.adress = adress; }

    public int getNumber() { return number; }
    public void setNumber(int number) { this.number = number; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
