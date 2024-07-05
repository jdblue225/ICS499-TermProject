/**
 *  The SQLConnection class is designed to function as a
 *  Singleton class which can be called throughout the
 *  package in order to reduce connection overhead at
 *  the server level.
 *
 *  NOTE: Global variables SQL_USERNAME and SQL_PASSWORD
 *        need to reflect the values of your MySQL install.
 *
 * @author Cookie Coders
 *
 */
package com.cookiecoders.gamearcade;
import java.sql.*;
import java.util.HashMap;

public class SQLConnection {
    private Logger logger = Logger.getInstance();
    private static SQLConnection instance;
    private Connection connection;
    private static final String DATABASE_NAME = "arcadeapp";
    private static final String SQL_USERNAME = "root";  // Edit username to reflect YOUR SQL database username
    private static final String SQL_PASSWORD = "password";  // Edit password to reflect YOUR SQL database password

    /**
     * This method is called from within the SQLConnection
     * class in order to establish a connection with the
     * SQL database.
     */
    private SQLConnection() {
        connect();
    }

    /**
     * This method is used to retrieve the current instance
     * of the SQLConnection class. If one exists, it returns
     * that.
     * @return instance
     */
    public static synchronized SQLConnection getInstance() {
        if (instance == null) {
            instance = new SQLConnection();
        }
        return instance;
    }

    /**
     * This method is called from within the SQLConnection class
     * in order to establish the connection with the SQL server.
     */
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
     * Method accessable throughout the package to handle closing the sql connection.
     */
    public void sqlConnectionClose(){
        try{
            connection.close();
        }catch (SQLException e) {
            System.out.println("Error closing connection to the database: " + e.getMessage());
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

            stmt.setString(1, ID);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("Password");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This method can be used from within the package to
     * insert new users into the SQL database.
     *
     * TODO
     *
     * @param username
     * @param firstName
     * @param lastName
     * @param email
     * @param hashPass
     * @param usertype
     */
    public void createUser(String username, String firstName, String lastName, String email, String hashPass, String usertype){

    }
}
