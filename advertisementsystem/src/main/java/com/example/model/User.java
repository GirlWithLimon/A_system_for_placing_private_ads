package com.example.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "user")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idprofile", nullable = false)
    private Profile profile;

    @Column(name="login",nullable = false)
    private String login;

    @Column(name="password",nullable = false)
    private String password;

    @Column(name="role",nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    public User() {}

    public User(String login, String password, Profile profile, UserRole role) {
        this.login = login;
        this.password = password;
        this.role = role;
        this.profile = profile;
        this.enabled = true;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role.getDisplayRole(); }
    public void setRole(UserRole role) {this.role = role; }

    public Profile getProfile() { return profile; }
    public void setProfile(Profile profile) { this.profile = profile; }

    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }
}
