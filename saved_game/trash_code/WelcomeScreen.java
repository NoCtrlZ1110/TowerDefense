package TowerDefense;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Cursor;
import javafx.stage.Stage;
import javafx.util.Duration;

import static TowerDefense.Sound.*;

public class WelcomeScreen extends Screen {
    private Timeline timeline;

    public WelcomeScreen(Stage stage) {
        super(stage, 960, 540);

        imageObject welcomScr = new imageObject("file:images/welcome1.png");
        welcomScr.scaleTo(960, 540);

        timeline = new Timeline(
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
                    welcomScr.setCursor(Cursor.HAND);
                });

                startBtn.setOnMouseExited(event1 -> {
                    startBtn.setOpacity(0);
                    welcomScr.setCursor(Cursor.DEFAULT);
                });

                startBtn.setOnMouseClicked(event1 -> {
                    clickSound();
                    // GameStage.closePrimaryStage();
                    GameStage.startGameScreen();
                });
            })
        );
    }

    public void active() {
        super.active();

        timeline.play();
        playWelcomeMusic();
    }
}
