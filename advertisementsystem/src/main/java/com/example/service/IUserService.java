package com.example.service;

import com.example.dto.UserProfileScoreDTO;
import com.example.dto.UserScoreDTO;
import com.example.model.Profile;
import com.example.model.User;
import com.example.model.UserRole;

import java.util.List;

public interface IUserService extends GenericService<User, Integer> {
    User findByUsername(String username);
    User registerNewUser(String username, String rawPassword, Profile profile, UserRole role);
    List<UserScoreDTO> findUsersWithScore();
    UserProfileScoreDTO findUserItem(int id);
}