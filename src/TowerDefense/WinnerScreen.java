package TowerDefense;

import javafx.scene.Cursor;

import static TowerDefense.GameField.layout;
import static TowerDefense.GameField.stopGame;
import static TowerDefense.PauseScreen.pauseBlack;
import static TowerDefense.Sound.playWinMusic;

public class WinnerScreen {
    static ImageObject winner = new ImageObject("file:images/CompleteScreen/Winner.png");

    public static void showCompletedScreen() {
        playWinMusic();
        if (!layout.getChildren().contains(pauseBlack)) layout.getChildren().add(pauseBlack);
        pauseBlack.setOpacity(0.5);
        winner.setLocation(350,200);
        layout.setCursor(Cursor.DEFAULT);
        if (!layout.getChildren().contains(winner)) layout.getChildren().add(winner);
        stopGame(); // đã stop trước khi showCompletedScreen()
        System.out.println("You have cleared this map!");
    }
}
