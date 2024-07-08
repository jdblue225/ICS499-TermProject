package com.cookiecoders.gamearcade.database.dao;



import com.cookiecoders.gamearcade.database.DatabaseManager;
import com.cookiecoders.gamearcade.database.models.Game;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class GameDaoImpl implements GameDao{

    private static DatabaseManager dbm = DatabaseManager.getInstance();
    private static Connection connection = dbm.getConnection();

    public Game getGameById(int id) {
        String query = "SELECT * FROM Games WHERE GameID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    Game game = new Game(
                            resultSet.getInt("GameID"),
                            resultSet.getString("Title"),
                            resultSet.getString("Description"),
                            resultSet.getString("Developer"),
                            resultSet.getDate("ReleaseDate"),
                            resultSet.getDouble("Price"),
                            resultSet.getString("ImagePath"),
                            resultSet.getTimestamp("CreatedAt")
                    );
                    return game;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Map<String, Object>> getAllGamesSummary() {
        String query = "SELECT GameID, Title, ImagePath FROM Games";
        List<Map<String, Object>> gamesSummary = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet resultSet = stmt.executeQuery()) {
            while (resultSet.next()) {
                Map<String, Object> gameSummary = new HashMap<>();
                gameSummary.put("GameID", resultSet.getInt("GameID"));
                gameSummary.put("Title", resultSet.getString("Title"));
                gameSummary.put("ImagePath", resultSet.getString("ImagePath"));
                gamesSummary.add(gameSummary);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gamesSummary;
    }

    @Override
    public List<Map<String, Object>> getOwnedGamesSummary(Integer userID) {
        String query = """
                SELECT g.GameID, g.Title, g.ImagePath FROM Games g\s
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
                    gameSummary.put("ImagePath", resultSet.getString("ImagePath"));
                    gamesSummary.add(gameSummary);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return gamesSummary;
    }

    public boolean insertGame(Game game) {
        String query = "INSERT INTO Games (Title, Description, Developer, ReleaseDate, Price, ImagePath, CreatedAt) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, game.getTitle());
            stmt.setString(2, game.getDescription());
            stmt.setString(3, game.getDeveloper());
            stmt.setDate(4, new Date(game.getReleaseDate().getTime()));
            stmt.setDouble(5, game.getPrice());
            stmt.setString(6, game.getImagePath());

            // If createdAt is not set, use the current timestamp
            if (game.getCreatedAt() != null) {
                stmt.setTimestamp(7, new Timestamp(game.getCreatedAt().getTime()));
            } else {
                stmt.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
            }

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Game> getAllGames() {
        String query = "SELECT * FROM Games";
        List<Game> games = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet resultSet = stmt.executeQuery()) {
            while (resultSet.next()) {
                Game game = new Game(
                        resultSet.getInt("GameID"),
                        resultSet.getString("Title"),
                        resultSet.getString("Description"),
                        resultSet.getString("Developer"),
                        resultSet.getDate("ReleaseDate"),
                        resultSet.getDouble("Price"),
                        resultSet.getString("ImagePath"),
                        resultSet.getTimestamp("CreatedAt")
                );
                games.add(game);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return games;
    }

    public boolean updateGame(Game game) {
        String query = "UPDATE Games SET Title = ?, Description = ?, Developer = ?, ReleaseDate = ?, Price = ?, ImagePath = ? WHERE GameID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, game.getTitle());
            stmt.setString(2, game.getDescription());
            stmt.setString(3, game.getDeveloper());
            stmt.setDate(4, new Date(game.getReleaseDate().getTime()));
            stmt.setDouble(5, game.getPrice());
            stmt.setString(6, game.getImagePath());
            stmt.setInt(7, game.getGameId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteGame(int id) {
        String query = "DELETE FROM Games WHERE GameID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


}
