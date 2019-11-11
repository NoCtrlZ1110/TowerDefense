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

    public Enemy findTarget() {
        for (int i = 0; i < enemies.size(); i++) {
            Point e = new Point(enemies.get(i).getLocation().getX()+40,enemies.get(i).getLocation().getY()+40);
            Point t = new Point(getPosition().getX()+80,getPosition().getY()+80);
            if (t.getDistance(e) <= range)
            {
                line.setStartX(t.getX());
                line.setEndX(e.getX());
                line.setStartY(t.getY());
                line.setEndY(e.getY());
                if (!layout.getChildren().contains(line)) layout.getChildren().add(line);
                target = enemies.get(i);
                return enemies.get(i);
            }

        }
        layout.getChildren().remove(line);
        target = null;
        return null;

    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public Tower(String path) {
        super(path);
    }

    public void showTower(Point location) {
        this.position = location;
        this.setLocation(location.getX(), location.getY());
        if (!layout.getChildren().contains(this)) layout.getChildren().add(this);
    }
    //public Enemy findTarget()

}
