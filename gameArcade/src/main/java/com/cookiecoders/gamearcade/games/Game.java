package com.cookiecoders.gamearcade.games;

public interface Game {
    void initialize();
    void start();
    void update();
    void render();
    void stop();
}
