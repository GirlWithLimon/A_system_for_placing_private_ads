package com.example.service;

import com.example.model.User;
import com.example.model.UserRole;

public interface IUserService extends GenericService<User, Integer> {
    User findByUsername(String username);
    User registerNewUser(String username, String rawPassword, UserRole role);
}