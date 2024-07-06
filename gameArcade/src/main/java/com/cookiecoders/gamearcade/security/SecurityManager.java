package com.cookiecoders.gamearcade.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class SecurityManager {


    /**
     * This function takes a string and hashes it
     * via the SHA-256 algorithm.
     * @param stringToHash
     * @return hashedString
     */
    public static String hashString(String stringToHash){
        String hashedString ="";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(stringToHash.getBytes());

            StringBuilder hexString = new StringBuilder();
            for (byte b : hashedBytes) {
                hexString.append(String.format("%02x", b));
            }
            hashedString = hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hashedString;
    }

    // Method to authenticate a user
    public static boolean authenticate(String enteredPasswordHash, String dbPassword) {
        String hashedPassword = enteredPasswordHash;
        return hashedPassword.equals(dbPassword);
    }

    // Method to encrypt data (example using a simple encryption technique)
    public static String encrypt(String data, String key) {
        // Simple XOR encryption (for demonstration purposes only)
        char[] keyChars = key.toCharArray();
        char[] dataChars = data.toCharArray();
        char[] encryptedData = new char[data.length()];

        for (int i = 0; i < data.length(); i++) {
            encryptedData[i] = (char) (dataChars[i] ^ keyChars[i % keyChars.length]);
        }

        return Base64.getEncoder().encodeToString(new String(encryptedData).getBytes());
    }

    // Method to decrypt data (example using a simple decryption technique)
    public static String decrypt(String encryptedData, String key) {
        // Simple XOR decryption (for demonstration purposes only)
        char[] keyChars = key.toCharArray();
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedData);
        char[] dataChars = new String(decodedBytes).toCharArray();
        char[] decryptedData = new char[dataChars.length];

        for (int i = 0; i < dataChars.length; i++) {
            decryptedData[i] = (char) (dataChars[i] ^ keyChars[i % keyChars.length]);
        }

        return new String(decryptedData);
    }

    // Method to check if a user has a specific role
    public static boolean hasRole(String userRole, String requiredRole) {
        return userRole.equals(requiredRole);
    }

    // Method to check if a user has any role from a list of required roles
    public static boolean hasAnyRole(String userRole, String[] requiredRoles) {
        for (String role : requiredRoles) {
            if (userRole.equals(role)) {
                return true;
            }
        }
        return false;
    }

    // Method to validate a password strength
    public static boolean validatePasswordStrength(String password) {
        // Simple example: password must be at least 8 characters long
        return password.length() >= 8;
    }
}
