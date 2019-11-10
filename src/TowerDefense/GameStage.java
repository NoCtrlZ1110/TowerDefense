package TowerDefense;

import javafx.application.Application;
import javafx.stage.Stage;

import static TowerDefense.GameTile.ImportMap;
import static TowerDefense.GameField.*;
import static TowerDefense.GameTile.ImportRoad;

public class GameStage extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        ImportMap();
        ImportRoad();
        welcomeScreen(primaryStage);
        //gameScreen(primaryStage);


    }


}
