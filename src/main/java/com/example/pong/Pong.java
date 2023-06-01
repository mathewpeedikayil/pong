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

    }
}
