/**
 * Pong.java
 * Created on 01/06/2023
 * Last modified on 01/06/2023
 * No copyright
 * This class represents the game of pong.
 * Version History: 1.0 - only pure code; 2.0 - comments added.
 *
 * @author Mathew Philip Peedikayil
 * @version 2.0
 */

package com.example.pong;

import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Pong extends Application {

    private static final String WELCOME_MSG = "Welcome to Pong!";
    private static final String PLAY_GAME_TXT = "Play Game";

    private boolean startedGame;
    private static final int WINDOW_HEIGHT = 600;
    private static final int WINDOW_WIDTH = 800;
    private static final int PLAYER_HEIGHT = 100;
    private static final int PLAYER_WIDTH = 15;
    private static final double BALL_RADIUS = 15;
    private int yBallSpeed = 1;
    private int xBallSpeed = 1;
    private double ballXPosition = WINDOW_WIDTH / 2;
    private double ballYPosition = WINDOW_WIDTH / 2;
    private int playerOneScore = 0;
    private int playerTwoScore = 0;
    private int playerOneXPosition = 0;
    private int playerTwoXPosition = WINDOW_WIDTH - PLAYER_WIDTH;
    private double playerOneYPosition = WINDOW_HEIGHT / 2;
    private double playerTwoYPosition = WINDOW_HEIGHT / 2;

    public void start(Stage stage) throws Exception {
        stage.setTitle(WELCOME_MSG);
        Canvas canvas = new Canvas(WINDOW_WIDTH, WINDOW_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Timeline tl = new Timeline(new KeyFrame(Duration.millis(10), e -> run(gc)));
        tl.setCycleCount(Timeline.INDEFINITE);

        //  game controls
        canvas.setOnMouseMoved(e -> playerOneYPosition = e.getY()); // stick movement
        canvas.setOnMouseClicked(e -> startedGame = true);
        stage.setScene(new Scene(new StackPane(canvas)));
        stage.show();
        tl.play();
    }

    private void run(GraphicsContext gc) {
        gc.setFill(Color.BLACK); // background colour
        gc.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

        gc.setFill(Color.WHITE);
        gc.setFont(Font.font(21));

        // game logic
        if(startedGame) {
            // ball movement on X and Y axis
            ballXPosition += xBallSpeed;
            ballYPosition += yBallSpeed;

            // player two (computer) who follows the ball
            if(ballXPosition < WINDOW_WIDTH - WINDOW_WIDTH / 4) {
                playerTwoYPosition = ballYPosition - PLAYER_HEIGHT / 2;
            } else {
                playerTwoYPosition = ballYPosition > playerTwoYPosition + PLAYER_HEIGHT / 2 ? playerTwoYPosition + 1 : playerTwoYPosition - 1;
            }

            // draw the ball
            gc.fillOval(ballXPosition, ballYPosition, BALL_RADIUS, BALL_RADIUS);
        } else {
            // start text
            gc.setStroke(Color.WHITE);
            gc.setTextAlign(TextAlignment.CENTER);
            gc.strokeText(PLAY_GAME_TXT, WINDOW_WIDTH / 2, WINDOW_HEIGHT / 2);

            // ball start position reset to middle when ball goes out of window
            ballXPosition = WINDOW_WIDTH / 2;
            ballYPosition = WINDOW_HEIGHT / 2;

            // ball speed and direction reset
            // the speed of the ball increases during the game
            // the speed of the ball is slow at the beginning
            xBallSpeed = new Random().nextInt(2) == 0 ? 1 : -1;
            yBallSpeed = new Random().nextInt(2) == 0 ? 1 : -1;
        }

        // make sure the ball stays in the canvas
        if(ballYPosition > WINDOW_HEIGHT || ballYPosition < 0) {
            yBallSpeed *= -1;
        }

        // points system
        // player two (computer) scores a point
        // if player one (user) misses the ball
        if(ballXPosition < playerOneXPosition - PLAYER_WIDTH) {
            playerTwoScore++;
            startedGame = false;
        }

        // player one (user) scores a point
        // if player two (computer) misses the ball
        if(ballXPosition > playerTwoXPosition + PLAYER_WIDTH) {
            playerOneScore++;
            startedGame = false;
        }

        // ball speed increase
        if( ((ballXPosition + BALL_RADIUS > playerTwoXPosition) && ballYPosition >= playerTwoYPosition && ballYPosition <= playerTwoYPosition + PLAYER_HEIGHT) ||
                ((ballXPosition < playerOneXPosition + PLAYER_WIDTH) && ballYPosition >= playerOneYPosition && ballYPosition <= playerOneYPosition + PLAYER_HEIGHT)) {
            yBallSpeed += 1 *  Math.signum(yBallSpeed);
            xBallSpeed += 1 * Math.signum(xBallSpeed);
            xBallSpeed *= -1;
            yBallSpeed *= -1;
        }

        // draw the score
        gc.fillText(playerOneScore + "\t\t\t\t\t\t\t\t" + playerTwoScore, WINDOW_WIDTH / 2, 100);

        // draw the players
        gc.fillRect(playerOneXPosition, playerOneYPosition, PLAYER_WIDTH, PLAYER_HEIGHT);
        gc.fillRect(playerTwoXPosition, playerTwoYPosition, PLAYER_WIDTH, PLAYER_HEIGHT);
    }
}
