package com.example.service;

import com.example.dto.CommentsDTO;
import com.example.model.Comments;

import java.util.List;

public interface ICommentsService extends GenericService<Comments, Integer> {
    List<CommentsDTO> findAdvertisementComments(int idadvertisement);
}