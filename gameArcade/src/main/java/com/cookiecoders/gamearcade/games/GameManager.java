package com.cookiecoders.gamearcade.games;

import com.cookiecoders.gamearcade.database.dao.GameDao;
import com.cookiecoders.gamearcade.database.dao.GameDaoImpl;
import com.cookiecoders.gamearcade.database.dao.OwnedGamesDao;
import com.cookiecoders.gamearcade.database.dao.OwnedGamesDaoImpl;
import com.cookiecoders.gamearcade.users.UserSession;
import javafx.scene.layout.StackPane;


public class GameManager {
    private Game currentGame;
    private StackPane root;
    private OwnedGamesDao ownedGamesDao;

    public GameManager() {
        this.ownedGamesDao = new OwnedGamesDaoImpl();
    }

    /**
     * This method sets the current game in the GameManager in this class
     * and calls the <Game Name>.java initialize() method from the Game.java
     * interface.
     *
     * @param game
     */
    public void loadGame(Game game) {
        if (currentGame != null) {
            currentGame.stop();
        }
        currentGame = game;
        /**
         * This is already called when the class is instantiated,
         * so we are re-initializing the class.... Why?
         * JJD
         */
        currentGame.initialize();
    }

    /**
     * This method accesses the Game interfaces start() method.
     */
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
        } else if (currentGame instanceof SnakeGame) {
            root = new StackPane(((SnakeGame) currentGame).getCanvas());
        }
        return root;
    }

    public void stopGame() {
        if (currentGame != null) {
            currentGame.stop();
        }
    }

    public void recordTime(long time) {
        UserSession userSession = UserSession.getInstance();
        int userId = userSession.getCurrentUser().getId();
        this.ownedGamesDao.recordGameDuration(userId,currentGame.getGameID(),time);
    }

}
