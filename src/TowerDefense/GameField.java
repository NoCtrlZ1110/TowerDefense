package TowerDefense;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;

import static TowerDefense.CONSTANT.*;
import static TowerDefense.Map.*;

public class GameField {
    static Rectangle border = new Rectangle(100, 100);

    public static void welcomeScreen(Stage stage) {

        imageObject power = new imageObject("file:images/start.png");
        Pane pane = new Pane();
        Button startBtn = new Button(" Start ");
        startBtn.setGraphic(power);
        startBtn.setLayoutX(90);
        startBtn.setLayoutY(433);
        startBtn.setMinWidth(126);
        startBtn.setOnAction(event -> {
            if (event.getSource() == startBtn) {
                gameScreen(stage);
            }
        });

        imageObject Welcome = new imageObject("file:images/Welcome_screen.png");
        //Welcome.setLocation(100,100);
        pane.getChildren().add(Welcome);
        pane.getChildren().add(startBtn);
        stage.setTitle("Tower Defense 1.0");
        stage.setScene(new Scene(pane, 960, 540));
        stage.getIcons().add(new Image("file:images/love.jpg"));
        stage.show();

    }

    public static Pane layout;

    public static void gameScreen(Stage stage) {
        stage.close();
        layout = new Pane();
        Scene gameScene = new Scene(layout, 1280, 800); // 16 x 10; 80px per block

        imageObject[][] tiled = new imageObject[ROW_NUM][COL_NUM];


        for (int i = 0; i < ROW_NUM; i++)
            for (int j = 0; j < COL_NUM; j++) {
                tiled[i][j] = new imageObject(pathTile + getTileType(i, j) + ".png");
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

        ArrayList<Enemy> enemies = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Enemy minion = new Enemy(-80, 720, pathRedEnemy);
            minion.setFitHeight(70);
            minion.setFitWidth(70);
            minion.setSpeed(2);
            enemies.add(minion);
            layout.getChildren().add(minion);
        }
//        minion.move(path);

        //KeyFrame[] keyFrames;
        Timeline timeline = new Timeline();
        for (int i = 0; i < enemies.size(); i++) {
            Enemy e = enemies.get(i);
            KeyFrame moveEnemy = new KeyFrame(Duration.millis(i * 800), event -> {
                e.move(path);
            });
            timeline.getKeyFrames().add(moveEnemy);
        }

        //Hiện thanh máu liên tục theo thời gian
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                enemies.forEach(Enemy::showHP);

            }
        };


        border.setStroke(Color.DARKRED);
        border.setStrokeWidth(3);
        border.setFill(Color.TRANSPARENT);
        border.setArcWidth(20.0);
        border.setArcHeight(20.0);

        layout.setOnMouseMoved(event -> {
            Point location = TowerBuildLocation(event);

            if (location != null) {
                gameScene.setCursor(Cursor.HAND);
                border.setX(location.getX() + 33);
                border.setY(location.getY() + 33);
            }
            else
            gameScene.setCursor(Cursor.DEFAULT);
        });

        ArrayList<Tower> towers = new ArrayList<>();


        layout.setOnMouseClicked(event ->
        {
            Point location = TowerBuildLocation(event);
            if (location != null)
            {
                Tower tower = new Tower("file:images/Tower.png");
                tower.showTower(location);
                towers.add(tower);
                setMapType(location.getX()/80,location.getY()/80,6);
                setMapType(location.getX()/80,location.getY()/80+1,6);
                setMapType(location.getX()/80+1,location.getY()/80,6);
                setMapType(location.getX()/80+1,location.getY()/80+1,6);

            }



        });

        timer.start();
        stage.getIcons().add(new Image("file:images/love.jpg"));

        stage.setScene(gameScene);
        stage.centerOnScreen();

        timeline.play();
        stage.show();
    }


}
