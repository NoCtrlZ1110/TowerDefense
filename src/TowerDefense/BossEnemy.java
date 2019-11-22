package TowerDefense;

import static TowerDefense.CONSTANT.pathBossEnemy;

public class BossEnemy extends Enemy {
    public BossEnemy(int x, int y) {
        super(x, y, pathBossEnemy, 0.5, 500, 5, 1000, 1000);
        scaleTo(70, 70);
    }
}
