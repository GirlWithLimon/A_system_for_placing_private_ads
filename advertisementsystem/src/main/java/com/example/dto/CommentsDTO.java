package com.example.dto;

import java.time.LocalDate;

public class CommentsDTO {
    private String comment;
    private LocalDate date;

    public CommentsDTO() { }
    public CommentsDTO(String comment, LocalDate date) {
        this.comment = comment;
        this.date = date;
    }
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
}
