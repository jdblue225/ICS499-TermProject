package com.cookiecoders.gamearcade.games;

import javafx.application.Application;
import javafx.stage.Stage;

public class CardzGame extends Application implements Game {
    private String gameId = "7";

    @Override
    public void initialize(){
    }
    @Override
    public void start(){

    }
    @Override
    public void start(Stage primaryStage) throws Exception {
    }
    @Override
    public void update(){}
    @Override
    public void render(){}
    @Override
    public void stop(){}
    @Override
    public String getGameID(){
        return this.gameId;
    }
}
