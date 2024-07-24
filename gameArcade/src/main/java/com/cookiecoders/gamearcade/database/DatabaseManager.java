package com.cookiecoders.gamearcade.database;


import com.cookiecoders.gamearcade.config.ConfigManager;
import com.cookiecoders.gamearcade.util.Logger;

import java.sql.*;

public class DatabaseManager {
    private com.cookiecoders.gamearcade.util.Logger logger = Logger.getInstance();
    private static DatabaseManager instance;
    private Connection connection;
//    private static final String DATABASE_NAME = "arcadeapp";
//    private static final String SQL_USERNAME = "root";  // Edit username to reflect YOUR SQL database username
//    private static final String SQL_PASSWORD = "password";  // Edit password to reflect YOUR SQL database password
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
            SQL_PASSWORD = ConfigManager.getProperty("db_pass");
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
        }catch (SQLException e) {
            System.out.println("Error closing connection to the database: " + e.getMessage());
            connection = null;
        }
    }
    public Connection getConnection(){
        if (this.connection == null){
            connect();
        }
        return this.connection;
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