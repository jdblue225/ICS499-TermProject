package com.cookiecoders.gamearcade.games;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class PongGame implements Game {
    private static final int             WIDTH         = 800;
    private static final int             HEIGHT        = 600;
    private static final int             PLAYER_HEIGHT = 100;
    private static final int             PLAYER_WIDTH  = 15;
    private static final int             BALL_RADIUS   = 15;
    private              Canvas          canvas;
    private              GraphicsContext gc;
    private              double          player1YPos;
    private              double          player2YPos;
    private              double          ballXPos;
    private              double          ballYPos;
    private              double          ballXSpeed;
    private              double          ballYSpeed;
    private              Timeline        timeline;
    private              long            startTime;
    private              long            endTime;


    @Override
    public void initialize() {
        canvas = new Canvas(WIDTH, HEIGHT);
        gc = canvas.getGraphicsContext2D();
        player1YPos = HEIGHT / 2 - PLAYER_HEIGHT / 2;
        player2YPos = HEIGHT / 2 - PLAYER_HEIGHT / 2;
        ballXPos = WIDTH / 2;
        ballYPos = HEIGHT / 2;
        ballXSpeed = 5;
        ballYSpeed = 5;
    }

    @Override
    public void start() {
        timeline = new Timeline(new KeyFrame(Duration.millis(16), e -> {
            update();
            render();
            startTime = System.currentTimeMillis();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    @Override
    public void update() {
        // Move ball
        ballXPos += ballXSpeed;
        ballYPos += ballYSpeed;

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

        // Check if ball is out of bounds
        if (ballXPos <= 0 || ballXPos >= WIDTH) {
            ballXPos = WIDTH / 2;
            ballYPos = HEIGHT / 2;
        }
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
    }

    public Canvas getCanvas() {
        return canvas;
    }


    @Override
    public void stop() {
        if (timeline != null) {

        }
    }

    @Override
    public String getName() {
        return "1";
    }
}
