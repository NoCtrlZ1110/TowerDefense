package Tower;

import javafx.application.Application;
import javafx.stage.Stage;
import static Tower.Map.ImportMap;
import static Tower.Screens.*;

public class TowerDefense extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        ImportMap();
        welcomeScreen(primaryStage);



    }


}
