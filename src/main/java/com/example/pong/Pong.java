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
        stage.setTitle("Welcome to Pong!");
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
            gc.strokeText("on Click", WINDOW_WIDTH / 2, WINDOW_HEIGHT / 2);
        }
    }
}
