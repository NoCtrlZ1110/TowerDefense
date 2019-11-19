package TowerDefense;

import static TowerDefense.CONSTANT.*;
import static TowerDefense.GameField.layout;

public class NormalEnemy extends Enemy {
    protected double speed;
    protected double hp = 100;
    protected final double defense_point = 0;
    protected int killed_bonus = 5;
    protected int harm_point = 1;

    public NormalEnemy(int x, int y) {
        super(x, y, pathRedEnemy);

        // setFitHeight(70);
        // setFitWidth(70);
        // speed = 1.2;
    }
}
