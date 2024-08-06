package com.cookiecoders.gamearcade.games;

import javafx.scene.Node;

public interface Game {
    void initialize();
    void start();
    void update();
    void render();
    void stop();
    String getName();
}
