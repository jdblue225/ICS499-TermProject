package com.cookiecoders.gamearcade.config;

import com.cookiecoders.gamearcade.util.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ConfigManager {
    private static final String ALT_CONFIG_FILE = "/com/cookiecoders/gamearcade/config/alt.app.config";
    private static final String CONFIG_FILE = "/com/cookiecoders/gamearcade/config/app.config";
    private static Properties properties = new Properties();

    // Static block to load properties at class loading time
    static {
        try (InputStream input = ConfigManager.class.getResourceAsStream(ALT_CONFIG_FILE)) {
            if (input != null) {
                // Load the alt.app.config if it exists
                properties.load(input);
                Logger.getInstance().log(Logger.LogLevel.INFO,"Loaded configuration from " + String.valueOf(ALT_CONFIG_FILE));
            } else {
                // Fallback to app.config if alt.app.config is not found
                try (InputStream fallbackInput = ConfigManager.class.getResourceAsStream(CONFIG_FILE)) {
                    if (fallbackInput != null) {
                        properties.load(fallbackInput);
                        Logger.getInstance().log(Logger.LogLevel.INFO,"Loaded configuration from " + String.valueOf(CONFIG_FILE));
                    } else {
                        Logger.getInstance().log(Logger.LogLevel.INFO,"Sorry, unable to find " + String.valueOf(CONFIG_FILE));
                    }
                }
            }
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

    public static List<String> getEmailList(String emailListPath) {
        List<String> emailList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(emailListPath))) {
            String line;
            while ((line = br.readLine()) != null) {
                emailList.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return emailList;
    }
}
