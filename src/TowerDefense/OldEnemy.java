/*
package TowerDefense;

import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import static TowerDefense.GameField.*;
public abstract class Enemy extends GameEntity {
    private double speed;
    private double hp = 100;
    private double defense_point = 0;
    private int killed_bonus = 10;
    private int harm_point = 1;
    private double hp_max;
    private Rectangle hp_bar = new Rectangle();
    private Rectangle hp_max_bar = new Rectangle();
    private PathTransition pathTransition = new PathTransition();

    public Enemy(String imageUrl) {
        super(imageUrl);
        initHpBar();
    }

    public Enemy(int x, int y, String imageUrl) {
        super(imageUrl);
        setLocation(x, y);
        initHpBar();
    }

    protected Enemy(int x, int y, String imageUrl, double speed, double hp_max, double defense_point, int killed_bonus, int harm_point) {
        super(imageUrl);
        setLocation(x, y);
        initHpBar();

        this.speed = speed;
        this.hp_max = hp_max;
        this.hp = hp_max;
        this.defense_point = defense_point;
        this.killed_bonus = killed_bonus;
        this.harm_point = harm_point;
    }

    public boolean exists() {
        return (hp > 0 && !is_destroyed);
    }

    public boolean isDead() {
        return (hp <= 0);
    }

    public int getKilledBonus() {
        return (isDead() ? killed_bonus : 0);
    }

    private void initHpBar() {
        layout.getChildren().add(hp_max_bar);
        layout.getChildren().add(hp_bar);
        hp_max_bar.setWidth(60);
        hp_max_bar.setHeight(5);
        hp_max_bar.setFill(Color.ALICEBLUE);

        hp_bar.setWidth(60);
        hp_bar.setHeight(5);
        hp_bar.setFill(Color.DARKRED);
    }

    public void displayHpBar() {
        if (is_destroyed)
            return;

        hp_max_bar.setX(this.getTranslateX());
        hp_max_bar.setY(this.getTranslateY()-10);

        hp_bar.setX(this.getTranslateX());
        hp_bar.setY(this.getTranslateY()-10);
        hp_bar.setWidth(hp_max_bar.getWidth() * (this.hp / this.hp_max));
        hp_bar.setFill(Color.DARKRED);
    }

    private void deleteHpBar() {
        // hp_bar.setVisible(false);
        layout.getChildren().remove(hp_bar);
        hp_bar = null;
        // hp_max_bar.setVisible(false);
        layout.getChildren().remove(hp_max_bar);
        hp_max_bar = null;
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
                enemies.remove(this);
            }
        }
    }

    private void decreaseHP(double amount) {
        hp -= Math.max(amount - defense_point, 0);
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

            System.out.println("ouch!");
            return true;
        }
        return false;
    }

    public void destroy() {
        deleteHpBar();
        super.destroy();
    }

    public String toString() {
        return String.format(
                "Enemy[speed=%f,defense_point=%f,killed_bonus=%d,hp=%f,hp_max=%f,x=%d,y=%d",
                speed, defense_point, killed_bonus, hp, hp_max, getLocation().getX(), getLocation().getY()
        );
    }
}
*/