package com.cookiecoders.gamearcade;
import java.sql.*;
import java.util.HashMap;

/**
 *  Singleton SQLConnection class to handle the connection to
 *  the local SQL database.
 */
public class SQLConnection {
    private Logger logger = Logger.getInstance();
    private static SQLConnection instance;
    private Connection connection;
    private static final String DATABASE_NAME = "arcadeapp";
    private static final String SQL_USERNAME = "root";  // Edit username to reflect YOUR SQL database username
    private static final String SQL_PASSWORD = "password";  // Edit password to reflect YOUR SQL database password

    // Private constructor to prevent instantiation
    private SQLConnection() {
        connect();
    }

    // Public method to get the instance of the class
    public static synchronized SQLConnection getInstance() {
        if (instance == null) {
            instance = new SQLConnection();
        }
        return instance;
    }

    // Method to establish a database connection

    private void connect() {
        try {
            String url = "jdbc:mysql://localhost/" + DATABASE_NAME + "?user=" + SQL_USERNAME + "&password=" + SQL_PASSWORD;
            connection = DriverManager.getConnection(url);
            logger.log(Logger.LogLevel.INFO, "Database connected successfully.");
        } catch (SQLException e) {
            System.out.println("Error connecting to the database: " + e.getMessage());
            connection = null;
        }
    }
    /**
     * getPasswordForUserID method takes userID passed to it and returns
     * the password from the users SQL table.
     *
     * @param ID
     * @return String password
     */
    public String getPasswordForUserID(String ID) {
        String query = "SELECT password FROM users WHERE username = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, ID);  // Set the userID in the prepared statement

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("Password");  // Return the password
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Handle any SQL exceptions
        }
        return null;  // Return null if the password is not found or an error occurs
    }
    public void createUser(String username, String firstName, String lastName, String email, String hashPass, String usertype){

    }
}
