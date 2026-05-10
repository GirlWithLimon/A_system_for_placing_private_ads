package com.example.dto;

import java.time.LocalDate;

public class CommentsDTO {
    private String comment;
    private LocalDate date;

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
}
