package TowerDefense;

import javafx.animation.*;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

import static TowerDefense.CONSTANT.*;
import static TowerDefense.GameField.*;

public class Bullet extends GameEntity {
    private static final double MAX_TIME = 1000;

    private double speed;
    private double damage;
    private Tower source;
    private Enemy target;

    private Path path;

    public Bullet(Tower source, Enemy target) {
        super(pathBullet);
        this.source = source;
        this.target = target;
        this.speed = source.getShootingSpeed();
        this.damage = source.getShootingDamage();

        createPath();
        layout.getChildren().add(this);
    }

    private void createPath() {
        double start_x = source.getPosition().getX() + TOWER_WIDTH/2;
        double start_y = source.getPosition().getY() + TOWER_WIDTH/2;
        double dest_x = target.getLocation().getX() + TILE_WIDTH*0.25;
        double dest_y = target.getLocation().getY();// - TILE_WIDTH*0.75;

        setLocation(start_x, start_y);
        setVisible(false);

        path = new Path();
        path.getElements().add(new MoveTo(start_x, start_y));
        path.getElements().add(new LineTo(dest_x, dest_y));
    }

    public double getDamage() {
        return damage;
    }

    private void move() {
        //Creating a path transition
        PathTransition pathTransition = new PathTransition();

        //Điều chỉnh gia tốc lúc xuất phát và kết thúc.
        pathTransition.setInterpolator(Interpolator.LINEAR);
        //Setting the duration of the path transition
        pathTransition.setDuration(Duration.millis(MAX_TIME / speed));

        //Setting the node for the transition
        pathTransition.setNode(this);

        //Setting the path
        pathTransition.setPath(path);

        //Setting the orientation of the path
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);

        //Setting auto reverse value to false
        pathTransition.setAutoReverse(false);

        //Playing the animation
        pathTransition.play();
    }

    public void beShot() {
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.millis(0), event -> {
                // BUG: di chuyển vẫn bị để lại vết đạn
                move();
            }),
            new KeyFrame(Duration.millis(100), event -> {
                if (target != null)
                    target.beShotBy(this);

                destroy();
            })
        );
        timeline.play();
    }
}
