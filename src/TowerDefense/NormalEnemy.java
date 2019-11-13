package TowerDefense;

import static TowerDefense.GameField.layout;

public class NormalEnemy extends Enemy {
    protected double hp = 100;
    protected int killed_bonus = 5;

    public NormalEnemy(String path) {
        super(path);
    }

    public NormalEnemy(int x, int y, String path) {
        super(x, y, path);
    }
}
