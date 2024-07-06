/**
 * This is the main class to the Cookie Coders application
 * developed for ICS 499 Capstone class summer of 2024.
 *
 * This is the main method and is to be initially called
 * in order to start the application.
 *
 * @author Cookie Coders
 *
 *      Brandon Crass
 *      Jeremy Daly
 *      Maddy Imhoff
 *      Kevin Trinh
 *
 */

package com.cookiecoders.gamearcade;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Logger logger = Logger.getInstance();
        logger.log(Logger.LogLevel.INFO, "Starting Cookie Arcade");
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("loginView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(getClass().getResource("loginView.css").toExternalForm());
        stage.setTitle("Cookie Arcade");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
