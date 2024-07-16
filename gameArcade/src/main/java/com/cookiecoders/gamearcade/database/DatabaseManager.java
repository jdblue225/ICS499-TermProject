package com.cookiecoders.gamearcade.database;

//import com.cookiecoders.gamearcade.SQLConnection;
import com.cookiecoders.gamearcade.util.Logger;

import java.sql.*;
import java.util.logging.Level;

//public class DatabaseManager {
//    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/arcadeapp";
//    private static final String JDBC_USER = "root";
//    private static final String JDBC_PASSWORD = "password";
//    private static final Logger LOGGER = Logger.getLogger(DatabaseManager.class.getName());
//
//    static {
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//        } catch (ClassNotFoundException e) {
//            LOGGER.log(Level.SEVERE, "Database driver not found", e);
//        }
//    }
//
//    public static Connection getConnection() throws SQLException {
//        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
//    }
//
//    public static void closeConnection(Connection connection) {
//        if (connection != null) {
//            try {
//                connection.close();
//            } catch (SQLException e) {
//                LOGGER.log(Level.SEVERE, "Failed to close database connection", e);
//            }
//        }
//    }
//}
public class DatabaseManager {
    private com.cookiecoders.gamearcade.util.Logger logger = Logger.getInstance();
    private static DatabaseManager instance;
    private Connection connection;
    private static final String DATABASE_NAME = "arcadeapp";
    private static final String SQL_USERNAME = "root";  // Edit username to reflect YOUR SQL database username
    private static final String SQL_PASSWORD = "password";  // Edit password to reflect YOUR SQL database password

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