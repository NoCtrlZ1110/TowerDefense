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
        rangeCircle.setLayoutX(this.getTranslateX() + 80);
        rangeCircle.setLayoutY(this.getTranslateY() + 80);
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
            Point e = new Point(enemy.getLocation().getX()+40,enemy.getLocation().getY()+40);
            Point t = new Point(getPosition().getX()+80,getPosition().getY()+80);
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
}
