package TowerDefense;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;


import static TowerDefense.SelectRoad.selectScreen;
import static TowerDefense.Sound.*;

public class GameStage extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        welcomeScreen(primaryStage);
//         gameScreen(primaryStage);
    }

    public static void welcomeScreen(Stage stage) {
        Pane pane = new Pane();

        Scene scene = new Scene(pane, 960, 540);

        imageObject welcomScr = new imageObject("file:images/welcome1.png");
        welcomScr.scaleTo(960, 540);

        Timeline timeline = new Timeline(
            new KeyFrame(Duration.millis(0), event -> {
                imageObject blackScr = new imageObject("file:images/black.png");
                blackScr.scaleTo(960, 540);
                pane.getChildren().add(blackScr);
            }),
            new KeyFrame(Duration.millis(850), event -> {
                imageObject logoScr = new imageObject("file:images/logo.png");
                logoScr.scaleTo(960, 540);
                pane.getChildren().add(logoScr);
                //showMuteBtn(pane);
            }),
            new KeyFrame(Duration.seconds(2), event -> {
                welcomScr.setOpacity(0);
                muteBtn.setOpacity(0);
                speakerBtn.setOpacity(0);
                pane.getChildren().add(welcomScr);

                FadeTransition ft = new FadeTransition(Duration.millis(2000), welcomScr);
                FadeTransition ft2 = new FadeTransition(Duration.millis(2000), muteBtn);
                FadeTransition ft3 = new FadeTransition(Duration.millis(2000), speakerBtn);

                ft.setFromValue(0);
                ft.setToValue(1);
                ft.play();
                ft2.setFromValue(0);
                ft2.setToValue(1);
                ft2.play();
                ft3.setFromValue(0);
                ft3.setToValue(1);
                ft3.play();
                showMuteBtn(pane);
            }),
            new KeyFrame(Duration.seconds(3), event -> {
                imageObject startBtn = new imageObject("file:images/startBtn.png");
                startBtn.setLocation(73, 437);
                startBtn.scaleTo(184, 56);
                startBtn.setOpacity(0);
                pane.getChildren().add(startBtn);
                showMuteBtn(pane);

                startBtn.setOnMouseEntered(event1 -> {
                    startBtn.setOpacity(1);
                    scene.setCursor(Cursor.HAND);
                });

                startBtn.setOnMouseExited(event1 -> {
                    startBtn.setOpacity(0);
                    scene.setCursor(Cursor.DEFAULT);
                });

                startBtn.setOnMouseClicked(event1 -> {
                    clickSound();
//                    gameScreen(stage);
                    selectScreen(stage);
                });
            })
        );

        stage.setTitle("Tower Defense 2.0");

        stage.setScene(scene);
        stage.getIcons().add(new Image("file:images/love.jpg"));
        //stage.setResizable(true);

        stage.show();

        timeline.play();
        playWelcomeMusic();
    }
}
