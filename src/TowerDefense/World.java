package TowerDefense;

import javafx.scene.input.MouseEvent;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

import static TowerDefense.CONSTANT.*;
import static TowerDefense.GameField.*;
import static TowerDefense.GameTile.getData;
import static TowerDefense.GameTile.getTileType;

public class World {
    private static final String[] PATH_ROAD = {"file:images/road2.png", "file:images/road.png"};
    private static final int[] _ROAD_NUM = {ROAD_NUM2, ROAD_NUM};
    private static final String[] PATH_MAP = {pathMap2, pathMap};
    private static final String[] PATH_TRANSITION = {pathTransition2, pathTransition};

    private int world_select;
    private imageObject road;
    private int roadnum;

    private Map map = new Map(ROW_NUM, COL_NUM);
    private int[][] tileType = new int[ROW_NUM][COL_NUM];
    // Mảng tileType lưu loại tile để load ảnh phù hợp tạo nên 1 bản đồ
    private int[][] roadLocation;
    private final static Path path = new Path();

    public World(int world_select) {
        this.world_select = world_select;
        roadnum = _ROAD_NUM[world_select];

        road = new imageObject(PATH_ROAD[world_select]);
        roadLocation = new int[roadnum][2];

        importMap();
        importRoad();
        generatePath();
    }

    private void importMap() {
        getData(map.getCoreTable(), ROW_NUM, COL_NUM, PATH_MAP[world_select]);
        map.backup();
        getData(tileType, ROW_NUM, COL_NUM, pathTileType);
    }

    private void importRoad() {
        getData(roadLocation, roadnum, 2, PATH_TRANSITION[world_select]);
    }

    private void generatePath() {
        if (world_select == 1)
            path.getElements().add(new MoveTo(-TILE_WIDTH, 760));
        else
            path.getElements().add(new MoveTo(-TILE_WIDTH, 600));

        for (int i = 0; i < roadnum; i++)
            path.getElements().add(new LineTo(roadLocation[i][0], roadLocation[i][1]));
    }

    public Point getEndPointOfRoad() {
        return new Point(roadLocation[roadnum-1][0], roadLocation[roadnum-1][1]);
    }

    public String getMapType(int x, int y) {
        return map.getType(x, y);
    }

    public void setMapType(int x, int y, int n) {
        map.setType(x, y, n);
    }

    // Kiểm tra xem vị trí được truyền vào có xây được tháp không (1 tháp chiếm 2x2)
    // Nếu có trả về vị trí để xây tháp

    public Point TowerBuildLocation(MouseEvent event) {
        int point_x = (int) event.getSceneX() / TILE_WIDTH;
        int point_y = (int) event.getSceneY() / TILE_WIDTH;
        String s = map.getType(point_x, point_y);

        // Điều kiện ở đây hơi khó hiểu nên không cần phải đọc đâu @@

        if (!layout.getChildren().contains(border))
            layout.getChildren().add(border);

        if (s.equals("2") && !(map.isTowerPlacedAt(point_x + 1, point_y) || map.isTowerPlacedAt(point_x + 1, point_y + 1) || map.isTowerPlacedAt(point_x, point_y + 1))) {
            return new Point((point_x) * TILE_WIDTH, (point_y) * TILE_WIDTH);
        } else if (s.equals("3") && !((map.isTowerPlacedAt(point_x - 1, point_y) || map.isTowerPlacedAt(point_x - 1, point_y + 1) || map.isTowerPlacedAt(point_x, point_y + 1)))) {
            return new Point((point_x - 1) * TILE_WIDTH, (point_y) * TILE_WIDTH);
        } else if (s.equals("4") && !(map.isTowerPlacedAt(point_x - 1, point_y - 1) || map.isTowerPlacedAt(point_x - 1, point_y) || map.isTowerPlacedAt(point_x, point_y - 1))) {
            return new Point((point_x - 1) * TILE_WIDTH, (point_y - 1) * TILE_WIDTH);
        } else if (s.equals("5") && !(map.isTowerPlacedAt(point_x, point_y - 1) || map.isTowerPlacedAt(point_x + 1, point_y - 1) || map.isTowerPlacedAt(point_x + 1, point_y + 1))) {
            return new Point(point_x * TILE_WIDTH, (point_y - 1) * TILE_WIDTH);
        } else {
            layout.getChildren().remove(border);
            return null;
        }
    }

    public boolean isTowerPlaced(Point point) {
        return map.isTowerPlacedAt(point.getX(), point.getY());
    }

    public boolean isRoadPlaced(Point point) {
        return map.isRoadPlacedAt(point.getX(), point.getY());
    }

    public void resetMap(int x, int y) {
        map.reset(x, y);
    }

    public static void drawMap() {
        imageObject[][] tiled = new imageObject[ROW_NUM][COL_NUM];

        for (int i = 0; i < ROW_NUM; i++)
            for (int j = 0; j < COL_NUM; j++) {
                tiled[i][j] = new imageObject(pathTile + getTileType(i, j) + ".png");
                tiled[i][j].scaleTo(TILE_WIDTH, TILE_WIDTH);
                tiled[i][j].setLocation(j * TILE_WIDTH, i * TILE_WIDTH);
                layout.getChildren().add(tiled[i][j]);
            }
    }
}