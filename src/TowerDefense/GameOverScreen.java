package TowerDefense;

import javafx.scene.Cursor;

import static TowerDefense.GameField.layout;
import static TowerDefense.GameField.stopGame;
import static TowerDefense.PauseScreen.pauseBlack;
import static TowerDefense.Sound.playGameOverSound;

public class GameOverScreen {
    private static ImageObject gameOver = new ImageObject("file:images/GameOver/gameover.png");

    public static void showGameOverScreen() {
        playGameOverSound();
        if (!layout.getChildren().contains(pauseBlack)) layout.getChildren().add(pauseBlack);
        pauseBlack.setOpacity(0.5);
        gameOver.setLocation(350, 200);
        layout.setCursor(Cursor.DEFAULT);
        if (!layout.getChildren().contains(gameOver)) layout.getChildren().add(gameOver);
        stopGame();
        System.out.println("Game over!");
    }
}
