package com.cookiecoders.gamearcade.util;

import com.cookiecoders.gamearcade.config.ConfigManager;
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

    /**
     * Constructor for the logger class and is only called
     * once from the main method.
     * @param logFilePath
     * @param maxSizeBytes
     */
    private Logger(String logFilePath, long maxSizeBytes) {
        this.logFilePath = logFilePath;
        this.maxSizeBytes = maxSizeBytes;
        this.currentSizeBytes = new File(logFilePath).length();
        this.currentLogDate = getCurrentDate();
    }

    /**
     * Retrieves the active instance of this singleton
     * class. The first time this method is called, it constructs the
     * class. All subsequent calls, it only returns the current instance.
     * @return instance
     */
    public static Logger getInstance() {
        if (instance == null) {
            String logFilePath = ConfigManager.getProperty("log.file.path", "log/arcade_app.log");
            long maxSizeBytes = Long.parseLong(ConfigManager.getProperty("log.file.maxsize", "1048576"));
            instance = new Logger(logFilePath, maxSizeBytes);
        }
        return instance;
    }

    /**
     * This enum method is used to define log levels.
     */
    public enum LogLevel {
        INFO, WARNING, CRITICAL, ERROR
    }

    /**
     * This method is called from within the package and is used
     * to add log entries to the log file.
     * @param level
     * @param message
     */
    public void log(LogLevel level, String message) {
        String logMessage = level + ": " + message;
        log(logMessage);
    }

    /**
     * This method is called from within the logger class
     * and is used to write log entries to the log.
     * @param message
     */
    private void log(String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFilePath, true)) ) {
            String timestamp = getTimestamp();
            String logEntry = timestamp + " - " + message;
            writer.write(logEntry);
            writer.newLine();

            currentSizeBytes += logEntry.length();

            if (currentSizeBytes >= maxSizeBytes || !getCurrentDate().equals(currentLogDate)) {
                createNewLogFile();
                currentSizeBytes = new File(logFilePath).length();
                currentLogDate = getCurrentDate();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the current timestamp in "yyyy-MM-dd HH:mm:ss"
     * format.
     * @return timeStamp
     */
    private String getTimestamp() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        return dateFormat.format(now);
    }

    /**
     * Retrieves the current timestamp in "yyyy-MM-dd" format.
     * @return timeStamp
     */
    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        return dateFormat.format(now);
    }

    /**
     * Creates a new log file.
     * @throws IOException
     */
    private void createNewLogFile() throws IOException {
        String newLogFilePath = logFilePath + "_" + System.currentTimeMillis() + ".log";
        File newLogFile = new File(newLogFilePath);
        if (newLogFile.createNewFile()) {
            System.out.println("Created new log file: " + newLogFilePath);
        } else {
            throw new IOException("Failed to create a new log file.");
        }
    }
}
