package TowerDefense;

import javafx.scene.Cursor;

import static TowerDefense.CONSTANT.COL_NUM;
import static TowerDefense.CONSTANT.TILE_WIDTH;
import static TowerDefense.GameField.pauseGame;
import static TowerDefense.GameField.*;

public class PauseScreen {
    static imageObject playBtn = new imageObject("file:images/play.png");
    static imageObject pauseBtn = new imageObject("file:images/pause.png");

    public static void showPauseBtn() {
        playBtn.setLocation(TILE_WIDTH*COL_NUM -70, 80);
        playBtn.scaleTo(40, 40);
        playBtn.setOnMouseEntered(event -> playBtn.setCursor(Cursor.HAND));
        playBtn.setOnMouseExited(event -> playBtn.setCursor(Cursor.DEFAULT));
        if (!layout.getChildren().contains(playBtn))
            layout.getChildren().add(playBtn);
        playBtn.setVisible(false);

        pauseBtn.setLocation(TILE_WIDTH*COL_NUM -70, 80);
        pauseBtn.scaleTo(40, 40);
        pauseBtn.setOnMouseEntered(event -> pauseBtn.setCursor(Cursor.HAND));
        pauseBtn.setOnMouseExited(event -> pauseBtn.setCursor(Cursor.DEFAULT));
        if (!layout.getChildren().contains(pauseBtn))
            layout.getChildren().add(pauseBtn);
        pauseBtn.setVisible(true);

        pauseBtn.setOnMouseClicked(event -> {
            pauseGame();
            pauseBtn.setVisible(false);
            playBtn.setVisible(true);
        });
        playBtn.setOnMouseClicked(event -> {
            resumeGame();
            pauseBtn.setVisible(true);
            playBtn.setVisible(false);
        });
    }
    /*
    imageObject background = new imageObject("file:images/black_background.png");
    background.setLocation(0, 0);
    background.scaleTo(TILE_WIDTH * COL_NUM, TILE_WIDTH * ROW_NUM);
    layout.getChildren().add(background);

    // layout.getChildren().remove(background);
    */
}
