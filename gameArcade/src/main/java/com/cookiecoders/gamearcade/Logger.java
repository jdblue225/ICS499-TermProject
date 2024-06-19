/**
 *     This logger class is designed as a singleton instance to be called
 *     from any class within the package. It crates a log file in the log
 *     directory and creates new log file if the size grows too large or if
 *     the program is run on a new day.
 *
 *     Usage:
 *         Instantiate:
 *              private Logger = Logger.getInstance();
 *         Call:
 *              logger.log(Logger.LogLevel.<loglevel>, "Message");
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

    /**
     * This is the constructor for the logger class and is only called
     * once from the main method.
     * @param logFilePath
     * @param maxSizeBytes
     */
    private Logger(String logFilePath, long maxSizeBytes) {
        this.logFilePath = logFilePath;
        this.maxSizeBytes = maxSizeBytes;
        this.currentSizeBytes = 0;
        this.currentLogDate = getCurrentDate();
    }

    /**
     * This method retrieves the active instance of this singleton
     * class. The first time this method is called, it constructs the
     * class. All subsequent calls, it only returns the current instance.
     * @return instance
     */
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

    /**
     * This enum method is used to define log levels.
     */
    public enum LogLevel {
        INFO, WARNING, ERROR
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
                currentSizeBytes = 0;
                currentLogDate = getCurrentDate();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is called within the logger class to
     * retrieve the current timestamp in "yyyy-MM-dd HH:mm:ss"
     * format.
     * @return timeStamp
     */
    private String getTimestamp() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        return dateFormat.format(now);
    }

    /**
     * This method is called from within the logger class to
     * retrieve the current timestamp in "yyyy-MM-dd" format.
     * @return timeStamp
     */
    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        return dateFormat.format(now);
    }

    /**
     * This method is called from within the logger class and is used to create a new
     * log file.
     * @throws IOException
     */
    private void createNewLogFile() throws IOException {
        String newLogFilePath = logFilePath + "_" + System.currentTimeMillis() + ".log";
        File newLogFile = new File(newLogFilePath);
        if (newLogFile.createNewFile()) {
        } else {
            throw new IOException("Failed to create a new log file.");
        }
    }
}
