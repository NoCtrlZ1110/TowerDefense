package TowerDefense;

import javafx.animation.PathTransition;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.util.Duration;

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
        for (int i = 0; i<ROW_NUM; i++)
            for (int j = 0; j<COL_NUM; j++) {
                tiled[i][j] = new GameEntity(pathTile+getTileType(i,j)+".png");
                tiled[i][j].setFitHeight(80);
                tiled[i][j].setFitWidth(80);
                tiled[i][j].setLocation(j * 80, i * 80);
                layout.getChildren().add(tiled[i][j]);
            }

        Enemy minion = new Enemy(0,720,pathRedEnemy);
        minion.setFitHeight(80);
        minion.setFitWidth(80);
        //minion.setLocation(400,400);


        final Path path = new Path();
        //Creating the MoveTo path element
        path.getElements().add(new MoveTo(0,760));
        for (int i = 0; i<ROAD_NUM; i++)
            path.getElements().add(new LineTo(roadLocation[i][0],roadLocation[i][1]));

        //Creating a path transition
        PathTransition pathTransition = new PathTransition();

        //Setting the duration of the path transition
        pathTransition.setDuration(Duration.millis(3.5*5100));

        //Setting the node for the transition
        pathTransition.setNode(minion);

        //Setting the path
        pathTransition.setPath(path);
//
//        //Setting the orientation of the path
//        pathTransition.setOrientation(PathTransition.OrientationType.
//                ORTHOGONAL_TO_TANGENT);

        //Setting the cycle count for the transition
        pathTransition.setCycleCount(50);

        //Setting auto reverse value to false
        pathTransition.setAutoReverse(true);

        //Playing the animation
        pathTransition.play();

        //Creating a Group object





//        BaseObject grass = new BaseObject("file:images/grass.png");
//        grass.setFitHeight(80);
//        grass.setFitWidth(80);
//        layout.getChildren().add(grass);
        layout.getChildren().add(minion);
        Scene gameScene = new Scene(layout, 1280,800); // 16 x 10; 80px per block
        stage.setScene(gameScene);
        stage.centerOnScreen();
        stage.show();
    }


}
