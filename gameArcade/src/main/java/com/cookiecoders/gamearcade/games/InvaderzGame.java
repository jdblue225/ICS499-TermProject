/**
 * Space Invaders
 * Created by tonysaavedra on 6/17/16.
 * https://github.com/ajsaavedra/SpaceInvadersFX
 *
 * Modified by CookieCoders for Cooke Arcade under
 * Creative Commons Licensing for Non-commercial use.
 * ICS 499 Software Engineering/Capstone
 * Metropolitan State University
 * Summer 2024
 */
package com.cookiecoders.gamearcade.games;

import com.cookiecoders.gamearcade.games.InvaderzGameDir.*;
import com.cookiecoders.gamearcade.ui.controllers.Navigation;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.net.URL;

public class InvaderzGame extends Application {
    private String gameResourcePath;
    public static boolean GAME_SET;
    private long startTime;
    private Stage stage;

    @Override
    public void stop(){
        GameManager gameManager = new GameManager();
        long endTime = System.currentTimeMillis();
        long duration = (endTime - this.startTime) / 120 ;
        gameManager.recordTime(duration, 9);
    }


    private Parent createContent() {
        Pane root = new Pane();
        root.setStyle("-fx-background-color: black;");

        // Construct the full path to the resource
        String backgroundImagePath = gameResourcePath + "images/background.jpg";
        URL resourceUrl = getClass().getResource(backgroundImagePath);

        if (resourceUrl == null) {
            throw new IllegalArgumentException("Resource not found: " + backgroundImagePath);
        }

        ImageView imageView = new ImageView(new Image(resourceUrl.toExternalForm()));
        imageView.setFitWidth(600);
        imageView.setFitHeight(350);

        Rectangle mask = new Rectangle(600, 500);
        mask.setOpacity(0);
        mask.setMouseTransparent(true);

        MenuBox menuBox = new MenuBox(400, 100);
        menuBox.setTranslateX(100);
        menuBox.setTranslateY(350);
        menuBox.prefWidthProperty().bind(root.widthProperty());
        menuBox.prefHeightProperty().bind(root.heightProperty().multiply(0.3));

        MenuItem startButton = new MenuItem("START", 250);
        startButton.setOnAction(() -> {
            if (!GAME_SET) {
                GAME_SET = true;
                FadeTransition ft = new FadeTransition(Duration.seconds(0.8), mask);
                ft.setToValue(1);

                ft.setOnFinished(e -> {
                    mask.setOpacity(0);
                    SpaceInvadersFX game = new SpaceInvadersFX();
                    Stage st = new Stage();
                    try {
                        game.start(st);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                });
                ft.play();
            }
        });

        MenuItem quitGame = new MenuItem("QUIT", 250);
        quitGame.setOnAction(() -> {
//            System.exit(0); //close out of whole application
            stop();
            this.stage.close();
        });

        menuBox.addItem(startButton);
        menuBox.addItem(quitGame);
        root.getChildren().addAll(imageView, menuBox, mask);
        return root;
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        this.startTime = System.currentTimeMillis();
        this.gameResourcePath = InvaderzConfig.getInstance().getGameResourcePath();
        Scene scene = new Scene(createContent());
        scene.setFill(Color.BLACK);
        primaryStage.setTitle("Space Invaders");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        this.stage = primaryStage;
        this.stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
