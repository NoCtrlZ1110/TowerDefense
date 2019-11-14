package TowerDefense;

public class Bullet {
    private int range;
    private int speed;
    private int damage;
    private int start_x;
    private int start_y;
    private int dest_x;
    private int dest_y;

    public Bullet(int range, int speed, int damage, int start_x, int start_y, int dest_x, int dest_y) {
        // super();
        this.range = range;
        this.speed = speed;
        this.damage = damage;
        this.start_x = start_x;
        this.start_y = start_y;
        this.dest_x = dest_x;
        this.dest_y = dest_y;
    }

    public int getDamage() {
        return damage;
    }

    public void move() {

    }
}
