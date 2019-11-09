package TowerDefense;

import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Date;

import static TowerDefense.CONSTANT.*;
import static TowerDefense.Map.*;

public class GameField {
    public static void welcomeScreen(Stage stage) {
        GameEntity power = new GameEntity("file:images/start.png");
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

        GameEntity Welcome = new GameEntity("file:images/Welcome_screen.png");
        //Welcome.setLocation(100,100);
        pane.getChildren().add(Welcome);
        pane.getChildren().add(startBtn);
        stage.setTitle("Tower Defense 1.0");
        stage.setScene(new Scene(pane, 960, 540));
        stage.show();

    }

    public static void gameScreen(Stage stage) {
        stage.close();
        Pane layout = new Pane();
        GameEntity[][] tiled = new GameEntity[ROW_NUM][COL_NUM];


        for (int i = 0; i < ROW_NUM; i++)
            for (int j = 0; j < COL_NUM; j++) {
                tiled[i][j] = new GameEntity(pathTile + getTileType(i, j) + ".png");
                tiled[i][j].setFitHeight(80);
                tiled[i][j].setFitWidth(80);
                tiled[i][j].setLocation(j * 80, i * 80);
                layout.getChildren().add(tiled[i][j]);
            }


        final Path path = new Path();
        //Creating the MoveTo path element
        path.getElements().add(new MoveTo(-80, 760));
        for (int i = 0; i < ROAD_NUM; i++)
            path.getElements().add(new LineTo(roadLocation[i][0], roadLocation[i][1]));

//            final Timeline timeline = new Timeline(new KeyFrame(Duration.millis(10000), new EventHandler<ActionEvent>() {
//                @Override
//                public void handle(ActionEvent actionEvent) {
//                }
//            }));
        ArrayList<Enemy> enemies = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Enemy minion = new Enemy(-80, 720, pathRedEnemy);
            minion.setFitHeight(80);
            minion.setFitWidth(80);
            minion.setSpeed(2);
            enemies.add(minion);
            layout.getChildren().add(minion);
        }
//        minion.move(path);

        KeyFrame[] keyFrames;
        Timeline timeline = new Timeline();
        for (int i = 0; i<enemies.size(); i++)
        {
            Enemy e = enemies.get(i);
            KeyFrame moveEnemy = new KeyFrame(Duration.millis(i*1000), event -> {
                e.move(path);
            });
            timeline.getKeyFrames().add(moveEnemy);
        }


            //minion.setLocation(400,400);


//        BaseObject grass = new BaseObject("file:images/grass.png");
//        grass.setFitHeight(80);
//        grass.setFitWidth(80);
//        layout.getChildren().add(grass);

            Scene gameScene = new Scene(layout, 1280, 800); // 16 x 10; 80px per block
        stage.setScene(gameScene);
        stage.centerOnScreen();
        timeline.play();
        stage.show();
    }


}
