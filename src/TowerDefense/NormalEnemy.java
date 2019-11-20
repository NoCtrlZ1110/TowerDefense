package TowerDefense;

import static TowerDefense.CONSTANT.*;
import static TowerDefense.GameField.layout;

public class NormalEnemy extends Enemy {
    public NormalEnemy(int x, int y) {
        super(x, y, pathRedEnemy);

        // setFitHeight(70);
        // setFitWidth(70);
        // speed = 1.2;
        speed = 1.2;
        hp = 100;
        defense_point = 0;
        killed_bonus = 5;
        harm_point = 1;
    }
}
