package com.cookiecoders.gamearcade.games;

import javafx.stage.Stage;

public interface Game {
    void initialize();
    void start();
    void update();
    void render();
    void stop();
    //This should be an integer!!!!!!!!
    String getGameID();
}
