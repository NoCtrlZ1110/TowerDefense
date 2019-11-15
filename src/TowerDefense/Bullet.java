package TowerDefense;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import static TowerDefense.CONSTANT.TILE_WIDTH;
import static TowerDefense.CONSTANT.pathBullet;
import static TowerDefense.GameField.layout;
import static TowerDefense.Sound.buildingSound;

public class Bullet extends GameEntity {
    private int speed;
    private int damage;
    private int start_x;
    private int start_y;
    private int dest_x;
    private int dest_y;

    private Path path;

    public Bullet(int speed, int damage, int start_x, int start_y, int dest_x, int dest_y) {
        super(pathBullet);
        this.speed = speed;
        this.damage = damage;
        this.start_x = start_x;
        this.start_y = start_y;
        this.dest_x = dest_x;
        this.dest_y = dest_y;

        createPath();

        setLocation(start_x, start_y);
        double angle = Math.atan((start_y - dest_y) / (start_x - dest_x - 1e-9));
        Rotate rotate = new Rotate(angle, start_x, start_y);
        this.getTransforms().add(rotate);
        layout.getChildren().add(this);
    }

    public int getDamage() {
        return damage;
    }

    private void createPath() {
        path = new Path();
        path.getElements().add(new MoveTo(start_x, start_y));
        path.getElements().add(new LineTo(dest_x, dest_y));
    }

    public void move() {
        //Creating a path transition
        PathTransition pathTransition = new PathTransition();

        //Điều chỉnh gia tốc lúc xuất phát và kết thúc.
        pathTransition.setInterpolator(Interpolator.LINEAR);
        //Setting the duration of the path transition
        pathTransition.setDuration(Duration.millis(500 / speed));

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

    public void beShot() {
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.millis(0), event -> {
                move();
            }), new KeyFrame(Duration.millis(500), event -> {
                disappear();
            })
        );
        timeline.play();
    }

    public void disappear() {
        layout.getChildren().remove(this);
    }
}
