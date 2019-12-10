package TowerDefense;

import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.Window;


import static TowerDefense.GameField.*;
import static TowerDefense.Sound.*;

public class SelectRoad {
    public static Pane selectWorldLayout;

    public static void selectScreen(Stage stage) {
        stage.close();
        stage.setTitle("Choose World Map");
        stage.centerOnScreen();

        selectWorldLayout = new Pane();
        Scene selectScene = new Scene(selectWorldLayout, 800, 540);

        Rectangle selectedWorld = new Rectangle(330, 200);

        selectedWorld.setVisible(false);
        selectedWorld.setFill(Color.TRANSPARENT);
        selectedWorld.setStroke(Color.WHITESMOKE);
        selectedWorld.setStrokeWidth(3);
        selectedWorld.setArcHeight(10);
        selectedWorld.setArcWidth(10);

        ImageObject world = new ImageObject("file:images/SelectScreen/SelectScreen.png");
        ImageObject selectBtn = new ImageObject("file:images/SelectScreen/SelectBtn.png");
        ImageObject loadgameBtn = new ImageObject("file:images/SelectScreen/loadgameBtn.png");
        ImageObject world1 = new ImageObject("file:images/SelectScreen/world1.png");
        ImageObject world2 = new ImageObject("file:images/SelectScreen/world2.png");
        selectWorldLayout.getChildren().addAll(world, selectBtn, world1, world2, selectedWorld, loadgameBtn);
        showMuteBtn(selectWorldLayout);

        selectBtn.setLocation(508, 460);
        loadgameBtn.setLocation(134,460);

        loadgameBtn.setOnMouseEntered(event -> loadgameBtn.setCursor(Cursor.HAND));
        loadgameBtn.setOnMouseExited(event -> loadgameBtn.setCursor(Cursor.DEFAULT));
        loadgameBtn.setOnMouseClicked(event -> {
            System.out.println("-> Load Game");
            boolean load_ok = GameField.loadGame();
            // System.out.println(load_ok);
            if (load_ok) {
                gameScreen(stage);
            }
            chooseSound();
        });

        world1.setOnMouseEntered(event -> world1.setCursor(Cursor.HAND));
        world1.setOnMouseExited(event -> world1.setCursor(Cursor.DEFAULT));
        world1.setOnMouseClicked(event -> {
            chooseSound();
            selectedWorld.setVisible(true);
            selectedWorld.setLayoutX(42);
            selectedWorld.setLayoutY(195);
            world_select = 1;
        });

        world2.setOnMouseEntered(event -> world2.setCursor(Cursor.HAND));
        world2.setOnMouseExited(event -> world2.setCursor(Cursor.DEFAULT));
        world2.setOnMouseClicked(event -> {
            chooseSound();
            selectedWorld.setVisible(true);
            selectedWorld.setLayoutX(425);
            selectedWorld.setLayoutY(195);
            world_select = 2;
        });

        selectBtn.setOnMouseEntered(event -> selectBtn.setCursor(Cursor.HAND));
        selectBtn.setOnMouseExited(event -> selectBtn.setCursor(Cursor.DEFAULT));
        selectBtn.setOnMouseClicked(event -> {
            chooseSound();
            if (world_select != 0) {
                gameScreen(stage);
            }
        });

        stage.setScene(selectScene);
        stage.show();
    }

    public static Window getMasterWindow() {
        return selectWorldLayout.getScene().getWindow();
    }
}
