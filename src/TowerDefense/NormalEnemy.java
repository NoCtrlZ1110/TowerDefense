package TowerDefense;

import static TowerDefense.CONSTANT.*;
import static TowerDefense.GameField.layout;

public class NormalEnemy extends Enemy {
    protected double hp = 100;
    protected final double defense_point = 0;
    protected int killed_bonus = 5;

    public NormalEnemy(int x, int y) {
        super(x, y, pathRedEnemy);
    }
}
