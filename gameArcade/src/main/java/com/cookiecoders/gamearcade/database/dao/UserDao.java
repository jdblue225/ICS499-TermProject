package com.cookiecoders.gamearcade.database.dao;

import com.cookiecoders.gamearcade.database.models.User;

import java.util.List;

public interface UserDao {
    void addUser(User user);
    User getUser(int id);
    List<User> getAllUsers();
    void updateUser(User user);
    void deleteUser(int id);
}