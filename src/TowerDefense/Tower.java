package TowerDefense;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import static TowerDefense.CONSTANT.TILE_WIDTH;
import static TowerDefense.GameField.*;
import static TowerDefense.GameTile.resetMap;
import static TowerDefense.GameTile.setMapType;

public class Tower extends GameEntity {
    int price = 10;
    Point position;
    int range = 200;
    Circle rangeCircle = new Circle();
    private Line line = new Line();
    private boolean is_destroyed = false;

    public Tower(String imageUrl) {
        super(imageUrl);
    }

    public int getPrice() {
        return price;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public void showRange() {
        if (is_destroyed)
            return;

        rangeCircle.setRadius(range);
        rangeCircle.setLayoutX(this.getTranslateX() + TILE_WIDTH);
        rangeCircle.setLayoutY(this.getTranslateY() + TILE_WIDTH);
        rangeCircle.setFill(Color.TRANSPARENT);
        //rangeCircle.setOpacity(0.6);
        rangeCircle.setStrokeWidth(3);
        rangeCircle.setStroke(Color.CADETBLUE);

        if (!layout.getChildren().contains(rangeCircle))
            layout.getChildren().add(rangeCircle);
    }

    public void removeRange() {
        if (layout.getChildren().contains(rangeCircle))
            layout.getChildren().remove(rangeCircle);
    }

    public void placeAt(Point location) {
        this.position = location;
        this.setLocation(location.getX(), location.getY());
        this.is_destroyed = false;

        // getMapType(location.getX() / TILE_WIDTH, location.getY() / TILE_WIDTH)); -> 2
        // getMapType(location.getX() / TILE_WIDTH, location.getY() / TILE_WIDTH + 1)); -> 5
        // getMapType(location.getX() / TILE_WIDTH + 1, location.getY() / TILE_WIDTH)); -> 4
        // getMapType(location.getX() / TILE_WIDTH + 1, location.getY() / TILE_WIDTH + 1)); -> 3

        setMapType(location.getX() / TILE_WIDTH, location.getY() / TILE_WIDTH, 6);
        setMapType(location.getX() / TILE_WIDTH, location.getY() / TILE_WIDTH + 1, 6);
        setMapType(location.getX() / TILE_WIDTH + 1, location.getY() / TILE_WIDTH, 6);
        setMapType(location.getX() / TILE_WIDTH + 1, location.getY() / TILE_WIDTH + 1, 6);
    }

    public void showTower() {
        if (this.is_destroyed) {
            layout.getChildren().remove(this);
            return;
        }
        if (!layout.getChildren().contains(this))
            layout.getChildren().add(this);
    }

    public void destroy() {
        // khôi phục trạng thái cũ trước khi đặt tháp
        resetMap(this.position.getX() / TILE_WIDTH, this.position.getY() / TILE_WIDTH);
        removeRange();
        layout.getChildren().remove(line);
        layout.getChildren().remove(this);
        this.is_destroyed = true;
    }

    public Enemy findTarget() {
        for (Enemy enemy: enemies) {
            int tower_width = 2*TILE_WIDTH;
            Point e = new Point(enemy.getLocation().getX()+TILE_WIDTH/2,enemy.getLocation().getY()+TILE_WIDTH/2);
            Point t = new Point(getPosition().getX()+tower_width/2,getPosition().getY()+tower_width/2);
            if (t.getDistance(e) <= range) {
                line.setStartX(t.getX());
                line.setEndX(e.getX());
                line.setStartY(t.getY());
                line.setEndY(e.getY());
                if (!layout.getChildren().contains(line))
                    layout.getChildren().add(line);

                return enemy;
            }
        }
        layout.getChildren().remove(line);
        return null;
    }

    public void shoot() {
        if (is_destroyed)
            return;

        Enemy target = findTarget();
        if (target != null) {
            Bullet b = new Bullet(1, 1, position.getX(), position.getY(),
                    (int) target.getTranslateX() + 40, (int) target.getTranslateY() + 40
            );
            // b.move();
            target.beShotBy(b);
            if (target.is_dead()) {
                money += target.getKilledBonus();
                System.out.println("new money = " + money);
                enemies.remove(target);
            }
        }
    }

    public boolean isXYInTower(int x, int y) {
        return (0 <= x - position.getX() && x - position.getX() <= 2*TILE_WIDTH &&
                0 <= y - position.getY() && y - position.getY() <= 2*TILE_WIDTH);
    }
}
