package TowerDefense;

import javafx.application.Platform;
import javafx.scene.Cursor;
import javafx.scene.layout.Pane;

import static TowerDefense.CONSTANT.COL_NUM;
import static TowerDefense.CONSTANT.TILE_WIDTH;
import static TowerDefense.GameField.pauseGame;
import static TowerDefense.GameField.*;

public class PauseScreen {
    static imageObject playBtn = new imageObject("file:images/play.png");
    static imageObject pauseBtn = new imageObject("file:images/pause.png");

    public static void showPauseBtn() {
        playBtn.setLocation(TILE_WIDTH * COL_NUM - 70, 80);
        playBtn.scaleTo(40, 40);
        playBtn.setOnMouseEntered(event -> playBtn.setCursor(Cursor.HAND));
        playBtn.setOnMouseExited(event -> playBtn.setCursor(Cursor.DEFAULT));
        if (!layout.getChildren().contains(playBtn))
            layout.getChildren().add(playBtn);
        playBtn.setVisible(false);

        pauseBtn.setLocation(TILE_WIDTH * COL_NUM - 70, 80);
        pauseBtn.scaleTo(40, 40);
        pauseBtn.setOnMouseEntered(event -> pauseBtn.setCursor(Cursor.HAND));
        pauseBtn.setOnMouseExited(event -> pauseBtn.setCursor(Cursor.DEFAULT));
        if (!layout.getChildren().contains(pauseBtn))
            layout.getChildren().add(pauseBtn);
        pauseBtn.setVisible(true);

        pauseBtn.setOnMouseClicked(event -> {
            pauseGame();
            showPauseMenu();
            pauseBtn.setVisible(false);
            //playBtn.setVisible(true);
        });
//        playBtn.setOnMouseClicked(event -> {
//            resumeGame();
//            hidePauseMenu();
//            pauseBtn.setVisible(true);
//            playBtn.setVisible(false);
//        });
    }

    static Pane pausePane = new Pane();
    static imageObject pauseMenu = new imageObject("file:images/PauseMenu/frame.png");
    static imageObject pauseBlack = new imageObject("file:images/PauseMenu/Black.png");
    static imageObject backBtn = new imageObject("file:images/PauseMenu/back.png");
    static imageObject resumeBtn = new imageObject("file:images/PauseMenu/resume.png");
    static imageObject quitBtn = new imageObject("file:images/PauseMenu/quit.png");

    public static void showPauseMenu() {
        pausePane.setLayoutX(180);
        pausePane.setLayoutY(100);
        pauseMenu.setLocation(pausePane.getLayoutX(), pausePane.getLayoutY());
        pauseBlack.setLocation(0, 0);
        pauseBlack.setOpacity(0.5);
        backBtn.setLocation(pausePane.getLayoutX() + 125, pausePane.getLayoutY() + 160);
        resumeBtn.setLocation(pausePane.getLayoutX() + 258, pausePane.getLayoutY() + 145);
        quitBtn.setLocation(pausePane.getLayoutX() + 415, pausePane.getLayoutY() + 160);

        if (!layout.getChildren().contains(pauseBlack)) layout.getChildren().add(pauseBlack);
        if (!pausePane.getChildren().contains(pauseMenu)) pausePane.getChildren().add(pauseMenu);
        if (!pausePane.getChildren().contains(backBtn)) pausePane.getChildren().add(backBtn);
        if (!pausePane.getChildren().contains(resumeBtn)) pausePane.getChildren().add(resumeBtn);
        if (!pausePane.getChildren().contains(quitBtn)) pausePane.getChildren().add(quitBtn);

        if (!layout.getChildren().contains(pausePane)) layout.getChildren().add(pausePane);

        backBtn.setOnMouseEntered(event -> backBtn.setCursor(Cursor.HAND));
        backBtn.setOnMouseExited(event -> backBtn.setCursor(Cursor.DEFAULT));
        resumeBtn.setOnMouseEntered(event -> resumeBtn.setCursor(Cursor.HAND));
        resumeBtn.setOnMouseExited(event -> resumeBtn.setCursor(Cursor.DEFAULT));
        quitBtn.setOnMouseEntered(event -> quitBtn.setCursor(Cursor.HAND));
        quitBtn.setOnMouseExited(event -> quitBtn.setCursor(Cursor.DEFAULT));

        //TODO
        backBtn.setOnMouseClicked(event -> {
            System.out.println("-> Save");
            SaveScreen.showSaveMenu();
            // GameStage.welcomeScreen();
        });
        resumeBtn.setOnMouseClicked(event -> {
            resumeGame();
            hidePauseMenu();
            pauseBtn.setVisible(true);
        });
        quitBtn.setOnMouseClicked(event -> {
            System.out.println("-> Quit");
            // hỏi save trước khi quit
            if (isStarted) {
                SaveScreen.showSaveMenu();
                if (SaveScreen.isCanceled())
                    GameStage.closePrimaryStage();
            }
        });
    }

    public static void refreshPauseMenu() {
        layout.getChildren().remove(pausePane);
        layout.getChildren().remove(pauseBlack);
        layout.getChildren().addAll(pauseBlack, pausePane);
    }

    public static void hidePauseMenu() {
        layout.getChildren().remove(pausePane);
        layout.getChildren().remove(pauseBlack);
    }
    /*
    imageObject background = new imageObject("file:images/black_background.png");
    background.setLocation(0, 0);
    background.scaleTo(TILE_WIDTH * COL_NUM, TILE_WIDTH * ROW_NUM);
    layout.getChildren().add(background);

    // layout.getChildren().remove(background);
    */
}
