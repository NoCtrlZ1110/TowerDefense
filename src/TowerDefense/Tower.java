package TowerDefense;

import static TowerDefense.GameField.layout;

public class Tower extends GameEntity {
    Enemy target = null;
    public Tower(String path) {
        super(path);
    }

    public void showTower(Point location) {
        this.setLocation(location.getX(), location.getY());
        if (!layout.getChildren().contains(this)) layout.getChildren().add(this);
    }
    //public Enemy findTarget()

}
