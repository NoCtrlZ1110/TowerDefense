package TowerDefense;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import static TowerDefense.GameField.layout;

public class Tower extends GameEntity {
    Enemy target = null;
    int range = 200;
    Circle rangeCircle = new Circle();
    Point position;

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public void showRange() {
        rangeCircle.setRadius(range);
        rangeCircle.setLayoutX(this.getTranslateX()+80);
        rangeCircle.setLayoutY(this.getTranslateY()+80);
        rangeCircle.setFill(Color.TRANSPARENT);
        //rangeCircle.setOpacity(0.6);
        rangeCircle.setStrokeWidth(3);
        rangeCircle.setStroke(Color.CADETBLUE);
        if (!layout.getChildren().contains(rangeCircle)) layout.getChildren().add(rangeCircle);

    }

    public Enemy getTarget() {
        return target;
    }

    public void setTarget(Enemy target) {
        this.target = target;
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
