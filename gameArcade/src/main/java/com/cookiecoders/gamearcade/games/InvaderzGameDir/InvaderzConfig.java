package com.cookiecoders.gamearcade.games.InvaderzGameDir;

public class InvaderzConfig {
    private static InvaderzConfig instance;
    private final String gameName = "InvaderzGame";
    private String gameResourcePath;

    private InvaderzConfig() {
        this.gameResourcePath = "/com/cookiecoders/gamearcade/games/InvaderzGame/";
    }

    public static InvaderzConfig getInstance() {
        if (instance == null) {
            synchronized (InvaderzConfig.class) {
                if (instance == null) {  // Double-checked locking.
                    instance = new InvaderzConfig();
                }
            }
        }
        return instance;
    }

    public String getGameResourcePath() {
        return this.gameResourcePath;
    }
    public String getGameName(){
        return this.gameName;
    }
}
