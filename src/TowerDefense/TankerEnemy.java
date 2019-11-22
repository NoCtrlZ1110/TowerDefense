package TowerDefense;

import static TowerDefense.CONSTANT.pathTankerEnemy;

public class TankerEnemy extends Enemy {
    public TankerEnemy(int x, int y) {
        super(x, y, pathTankerEnemy, 0.8, 200, 0.5, 10, 2);
        scaleTo(70, 70);
    }
}
