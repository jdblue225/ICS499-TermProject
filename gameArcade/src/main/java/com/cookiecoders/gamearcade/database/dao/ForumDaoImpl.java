package com.cookiecoders.gamearcade.database.dao;

import com.cookiecoders.gamearcade.database.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ForumDaoImpl implements ForumDao {

    private static DatabaseManager dbm = DatabaseManager.getInstance();
    private static Connection connection = dbm.getConnection();

    public ForumDaoImpl() {}

    // Constructor for injecting connection (used in tests)
    public ForumDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Map<String, Object>> getAllPosts() {
        List<Map<String, Object>> posts = new ArrayList<>();
        String sql = "SELECT fp.PostID, fp.Title, fp.Content, u.username AS Author, fc.CategoryName " +
                "FROM ForumPosts fp " +
                "JOIN Users u ON fp.UserID = u.UserID " +
                "JOIN ForumCategories fc ON fp.CategoryID = fc.CategoryID " +
                "ORDER BY fp.CreatedAt DESC";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Map<String, Object> post = new HashMap<>();
                post.put("PostID", rs.getInt("PostID"));
                post.put("Title", rs.getString("Title"));
                post.put("Content", rs.getString("Content"));
                post.put("Author", rs.getString("Author"));
                post.put("CategoryName", rs.getString("CategoryName"));
                posts.add(post);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }

    @Override
    public void createPost(int userId, int categoryId, String title, String content) {
        String sql = "INSERT INTO ForumPosts(UserID, CategoryID, Title, Content) VALUES(?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, categoryId);
            pstmt.setString(3, title);
            pstmt.setString(4, content);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Map<String, Object>> getAllCategories() {
        List<Map<String, Object>> categories = new ArrayList<>();
        String sql = "SELECT CategoryID, CategoryName, Description FROM ForumCategories";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Map<String, Object> category = new HashMap<>();
                category.put("CategoryID", rs.getInt("CategoryID"));
                category.put("CategoryName", rs.getString("CategoryName"));
                category.put("Description", rs.getString("Description"));
                categories.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }
}
