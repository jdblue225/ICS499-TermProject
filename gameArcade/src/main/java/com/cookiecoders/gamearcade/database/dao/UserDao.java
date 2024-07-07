package com.cookiecoders.gamearcade.database.dao;

import com.cookiecoders.gamearcade.database.models.User;

import java.util.List;

public interface UserDao {
    boolean insertUser(User user);
//    User getUserById(int id);
    User getUserByUsername(String username);
//    List<User> getAllUsers();
//    void updateUser(User user);
//    void deleteUser(int id);

}