package TowerDefense;

import javafx.scene.input.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static TowerDefense.GameField.*;
import static TowerDefense.CONSTANT.*;

public class GameTile {
    private static Map map = new Map(ROW_NUM, COL_NUM);
    private static int[][] tileType = new int[ROW_NUM][COL_NUM];
    // Mảng tileType lưu loại tile để load ảnh phù hợp tạo nên 1 bản đồ

    static int[][] roadLocation;

    // Mảng lưu trữ các vị trí cụ thể của đường đi (path)

    public GameTile() {
    }

    public static void importMap() {
        if (world_select == 1)
            getData(map.getCoreTable(), ROW_NUM, COL_NUM, pathMap);
        else
            getData(map.getCoreTable(), ROW_NUM, COL_NUM, pathMap2);
        map.backup();
        getData(tileType, ROW_NUM, COL_NUM, pathTileType);
    }

    public static void importRoad() {
        if (world_select == 1)
            getData(roadLocation, ROAD_NUM, 2, pathTransition);
        else
            getData(roadLocation, ROAD_NUM2, 2, pathTransition2);
    }

    public static Point getEndPointOfRoad() {
        if (world_select == 1)
            return new Point(roadLocation[ROAD_NUM-1][0], roadLocation[ROAD_NUM-1][1]);
        else
            return new Point(roadLocation[ROAD_NUM2-1][0], roadLocation[ROAD_NUM2-1][1]);
    }

    public static String getTileType(int x, int y) {
        return Integer.toString(tileType[x][y]);
    }

    public static String getMapType(int x, int y) {
        return map.getType(x, y);
    }

    public static void setMapType(int x, int y, int n) {
        map.setType(x, y, n);
    }

    public static void getData(int[][] arr, int height, int width, String fileDir) {
        try {
            FileInputStream MapIn = new FileInputStream(fileDir);
            Scanner sc = new Scanner(MapIn);
            for (int i = 0; i < height; i++) {
                String temp = sc.nextLine(); // doc dong mang trong file
                //System.out.println(temp);
                String[] items = temp.split(" "); // tach chuoi thanh cac phan tu chuoi
                for (int j = 0; j < width; j++)
                    arr[i][j] = Integer.parseInt(items[j]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Kiểm tra xem vị trí được truyền vào có xây được tháp không (1 tháp chiếm 2x2)
    // Nếu có trả về vị trí để xây tháp

    public static Point TowerBuildLocation(MouseEvent event) {
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

    public static boolean isTowerPlaced(Point point) {
        return map.isTowerPlacedAt(point.getX(), point.getY());
    }

    public static boolean isRoadPlaced(Point point) {
        return map.isRoadPlacedAt(point.getX(), point.getY());
    }

    public static void resetMap(int x, int y) {
        map.reset(x, y);
    }

    public static Point getLocationFromMouseEvent(MouseEvent event) {
        return new Point((int)(event.getSceneX() / TILE_WIDTH),
                (int)(event.getSceneY() / TILE_WIDTH));
    }
}
