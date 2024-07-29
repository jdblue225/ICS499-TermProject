package com.cookiecoders.gamearcade.games;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class SnakeGame implements Game {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;
    private static final int BLOCK_SIZE = 20;

    private List<Block> snake;
    private Block food;
    private Direction direction;
    private boolean running;
    private GraphicsContext gc;
    private Timeline timeline;
    private Canvas canvas;

    private enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    public SnakeGame() {
        canvas = new Canvas(WIDTH, HEIGHT);
        gc = canvas.getGraphicsContext2D();
    }

    @Override
    public void initialize() {
        snake = new ArrayList<>();
        snake.add(new Block(WIDTH / 2, HEIGHT / 2));
        direction = Direction.RIGHT;
        running = true;
        spawnFood();
    }

    @Override
    public void start() {
        initialize();

        timeline = new Timeline(new KeyFrame(Duration.millis(150), e -> {
            update();
            render();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    @Override
    public void update() {
        if (!running) return;

        Block head = snake.get(0);
        Block newHead = new Block(head.x, head.y);

        switch (direction) {
            case UP -> newHead.y -= BLOCK_SIZE;
            case DOWN -> newHead.y += BLOCK_SIZE;
            case LEFT -> newHead.x -= BLOCK_SIZE;
            case RIGHT -> newHead.x += BLOCK_SIZE;
        }

        if (newHead.x < 0 || newHead.x >= WIDTH || newHead.y < 0 || newHead.y >= HEIGHT || snake.contains(newHead)) {
            running = false;
            stop();
            return;
        }

        snake.add(0, newHead);
        if (newHead.equals(food)) {
            spawnFood();
        } else {
            snake.remove(snake.size() - 1);
        }
    }

    @Override
    public void render() {
        gc.clearRect(0, 0, WIDTH, HEIGHT);

        gc.setFill(Color.GREEN);
        for (Block block : snake) {
            gc.fillRect(block.x, block.y, BLOCK_SIZE, BLOCK_SIZE);
        }

        gc.setFill(Color.RED);
        gc.fillRect(food.x, food.y, BLOCK_SIZE, BLOCK_SIZE);
    }

    @Override
    public void stop() {
        if (timeline != null) {
            timeline.stop();
        }
        gc.setFill(Color.RED);
        gc.fillText("Game Over", WIDTH / 2.0 - 30, HEIGHT / 2.0);
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void setScene(Scene scene) {
        scene.setOnKeyPressed(event -> {
            if (!running) return;
            KeyCode key = event.getCode();
            if (key == KeyCode.UP && direction != Direction.DOWN) {
                direction = Direction.UP;
            } else if (key == KeyCode.DOWN && direction != Direction.UP) {
                direction = Direction.DOWN;
            } else if (key == KeyCode.LEFT && direction != Direction.RIGHT) {
                direction = Direction.LEFT;
            } else if (key == KeyCode.RIGHT && direction != Direction.LEFT) {
                direction = Direction.RIGHT;
            }
        });
    }

    private void spawnFood() {
        int x = (int) (Math.random() * (WIDTH / BLOCK_SIZE)) * BLOCK_SIZE;
        int y = (int) (Math.random() * (HEIGHT / BLOCK_SIZE)) * BLOCK_SIZE;
        food = new Block(x, y);

        while (snake.contains(food)) {
            x = (int) (Math.random() * (WIDTH / BLOCK_SIZE)) * BLOCK_SIZE;
            y = (int) (Math.random() * (HEIGHT / BLOCK_SIZE)) * BLOCK_SIZE;
            food = new Block(x, y);
        }
    }

    private static class Block {
        int x, y;

        Block(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Block block = (Block) obj;
            return x == block.x && y == block.y;
        }

        @Override
        public int hashCode() {
            return 31 * x + y;
        }
    }
}
