package Tower;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class TowerDefense extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        welcomeScreen(primaryStage);


    }

    public void welcomeScreen(Stage stage) {
        BaseObject power = new BaseObject("file:images/start.png");
        Pane pane = new Pane();
        Button startBtn = new Button(" Start ");
        startBtn.setGraphic(power);
        startBtn.setLayoutX(90);
        startBtn.setLayoutY(433);
        startBtn.setMinWidth(126);
        startBtn.setStyle("border-radius: 50%;");
        startBtn.setOnAction(event -> {
            if (event.getSource() == startBtn) {
                gameScreen(stage);
            }
        });

        BaseObject Welcome = new BaseObject("file:images/Welcome_screen.png");
        //Welcome.setLocation(100,100);
        pane.getChildren().add(Welcome);
        pane.getChildren().add(startBtn);
        stage.setTitle("Tower Defense 1.0");
        stage.setScene(new Scene(pane, 960, 540));
        stage.show();

    }

    public void gameScreen(Stage stage) {
        Parent root = new Parent() {
            @Override
            public void impl_updatePeer() {
                super.impl_updatePeer();
            }
        };
        Scene gameScene = new Scene(root, 1500,900);

        stage.setScene(gameScene);
        stage.centerOnScreen();

    }
}
