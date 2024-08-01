package com.cookiecoders.gamearcade.database;


import com.cookiecoders.gamearcade.config.ConfigManager;
import com.cookiecoders.gamearcade.util.Logger;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.*;

public class DatabaseManager {
    private com.cookiecoders.gamearcade.util.Logger logger = Logger.getInstance();
    private static DatabaseManager instance;
    private Connection connection;
    private static String DATABASE_URL;
    private static String DATABASE_NAME;
    private static String SQL_USERNAME;
    private static String SQL_PASSWORD;

    /**
     * This method is called from within the SQLConnection
     * class in order to establish a connection with the
     * SQL database.
     */
    private DatabaseManager() {
        connect();
    }

    /**
     * This method is used to retrieve the current instance
     * of the SQLConnection class. If one exists, it returns
     * that.
     * @return instance
     */
    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            DATABASE_URL = ConfigManager.getProperty("db_URL");
            DATABASE_NAME = ConfigManager.getProperty("db_name");
            SQL_USERNAME = ConfigManager.getProperty("db_uname");
            if(DATABASE_URL.equals("jdbc:mysql://127.0.0.1:3306/") || DATABASE_URL.equals("jdbc:mysql://localhost:3306/")){
                SQL_PASSWORD = ConfigManager.getProperty("db_pass");
            }else{
                SQL_PASSWORD = URLEncoder.encode(ConfigManager.getProperty("db_pass"), StandardCharsets.UTF_8);
            }
            instance = new DatabaseManager();
        }
        return instance;
    }
    /**
     * This method is called from within the SQLConnection class
     * in order to establish the connection with the SQL server.
     */

    private void connect() {
        try {
            String url = DATABASE_URL + DATABASE_NAME + "?user=" + SQL_USERNAME + "&password=" + SQL_PASSWORD;
            connection = DriverManager.getConnection(url);
            connection.isValid(5);
            logger.log(Logger.LogLevel.INFO, "Database connected successfully.");
        } catch (SQLException e) {
            System.out.println("Error connecting to the database: " + e.getMessage());
            connection = null;
        }
    }
    /**
     * Method accessable throughout the package to handle closing the sql connection.
     */
    public void connectionClose(){
        try{
            connection.close();
            logger.log(Logger.LogLevel.INFO, "Closing server connection.");
        }catch (SQLException e) {
            logger.log(Logger.LogLevel.ERROR,"Error closing connection to the database: " + e.getMessage());
            connection = null;
        }
    }
    public Connection getConnection(){
        if (this.connection == null){
            connect();
        }
        return this.connection;
    }

    public boolean verifyConnection() {
        try{
            if (this.connection != null && !connection.isClosed()){
                return connection.isValid(5);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public ResultSet runQuery(PreparedStatement stmt){
            try {
                ResultSet rs = stmt.executeQuery();
                return rs;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
    }
}