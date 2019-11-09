package TowerDefense;

import javafx.animation.PathTransition;
import javafx.scene.shape.Path;
import javafx.util.Duration;

public class Enemy extends GameEntity {
    double speed;
    public Enemy(String path) {
        super(path);
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public Enemy(int x, int y, String path) {
        super(path);
        setLocation(x,y);
    }
    public void move(Path path)
    {
        //Creating a path transition
        PathTransition pathTransition = new PathTransition();

        //Setting the duration of the path transition
        pathTransition.setDuration(Duration.millis(1000*51/speed));

        //Setting the node for the transition
        pathTransition.setNode(this);

        //Setting the path
        pathTransition.setPath(path);
//
//        //Setting the orientation of the path
//        pathTransition.setOrientation(PathTransition.OrientationType.
//                ORTHOGONAL_TO_TANGENT);

        //Setting the cycle count for the transition
        //pathTransition.setCycleCount(50);

        //Setting auto reverse value to false
        pathTransition.setAutoReverse(true);

        //Playing the animation
        pathTransition.play();


        //Creating a Group object
    }


}
