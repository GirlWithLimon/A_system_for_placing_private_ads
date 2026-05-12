package com.example.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class NewScoreDTO {
    private int idadvertisement;
    @Min(value = 1, message = "Оценка должна быть от 1 до 5")
    @Max(value = 5, message = "Оценка должна быть от 1 до 5")
    private int score;
    @NotBlank(message = "Комментарий не может быть пустым")
    private String comment;

    public NewScoreDTO() {
    }

    public NewScoreDTO(int idadvertisement, int score, String comment) {
        this.idadvertisement = idadvertisement;
        this.score = score;
        this.comment = comment;
    }
    public int getIdadvertisement() {
        return idadvertisement;
    }
    public void setIdadvertisement(int idadvertisement) {
        this.idadvertisement = idadvertisement;
    }

    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }

}
