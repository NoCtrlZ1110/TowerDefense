package TowerDefense;

import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


import static TowerDefense.GameField.*;
import static TowerDefense.Sound.clickSound;
import static TowerDefense.Sound.showMuteBtn;

public class SelectRoad {

    public static Pane selectWorldLayout;

    public static void selectScreen(Stage stage) {
        stage.close();
        stage.setTitle("Choose World Map");
        stage.centerOnScreen();

        selectWorldLayout = new Pane();
        Scene selectScence = new Scene(selectWorldLayout, 800, 540);

        Rectangle selectedWorld = new Rectangle(330, 200);

        selectedWorld.setVisible(false);
        selectedWorld.setFill(Color.TRANSPARENT);
        selectedWorld.setStroke(Color.WHITESMOKE);
        selectedWorld.setStrokeWidth(3);
        selectedWorld.setArcHeight(10);
        selectedWorld.setArcWidth(10);


        imageObject world = new imageObject("file:images/SelectScreen/SelectScreen.png");
        imageObject selectBtn = new imageObject("file:images/SelectScreen/SelectBtn.png");
        imageObject world1 = new imageObject("file:images/SelectScreen/world1.png");
        imageObject world2 = new imageObject("file:images/SelectScreen/world2.png");
        selectWorldLayout.getChildren().addAll(world, selectBtn, world1, world2, selectedWorld);
        showMuteBtn(selectWorldLayout);

        selectBtn.setLocation(320, 460);
//        world1.setLocation(37,189);
//        world2.setLocation(417,189);

        world1.setOnMouseEntered(event -> world1.setCursor(Cursor.HAND));
        world1.setOnMouseExited(event -> world1.setCursor(Cursor.DEFAULT));
        world1.setOnMouseClicked(event -> {

            clickSound();
            selectedWorld.setVisible(true);
            selectedWorld.setLayoutX(42);
            selectedWorld.setLayoutY(195);
            world_select = 1;

        });

        world2.setOnMouseEntered(event -> world2.setCursor(Cursor.HAND));
        world2.setOnMouseExited(event -> world2.setCursor(Cursor.DEFAULT));
        world2.setOnMouseClicked(event -> {
            clickSound();
            selectedWorld.setVisible(true);
            selectedWorld.setLayoutX(425);
            selectedWorld.setLayoutY(195);
            world_select = 2;
        });


        selectBtn.setOnMouseEntered(event -> selectBtn.setCursor(Cursor.HAND));
        selectBtn.setOnMouseExited(event -> selectBtn.setCursor(Cursor.DEFAULT));
        selectBtn.setOnMouseClicked(event -> {
            clickSound();
            if (world_select != 0) {
                gameScreen(stage);
            }
        });



        stage.setScene(selectScence);

        stage.show();
    }
}
