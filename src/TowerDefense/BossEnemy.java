package TowerDefense;

import static TowerDefense.CONSTANT.pathBossEnemy;

public class BossEnemy extends Enemy {
    public BossEnemy(int x, int y) {
        super(x, y, pathBossEnemy, 0.5, 400, 1.6, 1000, 1000);
        scaleTo(70, 70);
    }

    public String toString() {
        return String.format("BossEnemy[x=%d,y=%d,hp=%f]", getLocation().getX(), getLocation().getY(), hp);
    }
}
