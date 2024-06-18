package com.cookiecoders.gamearcade;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Logger logger = Logger.getInstance();
        logger.log(Logger.LogLevel.INFO, "Starting Cookie Arcade");
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("loginView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        scene.getStylesheets().add(getClass().getResource("loginView.css").toExternalForm());
        stage.setTitle("Cookie Arcade");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
