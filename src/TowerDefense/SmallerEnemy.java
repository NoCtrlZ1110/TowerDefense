package TowerDefense;

import static TowerDefense.CONSTANT.pathSmallerEnemy;

public class SmallerEnemy extends Enemy {
    public SmallerEnemy(int x, int y) {
        super(x, y, pathSmallerEnemy, 1.7, 50, 0, 5, 1);
        scaleTo(70, 70);
    }
}
