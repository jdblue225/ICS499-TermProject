package com.cookiecoders.gamearcade.database.dao;

import com.cookiecoders.gamearcade.database.DatabaseManager;


import java.sql.*;

public class StoreDaoImpl implements StoreDao{
    private static DatabaseManager dbm = DatabaseManager.getInstance();
    private static Connection connection = dbm.getConnection();

    public boolean addToOwnedGames(Integer userId, Integer gameId) {

        String query = "INSERT INTO OwnedGames (UserID, GameID, PurchaseDate) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, gameId);
            stmt.setDate(3, new java.sql.Date(System.currentTimeMillis()));

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
