package TowerDefense;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import static TowerDefense.CONSTANT.*;
import static TowerDefense.GameField.*;
import static TowerDefense.GameTile.map;
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
        for (Tower t: towers) {
            if (t.isInTower(x, y)) {
                return t;
            }
        }
        return null;
    }

    public void buyTower(String tower_type) {
        Tower tower;
        if (tower_type.equals("normal"))
            tower = new NormalTower();
        else if (tower_type.equals("sniper"))
            tower = new SniperTower();
        else {
            // Tower tower = new Tower("file:images/Tower.png");
            tower = new Tower("file:images/Archer_Tower17.png");
        }

        if (money >= tower.getPrice()) {
            money -= tower.getPrice();
            System.out.println("new money = " + money);
            placeTower(tower);
        }
    }

    public void sellPlacedTower() {
        Tower tower = getPlacedTower();
        if (tower != null) {
            money += (int)(tower.getPrice() * SELL_RATE);
            System.out.println("new money = " + money);
            towers.remove(tower);
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
                layout.getChildren().add(building);

                tower.setPosition(new Point(x, y));
            }), new KeyFrame(Duration.millis(1800), event -> {
                layout.getChildren().remove(building);
                tower.showTower();
                // tower.showRange();
                towers.add(tower);
            })
        );
        timeline.play();
    }

    public void upgradePlacedTower() {
        Tower tower = getPlacedTower();
        if (tower != null) {
            // money -= ...;
            System.out.println("I'm waiting for you...");
        }
    }
}
