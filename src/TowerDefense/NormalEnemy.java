package TowerDefense;

import static TowerDefense.CONSTANT.pathRedEnemy;

public class NormalEnemy extends Enemy {
    public NormalEnemy(int x, int y) {
        super(x, y, pathRedEnemy, 1.2, 100, 0, 5, 1);
        scaleTo(70, 70);
    }

    public String toString() {
        return String.format("NormalEnemy[x=%d,y=%d,hp=%f]", getLocation().getX(), getLocation().getY(), hp);
    }
}
