package TowerDefense;

import javafx.scene.input.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static TowerDefense.GameField.*;

import static TowerDefense.CONSTANT.*;

public class GameTile {
    static Map map = new Map(ROW_NUM, COL_NUM);
    static int[][] tileType = new int[ROW_NUM][COL_NUM];
    // Mảng tileType lưu loại tile để load ảnh phù hợp tạo nên 1 bản đồ

    static int[][] roadLocation = new int[ROAD_NUM][2];
    // Mảng lưu trữ các vị trí cụ thể của đường đi (path)

    public GameTile() {
    }

    public static void importMap() {
        getData(map.getCoreTable(), ROW_NUM, COL_NUM, pathMap);
        getData(tileType, ROW_NUM, COL_NUM, pathTileType);
    }

    public static void importRoad() {
        getData(roadLocation, ROAD_NUM, 2, pathTransition);
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

    public static void getData(int[][] arr, int height, int width, String path) {
        try {
            FileInputStream MapIn = new FileInputStream(path);
            Scanner sc = new Scanner(MapIn);
            for (int i = 0; i < height; i++) {
                String temp = sc.nextLine(); //doc dong mang trong file
                //System.out.println(temp);
                String[] items = temp.split(" "); //tach chuoi thanh cac phan tu chuoi
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
        Point point = new Point((int) event.getSceneX() / TILE_WIDTH, (int) event.getSceneY() / TILE_WIDTH);
        int point_x = point.getX();
        int point_y = point.getY();
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

//    public static boolean isTowerPlacedAt() {
//
//    }
}
