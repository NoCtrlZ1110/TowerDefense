package TowerDefense;

import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import static TowerDefense.GameField.layout;

public class Enemy extends GameEntity {
    protected double speed;
    protected double hp = 100;
    protected double defense_point = 0;
    protected int killed_bonus = 10;
    protected int harm_point = 1;
    protected Rectangle hp_bar = new Rectangle();
    protected Rectangle hp_max = new Rectangle();
    private PathTransition pathTransition = new PathTransition();
    private boolean is_destroyed = false;

    public Enemy(String imageUrl) {
        super(imageUrl);
    }

    public Enemy(int x, int y, String imageUrl) {
        super(imageUrl);
        setLocation(x, y);
        layout.getChildren().add(hp_max);
        layout.getChildren().add(hp_bar);
        hp_max.setWidth(60);
        hp_max.setHeight(5);
        hp_max.setFill(Color.ALICEBLUE);

        hp_bar.setHeight(5);
        hp_bar.setFill(Color.DARKRED);
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public boolean isDead() {
        return (hp <= 0);
    }

    public int getKilledBonus() {
        return (isDead() ? killed_bonus : 0);
    }

    public void showHP() {
        hp_max.setX(this.getTranslateX());
        hp_max.setY(this.getTranslateY()-10);

        hp_bar.setX(this.getTranslateX());
        hp_bar.setY(this.getTranslateY()-10);
        hp_bar.setWidth(this.hp / 10*6);
        hp_bar.setHeight(5);
        hp_bar.setFill(Color.DARKRED);
    }

    public void deleteHPbar() {
        hp_bar.setVisible(false);
        hp_bar = null;
        hp_max.setVisible(false);
        hp_max = null;
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
            deleteHPbar();
            disappear();
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
            is_destroyed = true; // tránh "gây hại" nhiều lần
            GameField.decreaseHP(harm_point * 1.0);
            // note: có thể nhân với hệ số < 1

            System.out.println("ouch!");
            return true;
        }
        return false;
    }

    private void disappear() {
        layout.getChildren().remove(this);
    }
}
