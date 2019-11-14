package TowerDefense;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import static TowerDefense.GameField.*;

public class Tower extends GameEntity {
    Enemy target = null;
    int range = 200;
    Circle rangeCircle = new Circle();
    Point position;
    Line line = new Line();

    public Tower(String path) {
        super(path);
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public void showRange() {
        rangeCircle.setRadius(range);
        rangeCircle.setLayoutX(this.getTranslateX() + TILE_WIDTH);
        rangeCircle.setLayoutY(this.getTranslateY() + TILE_WIDTH);
        rangeCircle.setFill(Color.TRANSPARENT);
        //rangeCircle.setOpacity(0.6);
        rangeCircle.setStrokeWidth(3);
        rangeCircle.setStroke(Color.CADETBLUE);
        if (!layout.getChildren().contains(rangeCircle)) layout.getChildren().add(rangeCircle);
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public void showTower(Point location) {
        this.position = location;
        this.setLocation(location.getX(), location.getY());
        if (!layout.getChildren().contains(this))
            layout.getChildren().add(this);
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
                target = enemy;
                return enemy;
            }
        }
        layout.getChildren().remove(line);
        target = null;
        return null;
    }

    public void shoot() {
        target = findTarget();
        if (target != null) {
            Bullet b = new Bullet(range, 1, 1, position.getX(), position.getY(),
                    (int) target.getTranslateX() + 40, (int) target.getTranslateY() + 40
            );
            // b.move();
            target.beShotBy(b);
            if (target.is_dead()) {
                money += target.getKilledBonus();
                System.out.println("new money = " + money);
                enemies.remove(target);
                target = null;
            }
        }
    }

    public boolean isXYInTower(int x, int y) {
        return (0 <= x - position.getX() && x - position.getX() <= 2*TILE_WIDTH &&
                0 <= y - position.getY() && y - position.getY() <= 2*TILE_WIDTH);
    }
}
