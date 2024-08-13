package com.cookiecoders.gamearcade.leaderboard;

public class LeaderboardEntry {
    private String username;
    private String gameTitle;
    private Integer playTime;

    public LeaderboardEntry(String username, String gameTitle, Integer playTime) {
        this.username = username;
        this.gameTitle = gameTitle;
        this.playTime = playTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGameTitle() {
        return gameTitle;
    }

    public void setGameTitle(String gameTitle) {
        this.gameTitle = gameTitle;
    }

    public Integer getPlayTime() {
        return playTime;
    }

    public void setPlayTime(Integer playTime) {
        this.playTime = playTime;
    }
}