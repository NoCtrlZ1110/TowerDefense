package TowerDefense;

public class Player extends GameCharacter {
    // 500,45
    private static int HPBAR_X = 860; // 1030; // 500;
    private static int HPBAR_Y = 758; // 760; // 45;
    private static final String pathHpBar = "file:images/HPBar.png";
    // "file:images/HPBar.png";
    public Player(double hp, double hp_max) {
        super(pathHpBar, hp, hp_max, 152, 13);
        setHpBarXY(HPBAR_X+30, HPBAR_Y+10);
        setLocation(HPBAR_X, HPBAR_Y);
    }
}
