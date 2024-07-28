package com.cookiecoders.gamearcade.games;
import javafx.scene.layout.StackPane;

public class GameManager {
    private Game currentGame;
    private StackPane root;

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

    public StackPane getGamePane() {
        if (currentGame instanceof PongGame) {
            root = new StackPane(((PongGame) currentGame).getCanvas());
        } else if (currentGame instanceof MinesweeperGame) {
            root = new StackPane(((MinesweeperGame) currentGame).getCanvas());
        }
        return root;
    }

    public void stopGame() {
        if (currentGame != null) {
            currentGame.stop();
        }
    }
}
