package com.example.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "chat")
public class Chat implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idseller", nullable = false)
    private User seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idbuyer", nullable = false)
    private User buyer;

    public Chat(){}
    public Chat(User seller,User buyer){
        this.seller = seller;
        this.buyer = buyer;
    }
    public Chat(int id,User seller,User buyer){
        this.id=id;
        this.seller = seller;
        this.buyer = buyer;
    }
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public User getSeller() { return seller; }
    public void setSeller(User seller) { this.seller = seller; }

    public User getBuyer() { return buyer; }
    public void setBuyer(User buyer) { this.buyer = buyer; }
}
