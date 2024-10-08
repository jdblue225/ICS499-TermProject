package com.cookiecoders.gamearcade.database.dao;

import com.cookiecoders.gamearcade.database.models.Game;
import java.util.List;
import java.util.Map;

public interface GameDao {
    Game getGameById(int id);
    boolean insertGame(Game game);
    List<Game> getAllGames();
    List<Map<String, Object>> getAllGamesSummary();

    List<Map<String, Object>> getLeaderboardData();
    boolean updateGame(Game game);
    boolean deleteGame(int id);
    void recordGameDuration(int userId, String name, long time);

}