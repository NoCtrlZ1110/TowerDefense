package TowerDefense;

public class SniperTower extends Tower {
    public SniperTower() {
        super("file:images/bean.png");
        price = 15;
        range = 200;
        shooting_speed = 0.5;
        shooting_damage = 10;
    }
}
