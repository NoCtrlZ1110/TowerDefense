package TowerDefense;

import static TowerDefense.CONSTANT.pathNormalTower;

public class NormalTower extends Tower {
    public NormalTower() {
        super(pathNormalTower, 200, 0.75, 1, 10);
    }

    public String toString() {
        return String.format("NormalTower[x=%d,y=%d]", getPosition().getX(), getPosition().getY());
    }
}
