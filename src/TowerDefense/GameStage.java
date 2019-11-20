package TowerDefense;

import javafx.application.Application;
import javafx.stage.Stage;

import static TowerDefense.GameTile.importMap;
import static TowerDefense.GameTile.importRoad;
import static TowerDefense.GameField.*;

public class GameStage extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        importMap();
        importRoad();
        welcomeScreen(primaryStage);
        // gameScreen(primaryStage);
    }
}
