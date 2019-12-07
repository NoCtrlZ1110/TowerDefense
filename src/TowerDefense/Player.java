package TowerDefense;

public class Player extends GameCharacter {
    // 500,45
    private static int HPBAR_X = 1030; // 500;
    private static int HPBAR_Y = 760; // 45;
    private static final String pathHpBar = "file:images/HPBar2.png";
    // "file:images/HPBar.png";
    public Player(double hp, double hp_max) {
        super(pathHpBar, hp, hp_max, 164, 13);
        setHpBarXY(HPBAR_X+30, HPBAR_Y+10);
        setLocation(HPBAR_X, HPBAR_Y);
    }
}
