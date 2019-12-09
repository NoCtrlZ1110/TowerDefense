package TowerDefense;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import static TowerDefense.GameField.layout;
import static TowerDefense.GameWaves.getTotalWaves;
import static TowerDefense.GameWaves.running_wave_id;
import static javafx.scene.paint.Color.GREENYELLOW;

public class ProgressBar {

    public static Pane progressPane = new Pane();
    public static imageObject progressBar = new imageObject("file:images/ProgressBar/levelProgress.png");
    public static imageObject head = new imageObject("file:images/ProgressBar/head.png");
    public static Rectangle progess = new Rectangle();

    public static void initProgessBar()
    {
        if (!layout.getChildren().contains(progess)) layout.getChildren().add(progess);
        if (!layout.getChildren().contains(progressPane)) layout.getChildren().add(progressPane);
        progressPane.setLayoutX(1080);
        progressPane.setLayoutY(725);
        progess.setX(progressPane.getLayoutX()+6);
        progess.setY(progressPane.getLayoutY()+45);
        progess.setHeight(12);
        progess.setWidth(150);
        progess.setFill(GREENYELLOW);
//        head.setLocation(progressPane.getLayoutX()+6,progess.getWidth());
        progressPane.getChildren().add(progressBar);
        progressPane.getChildren().add(head);
        head.setLayoutY(762);
        head.setLayoutX(progess.getX()+45);
        if (!layout.getChildren().contains(head)) layout.getChildren().add(head);

    }

    public static void updateProgessBar(EnemiesWave running)
    {
                progess.setWidth((getTotalWaves()-running_wave_id-1)*29-running.getWaveRate()*30);
                head.setLayoutX(progess.getX()+progess.getWidth()-5);


    }
}
