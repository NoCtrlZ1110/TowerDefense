package TowerDefense;

import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;

import static TowerDefense.CONSTANT.ROAD_NUM;
import static TowerDefense.CONSTANT.TILE_WIDTH;
import static TowerDefense.GameField.*;
import static TowerDefense.GameTile.*;
import static TowerDefense.GameTile.roadLocation;

public class World1 {
    public static void setupWorld1() {
        int roadnum = ROAD_NUM;

        road = new imageObject("file:images/road.png");
        roadLocation = new int[roadnum][2];
        importMap();
        importRoad();

        path.getElements().add(new MoveTo(-TILE_WIDTH, 760));

        for (int i = 0; i < roadnum; i++)
            path.getElements().add(new LineTo(roadLocation[i][0], roadLocation[i][1]));

        road.setOpacity(0);
        layout.getChildren().add(road);
    }
}
