package TowerDefense;

public class NormalTower extends Tower {
    public NormalTower() {
        super("file:images/Archer_Tower17.png");
        price = 10;
        range = 50;
        shooting_speed = 1;
        shooting_damage = 1;
    }
}
