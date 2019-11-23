package TowerDefense;

import javafx.animation.*;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.sql.Time;

import static TowerDefense.CONSTANT.*;
import static TowerDefense.GameField.layout;

public class Bullet extends GameEntity {
    private static final double MAX_TIME = 150;

    private double speed;
    private double damage;
    private int start_x;
    private int start_y;
    private int dest_x;
    private int dest_y;

    private Enemy target;

    private Path path;

    public Bullet(double speed, double damage, Tower source, Enemy target) {
        super(pathBullet);
        this.speed = speed;
        this.damage = damage;
        this.start_x = source.getPosition().getX() + TOWER_WIDTH/2;
        this.start_y = source.getPosition().getY() + TOWER_WIDTH/2;
        this.dest_x = target.getLocation().getX() + TILE_WIDTH/2 + 20;
        this.dest_y = target.getLocation().getY() + TILE_WIDTH/2 ;

        this.target = target;

        createPath();
        rotate();
    }

    public double getDamage() {
        return damage;
    }

    private void createPath() {
        path = new Path();
        path.getElements().add(new MoveTo(start_x, start_y));
        path.getElements().add(new LineTo(dest_x, dest_y));
    }

    private void rotate() {
//        setLocation(start_x, start_y - (int)(getFitHeight() / 2));
//        double angle = (start_x > dest_x ? Math.PI : 0) + Math.atan((start_y - dest_y) / (start_x - dest_x - 1e-9));
//        this.getTransforms().add(new Rotate(Math.toDegrees(angle)));
        layout.getChildren().add(this);
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
        if (target != null)
            target.beShotBy(this);

        Timeline timeline = new Timeline(
            new KeyFrame(Duration.millis(0), event -> {
                // BUG: hiện di chuyển bị để lại vết đạn
                move();
            }),
            new KeyFrame(Duration.millis(MAX_TIME), event -> disappear())
        );
        timeline.play();
    }

    public void disappear() {
        layout.getChildren().remove(this);
    }
}
