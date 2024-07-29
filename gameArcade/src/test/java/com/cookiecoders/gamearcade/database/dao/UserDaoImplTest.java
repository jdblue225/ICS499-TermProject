package com.cookiecoders.gamearcade.database.dao;

import com.cookiecoders.gamearcade.database.DatabaseManager;
import com.cookiecoders.gamearcade.database.models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoImplTest {

    private UserDaoImpl userDao;
    private Connection connection;

    @BeforeEach
    void setUp() throws SQLException {
        connection = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "");
        Statement statement = connection.createStatement();
        statement.execute("CREATE TABLE users (userid INT AUTO_INCREMENT PRIMARY KEY, username VARCHAR(50), firstname VARCHAR(50), lastname VARCHAR(50), email VARCHAR(100), password VARCHAR(255), usertype VARCHAR(50), createdat TIMESTAMP)");
        userDao = new UserDaoImpl(connection);

    }

    @AfterEach
    void tearDown() throws SQLException {
        connection.createStatement().execute("DROP TABLE users");
        connection.close();
    }

    @Test
    void testInsertUser() {
        User user = new User("1", "testuser", "Test", "User", "testuser@example.com", "password", "regular", new byte[0]);
        boolean result = userDao.insertUser(user);
        assertTrue(result);

        User retrievedUser = userDao.getUserByUsername("testuser");
        assertNotNull(retrievedUser);
        assertEquals("testuser", retrievedUser.getUsername());
    }

    @Test
    void testGetUserByUsername() {
        User user = new User("1", "testuser", "Test", "User", "testuser@example.com", "password", "regular", new byte[0]);
        userDao.insertUser(user);

        User retrievedUser = userDao.getUserByUsername("testuser");
        assertNotNull(retrievedUser);
        assertEquals("testuser", retrievedUser.getUsername());
        assertEquals("testuser@example.com", retrievedUser.getEmail());
    }
}
