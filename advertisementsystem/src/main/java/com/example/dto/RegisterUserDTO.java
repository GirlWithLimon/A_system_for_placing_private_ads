package com.example.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class RegisterUserDTO {
    @NotBlank(message = "Заполните Фамилию")
    private String secondName;
    @NotBlank(message = "Заполните Имя")
    private String name;
    private String fatherName;
    private String address;
    @NotNull(message = "Поле номер обязательно")
    private String number;
    @NotBlank(message = "Поле логин обязательно")
    @Size(min = 3, max = 50, message = "Логин должен быть от 3 до 50 символов")
    private String login;
    @NotBlank(message = "Пароль обязателен")
    @Size(min = 6, message = "Пароль должен быть не менее 6 символов")
    private String password;

    public String getSecondName() { return secondName; }
    public void setSecondName(String secondName) { this.secondName = secondName; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getFatherName() { return fatherName; }
    public void setFatherName(String fatherName) { this.fatherName = fatherName; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }

    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}