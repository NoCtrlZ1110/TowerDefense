package TowerDefense;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;

import static TowerDefense.CONSTANT.*;
import static TowerDefense.GameField.*;
import static TowerDefense.Sound.*;

public class Shop {
    private static final double SELL_RATE = 0.8;

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
    private static imageObject outOfMoney1 = new imageObject("file:images/Shop/OutOfMoney1.png");
    private static imageObject outOfMoney2 = new imageObject("file:images/Shop/OutOfMoney2.png");
    private static imageObject outOfMoney3 = new imageObject("file:images/Shop/OutOfMoney3.png");
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

        outOfMoney1.setVisible(false);
        outOfMoney2.setVisible(false);
        outOfMoney3.setVisible(false);

        outOfMoney1.setLocation((int) shopBar.getTranslateX() + 14, (int) shopBar.getTranslateY() + 100);
        outOfMoney1.scaleTo(67, 90);
        outOfMoney2.setLocation((int) shopBar.getTranslateX() + 14, (int) shopBar.getTranslateY() + 200);
        outOfMoney2.scaleTo(67, 90);
        outOfMoney3.setLocation((int) shopBar.getTranslateX() + 14, (int) shopBar.getTranslateY() + 300);
        outOfMoney3.scaleTo(67, 90);

        shopPane.getChildren().addAll(towerType1, towerType2, towerType3, shovel, coin, selectedItem,outOfMoney1,outOfMoney2,outOfMoney3);
        layout.getChildren().add(shopPane);
        handleClickItem();
    }

    static void checkPrice()
    {
        if (getMoney()<10) outOfMoney1.setVisible(true); else  outOfMoney1.setVisible(false);
        if (getMoney()<20) outOfMoney2.setVisible(true); else  outOfMoney2.setVisible(false);
        if (getMoney()<30) outOfMoney3.setVisible(true); else  outOfMoney3.setVisible(false);


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

    public static void showIconWithMouse(MouseEvent event) {
        if (currentItem == 1) {
            if (!layout.getChildren().contains(placingTower1))
                layout.getChildren().add(placingTower1);
            placingTower1.setLocation((int) event.getSceneX(), (int) event.getSceneY());
        }
        else if (currentItem == 2) {
            if (!layout.getChildren().contains(placingTower2))
                layout.getChildren().add(placingTower2);
            placingTower2.setLocation((int) event.getSceneX(), (int) event.getSceneY());
        }
        else if (currentItem == 3) {
            if (!layout.getChildren().contains(placingTower3))
                layout.getChildren().add(placingTower3);
            placingTower3.setLocation((int) event.getSceneX(), (int) event.getSceneY());
        }
        else {
            layout.getChildren().removeAll(placingTower1, placingTower2, placingTower3);
        }

        if (selling) {
            if (!layout.getChildren().contains(using_shovel))
                layout.getChildren().add(using_shovel);
            using_shovel.setLocation((int) event.getSceneX() - 5, (int) event.getSceneY() - 82);
        } else {
            layout.getChildren().remove(using_shovel);
        }
    }

    public static int getCurrentItem() {
        return currentItem;
    }

    public static void cancelBuying() {
        if (!buying)
            return;

        // layout.getChildren().removeAll(placingTower1, placingTower2, placingTower3);
        if (currentItem == 1)
            layout.getChildren().remove(placingTower1);
        else if (currentItem == 2)
            layout.getChildren().remove(placingTower2);
        else if (currentItem == 3)
            layout.getChildren().remove(placingTower3);

        buying = false;
        selectedItem.setVisible(false);
        currentItem = 0;
    }

    public static void cancelSelling() {
        if (!selling)
            return;

        selling = false;
        layout.getChildren().remove(using_shovel);
    }

    public static void sellTowerAt(int x, int y) {
        Tower tower = GameField.getTowerPlacedAt(x, y);
        if (tower != null) {
            increaseMoney((int)(tower.getPrice() * SELL_RATE));
            GameField.removeTower(tower);
            tower.destroy();

            removeSound();
            cancelSelling();
        }
    }

    public static void buyTowerAt(int x, int y) {
        Tower tower;
        if (getCurrentItem() == 1)  // tower_type.equals("normal"))
            tower = new NormalTower();
        else if (getCurrentItem() == 2)  // tower_type.equals("sniper"))
            tower = new SniperTower();
        else if (getCurrentItem() == 3)  // tower_type.equals("machinegun"))
            tower = new MachineGunTower();
        else
            return; // mua thất bại

        int price = tower.getPrice();
        if (getMoney() >= price) {
            x = x - x % TILE_WIDTH;
            y = y - y % TILE_WIDTH;

            decreaseMoney(price);
            placeTowerAt(tower, x, y);
            cancelBuying();
        }
    }

    private static void placeTowerAt(Tower tower, int x, int y) {
        imageObject building = new imageObject("file:images/white_building.gif");
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.millis(0), event -> {
                buildingSound();
                building.scaleTo(TILE_WIDTH, TILE_WIDTH);
                building.setLocation(x+TILE_WIDTH/2, y+TILE_WIDTH/2);
                layout.getChildren().add(building);

                tower.setPosition(new Point(x, y));
            }), new KeyFrame(Duration.millis(1800), event -> {
                layout.getChildren().remove(building);
                tower.show();
                // tower.showRange();
                GameField.addTower(tower);
            })
        );
        timeline.play();
    }
}
