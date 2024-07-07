package com.cookiecoders.gamearcade.security;
import com.cookiecoders.gamearcade.config.ConfigManager;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

    // Method to validate email against the domain/email address list
    public String validateInputEmail(String email) {

        // Regular expression for validating email
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        // Compile the regex
        Pattern pattern = Pattern.compile(emailRegex);

        // Check if the email matches the regex
        Matcher matcher = pattern.matcher(email);

        if (!matcher.matches()) {
            // Invalid email address
            return null;
        }

        String emailListPath = ConfigManager.getProperty("Email List");
        List<String> emailList = ConfigManager.getEmailList(emailListPath);

        List<String> approvedList = new ArrayList<>();
        List<String> bannedList = new ArrayList<>();

        for (String entry : emailList) {
            if (entry.startsWith("!")) {
                bannedList.add(entry.substring(1));
            } else {
                approvedList.add(entry);
            }
        }


        // Extract the domain from the email
        String domain = email.substring(email.indexOf('@') + 1);

        // Check against banned list
        for (String bannedDomain : bannedList) {
            if (email.equalsIgnoreCase(bannedDomain) || domain.equalsIgnoreCase(bannedDomain)) {
                // Email address or domain is banned
                return null;
            }
        }

        // Check against approved list
        for (String approvedEntry : approvedList) {
            if (email.equalsIgnoreCase(approvedEntry) || domain.equalsIgnoreCase(approvedEntry)) {
                return email;
            }
        }

        // Email address is not in the approved list
        return null;
    }


}
