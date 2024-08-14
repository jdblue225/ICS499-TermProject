package com.cookiecoders.gamearcade.users;

import com.cookiecoders.gamearcade.database.models.User;

import java.util.UUID;

public class UserSession {
    private static UserSession instance;
    private String sessionID;
    private User currentUser;

    // Private constructor to prevent instantiation
    private UserSession() {
        generateSessionID();
    }

    // Public method to provide access to the instance
    public static synchronized UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    private void generateSessionID(){
        this.sessionID = UUID.randomUUID().toString();
    }

    // Method to get the current user
    public User getCurrentUser() {
        return currentUser;
    }
    public String getSessionID(){
        return this.sessionID;
    }

    // Method to set the current user
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    // Method to clear the current user
    public void clearSession() {
        this.currentUser = null;
        this.sessionID = null;
        this.instance = null;
    }
}
