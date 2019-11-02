package Tower;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import static Tower.CONSTANT.COL_NUM;
import static Tower.CONSTANT.ROW_NUM;
import static Tower.Map.*;

public class Screens {
    public static void welcomeScreen(Stage stage) {
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

    public static void gameScreen(Stage stage) {
        stage.close();
        Pane layout = new Pane();
        BaseObject[][] grassArr = new BaseObject[ROW_NUM][COL_NUM];
        for (int i = 0; i<ROW_NUM; i++)
            for (int j = 0; j<COL_NUM; j++)
            {
                System.out.println(i+" "+j+"\n");
                grassArr[i][j] = new BaseObject("file:images/grass.png");
                if (getType(i,j) == 0)
                {
                    grassArr[i][j].setFitHeight(80);
                    grassArr[i][j].setFitWidth(80);
                    grassArr[i][j].setLocation(j*80,i*80);
                    layout.getChildren().add(grassArr[i][j]);
                }
            }

//        BaseObject grass = new BaseObject("file:images/grass.png");
//        grass.setFitHeight(80);
//        grass.setFitWidth(80);
//        layout.getChildren().add(grass);
        Scene gameScene = new Scene(layout, 1280,800); // 16 x 10; 80px per block
        stage.setScene(gameScene);
        stage.centerOnScreen();
        stage.show();
    }


}
