package com.cookiecoders.gamearcade.games;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.input.KeyCode;

public class MinesweeperGame implements Game {
    private static final int TILE_SIZE = 20;
    private static final int WIDTH = 20;
    private static final int HEIGHT = 20;
    private static final int NUM_MINES = 40;

    private Canvas canvas;
    private GraphicsContext gc;
    private boolean[][] mines;
    private boolean[][] revealed;
    private boolean[][] flagged;
    private boolean gameOver;
    private boolean gameWon;
    private boolean gamePaused; // Add a paused state

    @Override
    public void initialize() {
        canvas = new Canvas(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);
        gc = canvas.getGraphicsContext2D();

        mines = new boolean[WIDTH][HEIGHT];
        revealed = new boolean[WIDTH][HEIGHT];
        flagged = new boolean[WIDTH][HEIGHT];

        gameOver = false;
        gameWon = false;
        gamePaused = true; // Start game as paused

        // Add mouse event handler
        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, this::handleMouseClick);

        // Add key event handler for spacebar
        canvas.setFocusTraversable(true);
        canvas.setOnKeyPressed(this::handleKeyPressed);
    }
    @Override
    public String getGameID(){
        return "2";
    }

    @Override
    public void start() {
        render(); // Render initial state
    }

    @Override
    public void update() {
        // Update game logic if needed (not used here)
    }

    @Override
    public void render() {
        gc.clearRect(0, 0, WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);

        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                if (revealed[x][y]) {
                    if (mines[x][y]) {
                        gc.setFill(Color.RED);
                        gc.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                    } else {
                        gc.setFill(Color.LIGHTGRAY);
                        gc.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                        int adjacentMines = countAdjacentMines(x, y);
                        if (adjacentMines > 0) {
                            gc.setFill(Color.BLACK);
                            gc.setFont(new Font(TILE_SIZE / 2));
                            gc.fillText(String.valueOf(adjacentMines), x * TILE_SIZE + TILE_SIZE / 4, y * TILE_SIZE + TILE_SIZE / 1.5);
                        }
                    }
                } else {
                    gc.setFill(Color.DARKGRAY);
                    gc.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);

                    if (flagged[x][y]) {
                        gc.setFill(Color.YELLOW);
                        gc.setFont(new Font(TILE_SIZE / 2));
                        gc.fillText("F", x * TILE_SIZE + TILE_SIZE / 4, y * TILE_SIZE + TILE_SIZE / 1.5);
                    }
                }
                gc.setStroke(Color.BLACK);
                gc.strokeRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }

        // Display a message when paused
        if (gamePaused) {
            gc.setFill(Color.BLACK);
            gc.setFont(new Font(30));
            gc.fillText("Press Spacebar to Start", WIDTH * TILE_SIZE / 4, HEIGHT * TILE_SIZE / 2);
        }
    }

    @Override
    public void stop() {
        // Cleanup resources if needed
    }

    private void handleMouseClick(MouseEvent event) {
        // Ensure the game is not paused and is running
        if (gamePaused || gameOver || gameWon) {
            return; // Ignore clicks when paused or game over
        }

        // Calculate tile position based on mouse click coordinates
        int x = (int) (event.getX() / TILE_SIZE);
        int y = (int) (event.getY() / TILE_SIZE);

        // Check bounds to prevent ArrayIndexOutOfBoundsException
        if (x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT) {
            return;
        }

        // Process left click separately
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            handleLeftClick(x, y);
        }
        // Process right click separately
        else if (event.getButton().equals(MouseButton.SECONDARY)) {
            handleRightClick(x, y);
        }

        render(); // Redraw canvas after any action
    }

    private void handleLeftClick(int x, int y) {
        if (!revealed[x][y] && !flagged[x][y]) {
            revealed[x][y] = true;
            if (mines[x][y]) {
                gameOver = true;
                revealAllMines();
                showAlert("Game Over", "You hit a mine! Game Over.");
            } else if (isGameWon()) {
                gameWon = true;
                showAlert("Congratulations", "You won!");
            }
        }
    }

    private void handleRightClick(int x, int y) {
        if (!revealed[x][y]) {
            flagged[x][y] = !flagged[x][y];
        }
    }

    private void handleKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.SPACE) {
            if (gamePaused || gameOver || gameWon) {
                resetGame();
                gamePaused = false; // Start the game
                render();
            }
        }
    }

    private void resetGame() {
        // Reset all game variables
        mines = new boolean[WIDTH][HEIGHT];
        revealed = new boolean[WIDTH][HEIGHT];
        flagged = new boolean[WIDTH][HEIGHT];
        gameOver = false;
        gameWon = false;
        gamePaused = true; // Reset to paused state

        // Place mines again
        for (int i = 0; i < NUM_MINES; i++) {
            int x, y;
            do {
                x = (int) (Math.random() * WIDTH);
                y = (int) (Math.random() * HEIGHT);
            } while (mines[x][y]);
            mines[x][y] = true;
        }
    }

    private void revealAllMines() {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                if (mines[x][y]) {
                    revealed[x][y] = true;
                }
            }
        }
    }

    private boolean isGameWon() {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                if (!mines[x][y] && !revealed[x][y]) {
                    return false;
                }
            }
        }
        return true;
    }

    private int countAdjacentMines(int x, int y) {
        int count = 0;
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                int nx = x + dx;
                int ny = y + dy;
                if (nx >= 0 && nx < WIDTH && ny >= 0 && ny < HEIGHT && mines[nx][ny]) {
                    count++;
                }
            }
        }
        return count;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public Canvas getCanvas() {
        return canvas;
    }
}