package com.cookiecoders.gamearcade.games;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Random;

public class DiceGame extends Application {
    private Integer score;
    private Label playerRollLabel;
    private Label computerRollLabel;
    private Label resultLabel;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.score = 0;
        primaryStage.setTitle("Dice Game");

        playerRollLabel = new Label("Your Roll: ");
        computerRollLabel = new Label("Computer Roll: ");
        resultLabel = new Label();

        Button rollButton = new Button("Roll Dice");
        rollButton.setOnAction(e -> rollDice());

        VBox vbox = new VBox(10, playerRollLabel, computerRollLabel, resultLabel, rollButton);
        vbox.setPadding(new Insets(20));
//        vbox.setStyle("-fx-font-size: 16px;");
        vbox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vbox, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void rollDice() {
        Random random = new Random();

        int playerDice1 = random.nextInt(6) + 1;
        int playerDice2 = random.nextInt(6) + 1;
        int playerTotal = playerDice1 + playerDice2;

        int computerDice1 = random.nextInt(6) + 1;
        int computerDice2 = random.nextInt(6) + 1;
        int computerTotal = computerDice1 + computerDice2;

        playerRollLabel.setText("Your Roll: " + playerDice1 + " and " + playerDice2 + " (Total: " + playerTotal + ")");
        computerRollLabel.setText("Computer Roll: " + computerDice1 + " and " + computerDice2 + " (Total: " + computerTotal + ")");

        if (playerTotal > computerTotal) {
            resultLabel.setText("You win!");
            this.score += 1;
        } else if (playerTotal < computerTotal) {
            resultLabel.setText("Computer wins!");
        } else {
            resultLabel.setText("It's a tie!");
        }
    }
}
