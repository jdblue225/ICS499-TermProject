package com.cookiecoders.gamearcade.games;

import com.cookiecoders.gamearcade.database.dao.GameDao;
import com.cookiecoders.gamearcade.database.dao.GameDaoImpl;
import com.cookiecoders.gamearcade.database.dao.OwnedGamesDao;
import com.cookiecoders.gamearcade.database.dao.OwnedGamesDaoImpl;
import com.cookiecoders.gamearcade.users.UserSession;
import javafx.scene.layout.StackPane;


public class GameManager {
    private Game currentGame;
    private       StackPane root;
    private final GameDao   gameDao;
    private OwnedGamesDao ownedGamesDao;

    public GameManager() {
        this.gameDao = new GameDaoImpl();
        this.ownedGamesDao = new OwnedGamesDaoImpl();
    }

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
        this.ownedGamesDao.recordGameDuration(userId,currentGame.getName(),time);
    }
}
