package com.cookiecoders.gamearcade.database.dao;

import java.util.List;
import java.util.Map;

public interface OwnedGamesDao {
    List<Map<String, Object>> getUnownedGames(Integer userID);
    List<Map<String, Object>> getOwnedGamesSummary(Integer userID);
    Map<Double, String> getOwnedGameReview(Integer UserId, Integer GameId);
    boolean updateGameReview(Integer userId, Integer gameId, Double rating, String review);
    boolean recordGameDuration(int userId, String name, long time);
    Integer getUserPlaytime(Integer userId);
}
