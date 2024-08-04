package com.cookiecoders.gamearcade.database.dao;

import com.cookiecoders.gamearcade.database.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OwnedGamesDaoImpl implements OwnedGamesDao {

    private static DatabaseManager dbm = DatabaseManager.getInstance();
    private static Connection connection = dbm.getConnection();





    @Override
    public List<Map<String, Object>> getUnownedGames(Integer userID) {
        String query = """
                    SELECT g.GameID, g.Title, g.ImageName, g.AverageRating\s
                    FROM Games g
                    LEFT JOIN OwnedGames og ON g.GameID = og.GameID AND og.UserId = ?
                    WHERE og.GameID IS NULL;
                """;
        List<Map<String, Object>> gamesSummary = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userID);
            try (ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    Map<String, Object> gameSummary = new HashMap<>();
                    gameSummary.put("GameID", resultSet.getInt("GameID"));
                    gameSummary.put("Title", resultSet.getString("Title"));
                    gameSummary.put("ImageName", resultSet.getString("ImageName"));
                    gameSummary.put("AverageRating", resultSet.getDouble("AverageRating"));
                    gamesSummary.add(gameSummary);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return gamesSummary;
    }

    @Override
    public List<Map<String, Object>> getOwnedGamesSummary(Integer userID) {
        String query = """
                    SELECT g.GameID, g.Title, g.ImageName FROM Games g\s
                    JOIN OwnedGames og ON g.GameID = og.GameID
                    WHERE og.UserId = ?;
                """;
        List<Map<String, Object>> gamesSummary = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userID);
            try (ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    Map<String, Object> gameSummary = new HashMap<>();
                    gameSummary.put("GameID", resultSet.getInt("GameID"));
                    gameSummary.put("Title", resultSet.getString("Title"));
                    gameSummary.put("ImageName", resultSet.getString("ImageName"));
                    gamesSummary.add(gameSummary);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return gamesSummary;
    }

    @Override
    public boolean updateGameReview(Integer userId, Integer gameId, Double rating, String review){
        String sql = "UPDATE OwnedGames SET Rating = ?, Review = ? WHERE UserID = ? AND GameID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setDouble(1, rating);
            pstmt.setString(2, review);
            pstmt.setInt(3, userId);
            pstmt.setInt(4, gameId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean recordGameDuration(int userId, String name, long time) {
        String sql = "UPDATE OwnedGames SET PlayTime = PlayTime + ? WHERE UserID = ? AND GameID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(2, userId);
            pstmt.setString(3, name);
            pstmt.setLong(1, time);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Map<Double, String> getOwnedGameReview(Integer userId, Integer gameId) {
        Map<Double, String> gameReview = null;
        String sql = "SELECT Rating, Review FROM OwnedGames WHERE UserID = ? AND GameID = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)){
                pstmt.setInt(1, userId);
                pstmt.setInt(2, gameId);

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        gameReview = new HashMap<>();
                        gameReview.put(rs.getDouble("Rating"), rs.getString("Review"));
                    }
                }
            }catch (SQLException e) {
                e.printStackTrace();

            }

        return gameReview;
    }
}
