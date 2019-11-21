package TowerDefense;

import static TowerDefense.CONSTANT.pathRedEnemy;

public class NormalEnemy extends Enemy {
    public NormalEnemy(int x, int y) {
        super(x, y, pathRedEnemy, 1.2, 100, 0, 5, 1);
        scaleTo(70, 70);
    }
}
