package com.cookiecoders.gamearcade.games;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class MinesweeperGame implements Game {
    private static final int TILE_SIZE = 20;
    private static final int WIDTH     = 20;
    private static final int HEIGHT    = 20;
    private static final int NUM_MINES = 40;

    private Canvas          canvas;
    private GraphicsContext gc;
    private boolean[][]     mines;
    private boolean[][]     revealed;
    private boolean[][]     flagged;
    private boolean         gameOver;
    private boolean         gameWon;

    @Override
    public void initialize() {
        canvas = new Canvas(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);
        gc = canvas.getGraphicsContext2D();

        mines = new boolean[WIDTH][HEIGHT];
        revealed = new boolean[WIDTH][HEIGHT];
        flagged = new boolean[WIDTH][HEIGHT];

        gameOver = false;
        gameWon = false;


        // Place mines
        for (int i = 0; i < NUM_MINES; i++) {
            int x, y;
            do {
                x = (int) (Math.random() * WIDTH);
                y = (int) (Math.random() * HEIGHT);
            } while (mines[x][y]);
            mines[x][y] = true;
        }

        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, this::handleMouseClick);
    }

    @Override
    public void start() {
        render();
    }

    @Override
    public void update() {
        // Update game logic if needed
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
    }

    @Override
    public void stop() {
        // Cleanup resources if needed
    }

    @Override
    public String getName() {
        return "2";
    }

    private void handleMouseClick(MouseEvent event) {
        int x = (int) (event.getX() / TILE_SIZE);
        int y = (int) (event.getY() / TILE_SIZE);

        if (event.isPrimaryButtonDown()) {
            if (!revealed[x][y]) {
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
        } else if (event.isSecondaryButtonDown()) {
            flagged[x][y] = !flagged[x][y];
        }
        render();
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

