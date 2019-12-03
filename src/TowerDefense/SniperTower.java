package TowerDefense;

import static TowerDefense.CONSTANT.pathSniperTower;

public class SniperTower extends Tower {
    public SniperTower() {
        super(pathSniperTower, 250, 0.45, 2, 20);
    }

    public String toString() {
        return String.format("SniperTower[x=%d,y=%d]", getPosition().getX(), getPosition().getY());
    }
}
