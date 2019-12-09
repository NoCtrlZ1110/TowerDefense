package TowerDefense;

import static TowerDefense.CONSTANT.pathSniperTower;

public class SniperTower extends Tower {
    public SniperTower() {
        super(pathSniperTower, 225, 0.4, 1.1, 20);
    }

    public String toString() {
        return String.format("SniperTower[x=%d,y=%d]", getPosition().getX(), getPosition().getY());
    }
}
