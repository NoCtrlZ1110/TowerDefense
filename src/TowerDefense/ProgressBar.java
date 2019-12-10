package TowerDefense;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import static TowerDefense.GameField.layout;
import static TowerDefense.GameWaves.getTotalWaves;
import static TowerDefense.GameWaves.getRunningWaveId;
import static javafx.scene.paint.Color.GREENYELLOW;

public class ProgressBar {
    private static final int PROGRESSBAR_X = 1080; // 1080
    private static final int PROGRESSBAR_Y = 725; // 725

    public static Pane progressPane = new Pane();
    public static ImageObject progressBar = new ImageObject("file:images/ProgressBar/levelProgress.png");
    public static ImageObject head = new ImageObject("file:images/ProgressBar/head.png");
    public static Rectangle progress = new Rectangle();

    public static void initProgessBar() {
        if (!layout.getChildren().contains(progress))
            layout.getChildren().add(progress);
        if (!layout.getChildren().contains(progressPane))
            layout.getChildren().add(progressPane);
        progressPane.setLayoutX(PROGRESSBAR_X);
        progressPane.setLayoutY(PROGRESSBAR_Y);
        progress.setX(progressPane.getLayoutX()+6);
        progress.setY(progressPane.getLayoutY()+45);
        progress.setHeight(12);
        progress.setWidth(150);
        progress.setFill(GREENYELLOW);
//        head.setLocation(progressPane.getLayoutX()+6,progess.getWidth());
        progressPane.getChildren().add(progressBar);
        progressPane.getChildren().add(head);
        head.setLayoutY(progressPane.getLayoutY()+37);
        head.setLayoutX(progress.getX()+progress.getWidth()-10);
        if (!layout.getChildren().contains(head))
            layout.getChildren().add(head);
    }

    public static void updateProgressBar(EnemiesWave running) {
        progress.setWidth((getTotalWaves()-getRunningWaveId()-1)*29-running.getWaveRate()*30>=0? (getTotalWaves()-getRunningWaveId()-1)*29-running.getWaveRate()*30:0);
        head.setLayoutX(progress.getX()+progress.getWidth()-10>=0? progress.getX()+progress.getWidth()-10:progress.getX()+progress.getWidth());
    }
}
