package TowerDefense;

import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import static TowerDefense.GameField.*;

public abstract class Enemy extends GameCharacter {
    private double speed;
    private double defense_point = 0;
    private int killed_bonus = 10;
    private int harm_point = 1;
    private PathTransition pathTransition = new PathTransition();

    public Enemy(int x, int y, String imageUrl) {
        super(imageUrl);
        setLocation(x, y);
        setHpBarXY(x, y-10);
    }

    protected Enemy(int x, int y, String imageUrl, double speed, double hp_max, double defense_point, int killed_bonus, int harm_point) {
        super(imageUrl, hp_max, 60, 5);
        setHpBarXY(x, y-10);
        setLocation(x, y);

        this.speed = speed;
        this.defense_point = defense_point;
        this.killed_bonus = killed_bonus;
        this.harm_point = harm_point;
    }

    public boolean exists() {
        return (hp > 0 && !is_destroyed);
    }

    public int getKilledBonus() {
        return (isDead() ? killed_bonus : 0);
    }

    public void decreaseHP(double amount) {
        hp -= Math.max(amount - defense_point, 0);
    }

    public void displayHpBar() {
        setHpBarXY(this.getTranslateX(), this.getTranslateY()-10);
        super.displayHpBar();
    }

    // [Hàm di chuyển theo path được truyền vào]
    public void move(Path path) {
        //Điều chỉnh gia tốc lúc xuất phát và kết thúc.
        pathTransition.setInterpolator(Interpolator.LINEAR);
        //Setting the duration of the path transition
        pathTransition.setDuration(Duration.millis(1000 * 51 / speed));

        //Setting the node for the transition
        pathTransition.setNode(this);

        //Setting the path
        pathTransition.setPath(path);

        //Setting the orientation of the path
        //pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);

        //Setting auto reverse value to false
        pathTransition.setAutoReverse(false);

        //Playing the animation
        pathTransition.play();
    }

    public void pauseMoving() {
        pathTransition.pause();
    }

    public void resumeMoving() {
        pathTransition.play();
    }

    public void beShotBy(Bullet b) {
        if (exists()) { // trường hợp enemy ra khỏi map lúc chưa chết (chỉ bị destroy)
            decreaseHP(b.getDamage());
            displayHpBar();
            if (isDead()) {
                increaseMoney(getKilledBonus());
                // cho increaseMoney ra ngoài Tower.shoot thì không tăng tiền,
                // còn cho ra ngoài Bullet.beShot thì tăng tiền nhiều lần @@
                destroy();
            }
        }
    }

    public boolean isReachedEndPoint() {
        Point last_point = GameTile.getEndPointOfRoad();
        int enemy_width = 70;
        double _x = last_point.getX() - GetX();
        double _y = last_point.getY() - GetY();
        return (0 <= _x && _x <= (enemy_width >> 1) && 0 <= _y && _y <= (enemy_width >> 1));
    }

    public boolean harm() {
        if (!is_destroyed && isReachedEndPoint()) {
            destroy(); // tránh "gây hại" nhiều lần
            GameField.decreaseUserHP(harm_point * (hp / hp_max));
            // System.out.println("ouch!");
            return true;
        }
        return false;
    }

    public void destroy() {
        GameField.removeEnemy(this);
        super.destroy();
    }

    public String toString() {
        return String.format(
            "Enemy[x=%d,y=%d,speed=%f,defense_point=%f,killed_bonus=%d,hp=%f,hp_max=%f",
            getLocation().getX(), getLocation().getY(), speed, defense_point, killed_bonus, hp, hp_max
        );
    }

    public static Enemy generateEnemyByType(String enemy_type, int x, int y) {
        switch (enemy_type.toLowerCase()) {
            case "normal":
                return new NormalEnemy(x, y);
            case "smaller":
                return new SmallerEnemy(x, y);
            case "tanker":
                return new TankerEnemy(x, y);
            case "boss":
                return new BossEnemy(x, y);
            default:
                return null;
        }
    }
}
