package TowerDefense;

import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import static TowerDefense.CONSTANT.pathNormalTower;
import static TowerDefense.GameField.*;
import static TowerDefense.GameTile.*;

public class Shop {


    static Rectangle selectedItem = new Rectangle(70, 94);
    static int currentItem = 0;
    static boolean selling = false;

    private static Pane shopPane = new Pane();

    static Label coin = new Label(Integer.toString(getMoney()));

    private static imageObject shopBar = new imageObject("file:images/shopBar.png");
    private static imageObject towerType1 = new imageObject("file:images/TowerType1.png");
    private static imageObject towerType2 = new imageObject("file:images/TowerType2.png");
    private static imageObject shovel = new imageObject("file:images/shovel.png");
    private static imageObject towerType3 = new imageObject("file:images/TowerType3.png");


    static imageObject placingTower1 = new imageObject(pathNormalTower);
    static imageObject placingTower2 = new imageObject(pathNormalTower);
    static imageObject placingTower3 = new imageObject(pathNormalTower);
    static imageObject using_shovel = new imageObject("file:images/shovel_noback.png");

    public static void showShopBar() {
        selectedItem.setVisible(false);
        selectedItem.setFill(Color.TRANSPARENT);
        selectedItem.setStroke(Color.WHITESMOKE);
        selectedItem.setStrokeWidth(2);
        selectedItem.setLayoutX(shopBar.getTranslateX() + 32);
        selectedItem.setArcHeight(5);
        selectedItem.setArcWidth(5);

        layout.getChildren().add(shopBar);
        shopBar.setLocation(20, 30);
        //+15,+100
        towerType1.setLocation((int) shopBar.getTranslateX() + 14, (int) shopBar.getTranslateY() + 100);
        towerType1.scaleTo(67, 90);
        towerType2.setLocation((int) shopBar.getTranslateX() + 14, (int) shopBar.getTranslateY() + 200);
        towerType2.scaleTo(67, 90);
        towerType3.setLocation((int) shopBar.getTranslateX() + 14, (int) shopBar.getTranslateY() + 300);
        towerType3.scaleTo(67, 90);
        shovel.scaleTo(70, 70);
        shovel.setLocation((int) shopBar.getTranslateX() + 13, (int) shopBar.getTranslateY() + 398);
        coin.setTranslateX(shopBar.getTranslateX() + 33);
        coin.setTranslateY(shopBar.getTranslateY() + 69);
        // coin.setMinWidth(70-27);
        // coin.setMaxWidth(70-27);
        coin.setAlignment(Pos.CENTER);
        coin.setFont(new Font("Aerial",13));
        shopPane.getChildren().addAll(towerType1, towerType2, towerType3, shovel, coin, selectedItem);
        layout.getChildren().add(shopPane);
        handleClickItem();
    }

    private static void handleClickItem() {
        towerType1.setOnMouseEntered(event -> shopPane.setCursor(Cursor.HAND));
        towerType1.setOnMouseExited(event -> shopPane.setCursor(Cursor.DEFAULT));
        towerType1.setOnMouseClicked(event -> {
            currentItem = 1;
            selectedItem.setVisible(true);
            selectedItem.setLayoutY(shopBar.getTranslateY() + 97);
            System.out.println("Building Tower 1");
        });

        towerType2.setOnMouseEntered(event -> shopPane.setCursor(Cursor.HAND));
        towerType2.setOnMouseExited(event -> shopPane.setCursor(Cursor.DEFAULT));
        towerType2.setOnMouseClicked(event -> {
            currentItem = 2;
            selectedItem.setVisible(true);
            selectedItem.setLayoutY(shopBar.getTranslateY() + 197);

            System.out.println("Building Tower 2");
        });

        towerType3.setOnMouseEntered(event -> shopPane.setCursor(Cursor.HAND));
        towerType3.setOnMouseExited(event -> shopPane.setCursor(Cursor.DEFAULT));
        towerType3.setOnMouseClicked(event -> {
            currentItem = 3;
            selectedItem.setVisible(true);
            selectedItem.setLayoutY(shopBar.getTranslateY() + 297);

            System.out.println("Building Tower 3");
        });

        // Remove tower
        shovel.setOnMouseEntered(event -> shopPane.setCursor(Cursor.HAND));
        shovel.setOnMouseExited(event -> shopPane.setCursor(Cursor.DEFAULT));
        shovel.setOnMouseClicked(event -> {
            selling = true;
            using_shovel.setLocation((int) shovel.getTranslateX() + 30, (int) shovel.getTranslateY() - 40);
            if (!layout.getChildren().contains(using_shovel)) layout.getChildren().add(using_shovel);
        }
        );
    }
}
