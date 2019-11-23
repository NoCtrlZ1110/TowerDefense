package TowerDefense;

import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import static TowerDefense.GameField.layout;

public class Enemy extends GameEntity {
    private double speed;
    private double hp = 100;
    private double defense_point = 0;
    private int killed_bonus = 10;
    private int harm_point = 1;
    private double hp_max;
    private Rectangle hp_bar = new Rectangle();
    private Rectangle hp_max_bar = new Rectangle();
    private PathTransition pathTransition = new PathTransition();
    private boolean is_destroyed = false;

    public Enemy(String imageUrl) {
        super(imageUrl);
    }

    public Enemy(int x, int y, String imageUrl) {
        super(imageUrl);
        setLocation(x, y);

        layout.getChildren().add(hp_max_bar);
        layout.getChildren().add(hp_bar);
        hp_max_bar.setWidth(60);
        hp_max_bar.setHeight(5);
        hp_max_bar.setFill(Color.ALICEBLUE);

        hp_bar.setWidth(60);
        hp_bar.setHeight(5);
        hp_bar.setFill(Color.DARKRED);
    }

    protected Enemy(int x, int y, String imageUrl, double speed, double hp_max, double defense_point, int killed_bonus, int harm_point) {
        super(imageUrl);
        setLocation(x, y);
        this.speed = speed;
        this.hp_max = hp_max;
        this.hp = hp_max;
        this.defense_point = defense_point;
        this.killed_bonus = killed_bonus;
        this.harm_point = harm_point;

        layout.getChildren().add(hp_max_bar);
        layout.getChildren().add(hp_bar);
        hp_max_bar.setWidth(60);
        hp_max_bar.setHeight(5);
        hp_max_bar.setFill(Color.ALICEBLUE);

        hp_bar.setWidth(60);
        hp_bar.setHeight(5);
        hp_bar.setFill(Color.DARKRED);
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public boolean isAlive() {
        return (hp > 0 && !is_destroyed);
    }

    public boolean isDead() {
        return (hp <= 0);
    }

    public int getKilledBonus() {
        return (isDead() ? killed_bonus : 0);
    }

    public void showHP() {
        if (is_destroyed)
            return;

        hp_max_bar.setX(this.getTranslateX());
        hp_max_bar.setY(this.getTranslateY()-10);

        hp_bar.setX(this.getTranslateX());
        hp_bar.setY(this.getTranslateY()-10);
        hp_bar.setWidth(60 * (this.hp / this.hp_max));
        hp_bar.setHeight(5);
        hp_bar.setFill(Color.DARKRED);
    }

    private void deleteHPbar() {
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

    public void beShotBy(Bullet b) {
        decreaseHP(b.getDamage());
        showHP();
        if (isDead()) {
            destroy();
        }
    }

    private void decreaseHP(double amount) {
        hp -= Math.max(amount - defense_point, 0);
    }

    public boolean isReachedEndPoint() {
        Point last_point = GameTile.getEndPointOfRoad();
        int enemy_width = 70;
        return (GetX() + (enemy_width>>1) == last_point.getX() && GetY() + (enemy_width>>1) == last_point.getY());
    }

    public boolean harm() {
        if (!is_destroyed && isReachedEndPoint()) {
            destroy(); // tránh "gây hại" nhiều lần
            GameField.decreaseHP(harm_point * 1.0);
            // note: có thể nhân với hệ số < 1

            System.out.println("ouch!");
            return true;
        }
        return false;
    }

    private void destroy() {
        deleteHPbar();
        layout.getChildren().remove(this);
        this.is_destroyed = true;
    }
}
