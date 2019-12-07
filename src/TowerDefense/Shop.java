package TowerDefense;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import static TowerDefense.CONSTANT.*;
import static TowerDefense.GameField.*;
import static TowerDefense.Sound.chooseSound;
import static TowerDefense.Sound.shovelSound;

public class Shop {
    static Rectangle selectedItem = new Rectangle(70, 94);
    static int currentItem = 0;
    static boolean selling = false;
    static boolean buying = false;

    private static Pane shopPane = new Pane();

    static Label coin = new Label(Integer.toString(getMoney()));

    private static imageObject shopBar = new imageObject("file:images/Shop/shopBar.png");
    private static imageObject towerType1 = new imageObject("file:images/Shop/TowerType1.png");
    private static imageObject towerType2 = new imageObject("file:images/Shop/TowerType2.png");
    private static imageObject towerType3 = new imageObject("file:images/Shop/TowerType3.png");
    private static imageObject shovel = new imageObject("file:images/Shop/shovel.png");

    static imageObject placingTower1 = new imageObject(pathNormalTower);
    static imageObject placingTower2 = new imageObject(pathSniperTower);
    static imageObject placingTower3 = new imageObject(pathMachineGunTower);
    static imageObject using_shovel = new imageObject("file:images/Shop/shovel_noback.png");

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
        shovel.setLocation((int) shopBar.getTranslateX() + 13, (int) shopBar.getTranslateY() + 398);
        shovel.scaleTo(70, 70);

        coin.setTranslateX(shopBar.getTranslateX() + 33);
        coin.setTranslateY(shopBar.getTranslateY() + 69);
        // coin.setMinWidth(70-27);
        // coin.setMaxWidth(70-27);
        coin.setAlignment(Pos.CENTER);
        // final Font AERIAL_FONT = new Font(PATH_FONT, 13);
        coin.setFont(new Font("Aerial", 13));
        shopPane.getChildren().addAll(towerType1, towerType2, towerType3, shovel, coin, selectedItem);
        layout.getChildren().add(shopPane);
        handleClickItem();
    }

    private static void handleClickItem() {
        towerType1.setOnMouseEntered(event -> shopPane.setCursor(isPaused ? Cursor.DEFAULT : Cursor.HAND));
        towerType1.setOnMouseExited(event -> shopPane.setCursor(Cursor.DEFAULT));
        towerType1.setOnMouseClicked(event -> {
            if (isPaused) return;
            chooseSound();
            cancelSelling();
            buying = true;
            currentItem = 1;
            selectedItem.setVisible(true);
            selectedItem.setLayoutY(shopBar.getTranslateY() + 97);
            if (isStarted && !layout.getChildren().contains(placingTower1))
                layout.getChildren().add(placingTower1);
            layout.getChildren().remove(placingTower2);
            layout.getChildren().remove(placingTower3);
            placingTower1.setLocation((int)shopBar.getTranslateX() + 40, (int)shopBar.getTranslateY() + 150);
            System.out.println("Building Tower 1");
        });

        towerType2.setOnMouseEntered(event -> shopPane.setCursor(isPaused ? Cursor.DEFAULT : Cursor.HAND));
        towerType2.setOnMouseExited(event -> shopPane.setCursor(Cursor.DEFAULT));
        towerType2.setOnMouseClicked(event -> {
            if (isPaused) return;
            chooseSound();
            cancelSelling();
            buying = true;
            currentItem = 2;
            selectedItem.setVisible(true);
            selectedItem.setLayoutY(shopBar.getTranslateY() + 197);
            if (isStarted && !layout.getChildren().contains(placingTower2))
                layout.getChildren().add(placingTower2);
            layout.getChildren().remove(placingTower1);
            layout.getChildren().remove(placingTower3);
            placingTower2.setLocation((int)shopBar.getTranslateX() + 40,(int)shopBar.getTranslateY() + 250);
            System.out.println("Building Tower 2");
        });

        towerType3.setOnMouseEntered(event -> shopPane.setCursor(isPaused ? Cursor.DEFAULT : Cursor.HAND));
        towerType3.setOnMouseExited(event -> shopPane.setCursor(Cursor.DEFAULT));
        towerType3.setOnMouseClicked(event -> {
            if (isPaused) return;
            chooseSound();
            cancelSelling();
            buying = true;
            currentItem = 3;
            selectedItem.setVisible(true);
            selectedItem.setLayoutY(shopBar.getTranslateY() + 297);
            if (isStarted && !layout.getChildren().contains(placingTower3))
                layout.getChildren().add(placingTower3);
            layout.getChildren().remove(placingTower1);
            layout.getChildren().remove(placingTower2);
            placingTower3.setLocation((int)shopBar.getTranslateX() + 40,(int)shopBar.getTranslateY() + 350);
            System.out.println("Building Tower 3");
        });

        // Remove tower
        shovel.setOnMouseEntered(event -> shopPane.setCursor(isPaused ? Cursor.DEFAULT : Cursor.HAND));
        shovel.setOnMouseExited(event -> shopPane.setCursor(Cursor.DEFAULT));
        shovel.setOnMouseClicked(event -> {
            if (isPaused) return;
            shovelSound();
            cancelBuying();
            selling = true;
            using_shovel.setLocation((int) shovel.getTranslateX() + 30, (int) shovel.getTranslateY() - 40);
            if (!layout.getChildren().contains(using_shovel)) layout.getChildren().add(using_shovel);
        });
    }

    public static void cancelBuying() {
        if (!buying)
            return;

        buying = false;
        selectedItem.setVisible(false);
        // layout.getChildren().removeAll(placingTower1, placingTower2, placingTower3);
        if (currentItem == 1)
            layout.getChildren().remove(placingTower1);
        else if (currentItem == 2)
            layout.getChildren().remove(placingTower2);
        else if (currentItem == 3)
            layout.getChildren().remove(placingTower3);

        currentItem = 0;
    }

    public static void cancelSelling() {
        if (!selling)
            return;

        selling = false;
        layout.getChildren().remove(using_shovel);
    }

    public static int getCurrentItem() {
        return currentItem;
    }
}
