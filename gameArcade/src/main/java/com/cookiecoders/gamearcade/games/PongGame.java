package com.cookiecoders.gamearcade.games;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class PongGame implements Game {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int PLAYER_HEIGHT = 100;
    private static final int PLAYER_WIDTH = 15;
    private static final int BALL_RADIUS = 15;
    private static final int PLAYER_SPEED = 5;
    private static final double BALL_INITIAL_SPEED = 3.0; // Control initial ball speed here

    private Canvas canvas;
    private GraphicsContext gc;
    private double player1YPos;
    private double player2YPos;
    private double ballXPos;
    private double ballYPos;
    private double ballXSpeed;
    private double ballYSpeed;
    private int player1Score;
    private int player2Score;
    private boolean upPressed, downPressed, wPressed, sPressed;
    private boolean gamePaused; // Control game start and pause state
    private Timeline timeline;

    @Override
    public void initialize() {
        canvas = new Canvas(WIDTH, HEIGHT);
        gc = canvas.getGraphicsContext2D();
        player1YPos = HEIGHT / 2 - PLAYER_HEIGHT / 2;
        player2YPos = HEIGHT / 2 - PLAYER_HEIGHT / 2;
        resetBall();

        player1Score = 0;
        player2Score = 0;
        gamePaused = true; // Start with the game paused

        // Add key event handlers
        canvas.setFocusTraversable(true);
        canvas.setOnKeyPressed(this::handleKeyPressed);
        canvas.setOnKeyReleased(this::handleKeyReleased);
    }

    @Override
    public void start() {
        timeline = new Timeline(new KeyFrame(Duration.millis(16), e -> {
            update();
            render();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    @Override
    public void update() {
        if (gamePaused) {
            return; // Do not update game state if the game is paused
        }

        // Move ball
        ballXPos += ballXSpeed;
        ballYPos += ballYSpeed;

        // Move player 1 paddle
        if (wPressed && player1YPos > 0) {
            player1YPos -= PLAYER_SPEED;
        }
        if (sPressed && player1YPos < HEIGHT - PLAYER_HEIGHT) {
            player1YPos += PLAYER_SPEED;
        }

        // Move player 2 paddle
        if (upPressed && player2YPos > 0) {
            player2YPos -= PLAYER_SPEED;
        }
        if (downPressed && player2YPos < HEIGHT - PLAYER_HEIGHT) {
            player2YPos += PLAYER_SPEED;
        }

        // Check collisions with top and bottom walls
        if (ballYPos <= 0 || ballYPos >= HEIGHT - BALL_RADIUS) {
            ballYSpeed *= -1;
        }

        // Check collisions with paddles
        if (ballXPos <= PLAYER_WIDTH && ballYPos > player1YPos && ballYPos < player1YPos + PLAYER_HEIGHT) {
            ballXSpeed *= -1;
        }
        if (ballXPos >= WIDTH - PLAYER_WIDTH - BALL_RADIUS && ballYPos > player2YPos && ballYPos < player2YPos + PLAYER_HEIGHT) {
            ballXSpeed *= -1;
        }

        // Check if ball is out of bounds and update score
        if (ballXPos <= 0) {
            player2Score++;
            resetBall();
            gamePaused = true; // Pause after a point is scored
        }
        if (ballXPos >= WIDTH) {
            player1Score++;
            resetBall();
            gamePaused = true; // Pause after a point is scored
        }
    }

    private void resetBall() {
        ballXPos = WIDTH / 2;
        ballYPos = HEIGHT / 2;
        ballXSpeed = (Math.random() > 0.5 ? 1 : -1) * BALL_INITIAL_SPEED;
        ballYSpeed = (Math.random() > 0.5 ? 1 : -1) * BALL_INITIAL_SPEED;
    }

    @Override
    public void render() {
        gc.clearRect(0, 0, WIDTH, HEIGHT);

        // Draw background
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, WIDTH, HEIGHT);

        // Draw paddles
        gc.setFill(Color.WHITE);
        gc.fillRect(0, player1YPos, PLAYER_WIDTH, PLAYER_HEIGHT);
        gc.fillRect(WIDTH - PLAYER_WIDTH, player2YPos, PLAYER_WIDTH, PLAYER_HEIGHT);

        // Draw ball
        gc.fillOval(ballXPos, ballYPos, BALL_RADIUS, BALL_RADIUS);

        // Draw net
        gc.setStroke(Color.WHITE);
        gc.setLineDashes(10);
        gc.strokeLine(WIDTH / 2, 0, WIDTH / 2, HEIGHT);

        // Draw scores
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font(30));
        gc.fillText("Player 1: " + player1Score, WIDTH * 0.25, 50);
        gc.fillText("Player 2: " + player2Score, WIDTH * 0.75 - 100, 50);

        // Draw message when paused
        if (gamePaused) {
            gc.setFont(Font.font(20));
            gc.fillText("Press Spacebar to Start", WIDTH / 2 - 100, HEIGHT / 2);
        }
    }

    @Override
    public void stop() {
        if (timeline != null) {
            timeline.stop();
        }
    }

    public Canvas getCanvas() {
        return canvas;
    }

    private void handleKeyPressed(KeyEvent event) {
        KeyCode code = event.getCode();
        if (code == KeyCode.W) {
            wPressed = true;
        }
        if (code == KeyCode.S) {
            sPressed = true;
        }
        if (code == KeyCode.UP) {
            upPressed = true;
        }
        if (code == KeyCode.DOWN) {
            downPressed = true;
        }
        if (code == KeyCode.SPACE) {
            if (gamePaused) {
                gamePaused = false; // Start the game or resume when space is pressed
            }
        }
    }

    private void handleKeyReleased(KeyEvent event) {
        KeyCode code = event.getCode();
        if (code == KeyCode.W) {
            wPressed = false;
        }
        if (code == KeyCode.S) {
            sPressed = false;
        }
        if (code == KeyCode.UP) {
            upPressed = false;
        }
        if (code == KeyCode.DOWN) {
            downPressed = false;
        }
    }
}
