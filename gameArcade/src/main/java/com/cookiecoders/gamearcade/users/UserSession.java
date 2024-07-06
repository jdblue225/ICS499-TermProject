package com.cookiecoders.gamearcade.users;

import com.cookiecoders.gamearcade.database.models.User;

public class UserSession {
    private static UserSession instance;
    private User currentUser;

    // Private constructor to prevent instantiation
    private UserSession() {

    }

    // Public method to provide access to the instance
    public static synchronized UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    // Method to get the current user
    public User getCurrentUser() {
        return currentUser;
    }

    // Method to set the current user
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    // Method to clear the current user
    public void clearSession() {
        this.currentUser = null;
    }
}
