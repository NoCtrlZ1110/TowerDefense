package TowerDefense;

import static TowerDefense.CONSTANT.pathMachineGunTower;

public class MachineGunTower extends Tower {
    public MachineGunTower() {
        super(pathMachineGunTower, 150, 0.5, 1, 30);
    }

    public String toString() {
        return String.format("MachineGunTower[x=%d,y=%d]", getPosition().getX(), getPosition().getY());
    }
}
