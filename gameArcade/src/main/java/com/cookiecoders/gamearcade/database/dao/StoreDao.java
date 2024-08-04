package com.cookiecoders.gamearcade.database.dao;

import com.cookiecoders.gamearcade.database.models.Game;

public interface StoreDao {
    boolean addToOwnedGames(Integer UserId, Integer GameId);
}
