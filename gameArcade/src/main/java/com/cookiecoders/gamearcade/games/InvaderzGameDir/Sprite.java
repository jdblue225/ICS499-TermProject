package com.cookiecoders.gamearcade.games.InvaderzGameDir;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;

public class Sprite {
    private String gameResourcePath = "/com/cookiecoders/gamearcade/games/InvaderzGame/";
    private Image image;
    private double positionX;
    private double positionY;
    private double velocityX;
    private double velocityY;
    private double width;
    private double height;

    public Sprite() {
        this.positionX = 0;
        this.positionY = 0;
        this.velocityX = 0;
        this.velocityY = 0;
    }

    public void setImage(Image image) {
        this.image = image;
        this.width = image.getWidth();
        this.height = image.getHeight();
    }

    public void setImage(String filename) {
        String fullPath = this.gameResourcePath + filename;
        URL resourceUrl = getClass().getResource(fullPath);

        if (resourceUrl == null) {
            throw new IllegalArgumentException("Resource not found: " + fullPath);
        }
        Image i = new Image(resourceUrl.toExternalForm());
        Image resizedImage = new Image(resourceUrl.toExternalForm(), i.getWidth() / 3, i.getHeight() / 3, true, false);
        setImage(resizedImage);
    }


public void setImage(boolean gif, String filename) {
    String fullPath = this.gameResourcePath + filename;
    URL resourceUrl = getClass().getResource(fullPath);
    if (resourceUrl == null) {
        throw new IllegalArgumentException("Resource not found: " + fullPath);
    }
    Image image;
    if (gif) {
        // Handle the gif image loading
        image = new Image(resourceUrl.toExternalForm());
    } else {
        // Handle other image formats
        image = new Image(resourceUrl.toExternalForm(), true);
    }
    ImageView imageView = new ImageView(image);
    imageView.setPreserveRatio(true);
    setImage(image);
}

    public Image getImage() {
        return image;
    }

    public double getPositionX() {
        return positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public void setPosition(double x, double y) {
        this.positionX = x;
        this.positionY = y;
    }

    public void setVelocity(double x, double y) {
        this.velocityX = x;
        this.velocityY = y;
    }

    public void addVelocity(double x, double y) {
        velocityX += x;
    }

    public void update(double time) {
        positionX += velocityX * time;
        positionY += velocityY * time;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(image, positionX, positionY);
    }

    public Rectangle2D getBoundary() {
        return new Rectangle2D(positionX, positionY, width, height);
    }

    public boolean intersects(Sprite s) {
        return s.getBoundary().intersects(this.getBoundary());
    }
}
