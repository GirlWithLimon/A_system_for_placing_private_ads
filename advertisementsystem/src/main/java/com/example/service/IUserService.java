package com.example.service;

import com.example.model.Profile;
import com.example.model.User;
import com.example.model.UserRole;

public interface IUserService extends GenericService<User, Integer> {
    User findByUsername(String username);
    User registerNewUser(String username, String rawPassword, Profile profile, UserRole role);
}