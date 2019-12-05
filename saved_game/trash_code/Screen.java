package TowerDefense;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Screen {
    private Stage stage;
    protected static Pane pane;
    public static Scene scene;

    public Screen(int width, int height) {
        pane = new Pane();
        scene = new Scene(pane, width, height);
    }

    public Screen(Stage stage, int width, int height) {
        this.stage = stage;
        if (GameStage.is_first_layout) {
            GameStage.is_first_layout = false;
        } else {
            stage.close();
        }
        pane = new Pane();
        scene = new Scene(pane, width, height);
    }

    public void active() {
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
}
