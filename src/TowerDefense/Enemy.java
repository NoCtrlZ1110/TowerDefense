package TowerDefense;

import javafx.animation.PathTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import static TowerDefense.GameField.layout;

public class Enemy extends GameEntity {
    double speed;
    double HP = 100;
    Rectangle hp_bar = new Rectangle();
    public Enemy(String path) {
        super(path);
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public Enemy(int x, int y, String path) {
        super(path);
        setLocation(x,y);
        layout.getChildren().add(hp_bar);
    }

    // [Hàm hiển thị thanh máu] ---------

    public void showHP() {
        hp_bar.setX(this.getTranslateX());
        hp_bar.setY(this.getTranslateY()-10);
        hp_bar.setWidth(this.HP / 10 * 6);
        hp_bar.setHeight(5);
        hp_bar.setFill(Color.DARKRED);

        //-------------------------------------
    }

    // [Hàm di chuyển theo path được truyền vào]

    public void move(Path path) {

        //Creating a path transition
        PathTransition pathTransition = new PathTransition();

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
    //-----------------------------

}
