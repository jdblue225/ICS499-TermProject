/*
    public class MainApp {
       public static void main(String[] args) {
            // Accessing properties
            String appName = ConfigManager.getProperty("app.name");
            String appVersion = ConfigManager.getProperty("app.version");

            System.out.println("Application Name: " + appName);
            System.out.println("Application Version: " + appVersion);

            // Accessing database configuration
            String dbUrl = ConfigManager.getProperty("database.url");
            String dbUsername = ConfigManager.getProperty("database.username");
            String dbPassword = ConfigManager.getProperty("database.password");

            System.out.println("Database URL: " + dbUrl);
            System.out.println("Database Username: " + dbUsername);
            System.out.println("Database Password: " + dbPassword);
        }
    }
 */

package com.cookiecoders.gamearcade.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
    private static final String CONFIG_FILE = "/config/application.properties";
    private static Properties properties = new Properties();

    // Static block to load properties at class loading time
    static {
        try (InputStream input = ConfigManager.class.getResourceAsStream(CONFIG_FILE)) {
            if (input == null) {
                System.out.println("Sorry, unable to find " + CONFIG_FILE);
            }
            // Load the properties file from the classpath
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // Private constructor to prevent instantiation
    private ConfigManager() {
    }

    // Method to get a property value
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    // Method to get a property value with a default value
    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    // Method to get all properties
    public static Properties getAllProperties() {
        return properties;
    }
}
