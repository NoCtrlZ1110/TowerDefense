package TowerDefense;

import javafx.animation.Timeline;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;

import static TowerDefense.CONSTANT.*;
import static TowerDefense.GameField.*;
import static TowerDefense.GameTile.*;

public class World {
    /* roadnum = (world_select == 1 ? ROAD_NUM : ROAD_NUM2); */

    public static void setupWorld() {
        if (world_select == 1)
            setupWorld1();
        else
            setupWorld2();

        road.setOpacity(0);
        // layout.getChildren().add(road);
    }

    private static void setupWorld1() {
        int roadnum = ROAD_NUM;

        road = new imageObject("file:images/road.png");
        roadLocation = new int[roadnum][2];
        importMap();
        importRoad();

        path.getElements().add(new MoveTo(-TILE_WIDTH, 760));

        for (int i = 0; i < roadnum; i++)
            path.getElements().add(new LineTo(roadLocation[i][0], roadLocation[i][1]));
    }

    private static void setupWorld2() {
        int roadnum = ROAD_NUM2;

        road = new imageObject("file:images/road2.png");
        roadLocation = new int[roadnum][2];
        importMap();
        importRoad();

        path.getElements().add(new MoveTo(-TILE_WIDTH, 600));

        for (int i = 0; i < roadnum; i++)
            path.getElements().add(new LineTo(roadLocation[i][0], roadLocation[i][1]));
    }
}
