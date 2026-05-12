package com.example.service;

import com.example.dto.ScoreDTO;
import com.example.model.Score;

import java.util.List;

public interface IScoreService extends GenericService<Score, Integer> {
    List<ScoreDTO> findAllScoreUsers(int iduser);
}