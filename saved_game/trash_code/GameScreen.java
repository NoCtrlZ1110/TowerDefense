package TowerDefense;

import javafx.stage.Stage;

import static TowerDefense.CONSTANT.*;
import static TowerDefense.GameField.road;
import static TowerDefense.Sound.pauseWelcomeMusic;
import static TowerDefense.Sound.playGameScreenMusic;

public class GameScreen extends Screen {
    public GameScreen(Stage stage) {
        super(stage, TILE_WIDTH * COL_NUM, TILE_WIDTH * ROW_NUM);

        pauseWelcomeMusic();

        imageObject background = new imageObject("file:images/back.png");
        background.setLocation(0, 0);
        background.scaleTo(TILE_WIDTH * COL_NUM, TILE_WIDTH * ROW_NUM);
        road.setOpacity(0);
        pane.getChildren().addAll(background, road);
        playGameScreenMusic();
    }
}
