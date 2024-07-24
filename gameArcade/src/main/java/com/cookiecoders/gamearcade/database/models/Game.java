package com.cookiecoders.gamearcade.database.models;

import com.cookiecoders.gamearcade.config.ConfigManager;

import java.util.Date;

public class Game {
    private Integer gameId;
    private String title;
    private String description;
    private String developer;
    private Date releaseDate;
    private Double price;
    private String imagePath;
    private Date createdAt;
    private Double averageRating;

    // Default constructor
    public Game() {
    }

    // Constructor without ID and CreatedAt
    public Game(String title, String description, String developer, Date releaseDate, Double price, String imagePath) {
        this.title = title;
        this.description = description;
        this.developer = developer;
        this.releaseDate = releaseDate;
        this.price = price;
        this.imagePath = imagePath;
    }

    // Full constructor
    public Game(Integer gameId, String title, String description, String developer, Date releaseDate, Double price, String imageName, Date createdAt, Double averageRating) {
        this.gameId = gameId;
        this.title = title;
        this.description = description;
        this.developer = developer;
        this.releaseDate = releaseDate;
        this.price = price;
        this.imagePath = ConfigManager.getProperty("root_path") + ConfigManager.getProperty("game_image_path") + imageName;
        this.createdAt = createdAt;
        this.averageRating = averageRating;
    }

    // Getters
    public Integer getGameId() {
        return this.gameId;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public String getDeveloper() {
        return this.developer;
    }

    public Date getReleaseDate() {
        return this.releaseDate;
    }

    public Double getPrice() {
        return this.price;
    }

    public String getImagePath() {
        return this.imagePath;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public Double getAverageRating(){
        return this.averageRating;
    }

    // Setters
    public void setGame(Game game) {
        this.gameId = game.getGameId();
        this.title = game.getTitle();
        this.description = game.getDescription();
        this.developer = game.getDeveloper();
        this.releaseDate = game.getReleaseDate();
        this.price = game.getPrice();
        this.imagePath = game.getImagePath();
        this.createdAt = game.getCreatedAt();
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setAverageRating(Double averageRating){
        this.averageRating = averageRating;
    }
}