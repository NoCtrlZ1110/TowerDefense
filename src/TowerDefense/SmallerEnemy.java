package TowerDefense;

import static TowerDefense.CONSTANT.pathSmallerEnemy;

public class SmallerEnemy extends Enemy {
    public SmallerEnemy(int x, int y) {
        super(x, y, pathSmallerEnemy, 1.7, 50, 0, 3, 1);
        scaleTo(70, 70);
    }

    public String toString() {
        return String.format("SmallerEnemy[x=%d,y=%d,hp=%f]", getLocation().getX(), getLocation().getY(), hp);
    }
}
