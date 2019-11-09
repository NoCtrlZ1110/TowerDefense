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
    public Enemy(String path) {
        super(path);
    }
    Rectangle rectangle = new Rectangle();

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public Enemy(int x, int y, String path) {
        super(path);
        setLocation(x,y);
        layout.getChildren().add(rectangle);
    }
    public void showHP()
    {
        rectangle.setX(this.getTranslateX());
        rectangle.setY(this.getTranslateY()-10);
        rectangle.setWidth(this.HP/10*6);
        rectangle.setHeight(5);
        rectangle.setFill(Color.DARKRED);


    }
    public void move(Path path) {
        //Creating a path transition
        PathTransition pathTransition = new PathTransition();

        //Setting the duration of the path transition
        pathTransition.setDuration(Duration.millis(1000 * 51 / speed));

        //Setting the node for the transition
        pathTransition.setNode(this);

        //Setting the path
        pathTransition.setPath(path);

//        //Setting the orientation of the path
//        pathTransition.setOrientation(PathTransition.OrientationType.
//                ORTHOGONAL_TO_TANGENT);

        //Setting auto reverse value to false
        pathTransition.setAutoReverse(false);


        //Playing the animation
        pathTransition.play();
    }


}
