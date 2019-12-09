package TowerDefense;

import javafx.scene.Cursor;

import static TowerDefense.GameField.layout;
import static TowerDefense.GameField.stopGame;
import static TowerDefense.PauseScreen.pauseBlack;
import static TowerDefense.Sound.playGameOverSound;

public class GameOverScreen {
    static imageObject GameOver = new imageObject("file:images/GameOver/gameover.png");

    public static void showGameOverScreen() {
        playGameOverSound();
        if (!layout.getChildren().contains(pauseBlack)) layout.getChildren().add(pauseBlack);
        pauseBlack.setOpacity(0.5);
        GameOver.setLocation(350,200);
        layout.setCursor(Cursor.DEFAULT);
        if (!layout.getChildren().contains(GameOver)) layout.getChildren().add(GameOver);
        stopGame();
        System.out.println("Game over!");
    }



}
