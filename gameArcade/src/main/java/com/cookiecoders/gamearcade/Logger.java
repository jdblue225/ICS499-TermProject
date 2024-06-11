/*
    This logger class is designed as a singleton instance to be called
    from any class within the program with:

    private Logger logger = Logger.getInstance();

    After retrieving the instance, the logger can be called with:

    logger.log(Logger.LogLevel.<loglevel>, "Message");

 */

package com.cookiecoders.gamearcade;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.File;

public class Logger {
    private static Logger instance;
    private final String logFilePath;
    private final long maxSizeBytes;
    private long currentSizeBytes;
    private String currentLogDate;


    private Logger(String logFilePath, long maxSizeBytes) {
        this.logFilePath = logFilePath;
        this.maxSizeBytes = maxSizeBytes;
        this.currentSizeBytes = 0;
        this.currentLogDate = getCurrentDate();
    }

    public static Logger getInstance(){
        if (instance == null) {
            instance = new Logger("log/arcade_app.log", 200);
        }
        return instance;
    }

    public static Logger getInstance(String logFilePath, long maxSizeBytes) {
        if (instance == null) {
            instance = new Logger(logFilePath, maxSizeBytes);
        }
        return instance;
    }

    public enum LogLevel {
        INFO, WARNING, ERROR
    }

    // Log a message to the file with a timestamp and log level.
    public void log(LogLevel level, String message) {
        String logMessage = level + ": " + message;
        log(logMessage);
    }

    // Log a message to the file with a timestamp.
    private void log(String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFilePath, true)) ) {
            String timestamp = getTimestamp();
            String logEntry = timestamp + " - " + message;
            writer.write(logEntry);
            writer.newLine();

            // Update the current log file size
            currentSizeBytes += logEntry.length();

            // Check if the log file has reached the size limit or if a new day has started, and create a new log file if needed
            if (currentSizeBytes >= maxSizeBytes || !getCurrentDate().equals(currentLogDate)) {
                createNewLogFile();
                currentSizeBytes = 0;
                currentLogDate = getCurrentDate();
            }
        } catch (IOException e) {
            // Handle the exception, e.g., print an error message or throw it further.
            e.printStackTrace();
        }
    }

    private String getTimestamp() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        return dateFormat.format(now);
    }

    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        return dateFormat.format(now);
    }

    private void createNewLogFile() throws IOException {
        String newLogFilePath = logFilePath + "_" + System.currentTimeMillis() + ".log";
        File newLogFile = new File(newLogFilePath);
        if (newLogFile.createNewFile()) {
            // Successfully created a new log file
        } else {
            // Handle the case where creating a new log file failed
            throw new IOException("Failed to create a new log file.");
        }
    }
}
