package com.cookiecoders.gamearcade.database.dao;
// TODO Clean up extra methods
import com.cookiecoders.gamearcade.database.DatabaseManager;
import com.cookiecoders.gamearcade.database.models.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {

    @Override
    public void updateUser(User user) {
        String query = "UPDATE users SET username = ?, firstname = ?, lastname = ?, email = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getFirstname());
            statement.setString(3, user.getLastname());
            statement.setString(4, user.getEmail());
            statement.setInt(5, user.getId()); // Assuming you have an ID column
            int rowsUpdated = statement.executeUpdate();
            System.out.println("Rows updated: " + rowsUpdated); // Log the number of rows updated
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private static DatabaseManager dbm = DatabaseManager.getInstance();
    private static Connection connection = dbm.getConnection();


    public UserDaoImpl(){}

    // Constructor for injecting connection (used in tests)
    public UserDaoImpl(Connection connection) {
        this.connection = connection;
    }

//    @Override
//    public void addUser(User user) {
//        String query = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
//        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
//             PreparedStatement statement = connection.prepareStatement(query)) {
//            statement.setString(1, user.getUsername());
//            statement.setString(2, user.getPassword());
//            statement.setString(3, user.getEmail());
//            statement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

//    @Override
//    public User getUserById(int id) {
//        String query = "SELECT * FROM users WHERE id = ?";
//        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
//             PreparedStatement statement = connection.prepareStatement(query)) {
//            statement.setInt(1, id);
//            ResultSet resultSet = statement.executeQuery();
//            if (resultSet.next()) {
//                User user = new User();
//                user.setId(resultSet.getInt("id"));
//                user.setUsername(resultSet.getString("username"));
//                user.setPassword(resultSet.getString("password"));
//                user.setEmail(resultSet.getString("email"));
//                return user;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }



    @Override
    public User getUserByUsername(String username) {
        String hashedPass;
        String query = "SELECT * FROM Users WHERE UserName = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    User user = new User(
                            resultSet.getInt("UserID"),
                            resultSet.getString("UserName"),
                            resultSet.getString("FirstName"),
                            resultSet.getString("LastName"),
                            resultSet.getString("Email"),
                            resultSet.getString("Password"),
                            resultSet.getString("UserType"),
                            resultSet.getDate("CreateDate"),
                            resultSet.getString("ImageName"),
                            resultSet.getBytes("Image")
                    );
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

//    @Override
//    public List<User> getAllUsers() {
//        String query = "SELECT * FROM users";
//        List<User> users = new ArrayList<>();
//        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
//             PreparedStatement statement = connection.prepareStatement(query);
//             ResultSet resultSet = statement.executeQuery()) {
//            while (resultSet.next()) {
//                User user = new User();
//                user.setId(resultSet.getInt("id"));
//                user.setUsername(resultSet.getString("username"));
//                user.setPassword(resultSet.getString("password"));
//                user.setEmail(resultSet.getString("email"));
//                users.add(user);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return users;
//    }

    @Override
    public boolean insertUser(User user) {
        String query = "INSERT INTO Users (UserName, FirstName, LastName, Email, Password, UserType, ImageName, Image) VALUES (?, ?, ?, ?, ?, ?, ?,?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getFirstname());
            stmt.setString(3, user.getLastname());
            stmt.setString(4, user.getEmail());
            stmt.setString(5, user.getPassword());
            stmt.setString(6, user.getUsertype());
            stmt.setString(7, user.getImageName());
            stmt.setString(8, user.getImageName());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


//    @Override
//    public void updateUser(User user) {
//        String query = "UPDATE users SET username = ?, password = ?, email = ? WHERE id = ?";
//        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
//             PreparedStatement statement = connection.prepareStatement(query)) {
//            statement.setString(1, user.getUsername());
//            statement.setString(2, user.getPassword());
//            statement.setString(3, user.getEmail());
//            statement.setInt(4, user.getId());
//            statement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

//    @Override
//    public void deleteUser(int id) {
//        String query = "DELETE FROM users WHERE id = ?";
//        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
//             PreparedStatement statement = connection.prepareStatement(query)) {
//            statement.setInt(1, id);
//            statement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

}
