package TowerDefense;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import static TowerDefense.CONSTANT.*;
import static TowerDefense.GameField.*;
import static TowerDefense.Sound.buildingSound;

public class Roadside {
    private int x;
    private int y;

    public Roadside(int x, int y) {
        this.x = x - x % TILE_WIDTH;
        this.y = y - y % TILE_WIDTH;
    }

    /*
    public boolean isTowerPlaced() {
        return map.isTowerPlacedAt(x, y);
    }
    */

    public Tower getPlacedTower() {
        for (Tower t: GameField.getTowers()) {
            if (t.isInTower(x, y)) {
                return t;
            }
        }
        return null;
    }

    public void buyTower() {
        Tower tower;
        if (Shop.getCurrentItem() == 1)  // tower_type.equals("normal"))
            tower = new NormalTower();
        else if (Shop.getCurrentItem() == 2)  // tower_type.equals("sniper"))
            tower = new SniperTower();
        else if (Shop.getCurrentItem() == 3)  // tower_type.equals("machinegun"))
            tower = new MachineGunTower();
        else
            return; // mua thất bại

        if (getMoney() >= tower.getPrice()) {
            decreaseMoney(tower.getPrice());
            placeTower(tower);
        }
    }

    public void sellPlacedTower() {
        Tower tower = getPlacedTower();
        if (tower != null) {
            increaseMoney((int)(tower.getPrice() * SELL_RATE));
            GameField.removeTower(tower);
            tower.destroy();
        }
    }

    private void placeTower(Tower tower) {
        imageObject building = new imageObject("file:images/white_building.gif");
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.millis(0), event -> {
                buildingSound();
                building.scaleTo(TILE_WIDTH, TILE_WIDTH);
                building.setLocation(x+TILE_WIDTH/2, y+TILE_WIDTH/2);
                GameField.layout.getChildren().add(building);

                tower.setPosition(new Point(x, y));
            }), new KeyFrame(Duration.millis(1800), event -> {
                GameField.layout.getChildren().remove(building);
                tower.showTower();
                // tower.showRange();
                GameField.addTower(tower);
            })
        );
        timeline.play();
    }

    public void upgradePlacedTower() {
        Tower tower = getPlacedTower();
        if (tower != null) {
            // decreaseMoney(...);
            System.out.println("waiting for updating...");
        }
    }
}
