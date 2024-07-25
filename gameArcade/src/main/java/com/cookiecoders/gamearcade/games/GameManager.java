package com.cookiecoders.gamearcade.games;

public class GameManager {
    private Game currentGame;

    public void loadGame(Game game) {
        if (currentGame != null) {
            currentGame.stop();
        }
        currentGame = game;
        currentGame.initialize();
    }

    public void startGame() {
        if (currentGame != null) {
            currentGame.start();
        }
    }

    public void updateGame() {
        if (currentGame != null) {
            currentGame.update();
        }
    }

    public void renderGame() {
        if (currentGame != null) {
            currentGame.render();
        }
    }

    public void stopGame() {
        if (currentGame != null) {
            currentGame.stop();
        }
    }
}
