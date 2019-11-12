package TowerDefense;

import javafx.scene.Cursor;

import javafx.scene.input.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.beans.EventHandler;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static TowerDefense.GameField.*;

import static TowerDefense.CONSTANT.*;

public class GameTile {
    // static Map map = new Map(ROW_NUM, COL_NUM);
    static int[][] map = new int[ROW_NUM][COL_NUM];
    /* Các loại giá trị của mảng map:
       0: không có gì
       1: đường đi
       2: vị trí có thể đặt tháp
       3: bên phải của 2
       4: góc dưới bên phải của 2
       5: bên dưới của 2
       6: vị trí đã có tháp
     */

    static int[][] tileType = new int[ROW_NUM][COL_NUM];
    // Mảng tileType lưu loại tile để load ảnh phù hợp tạo nên 1 bản đồ

    static int[][] roadLocation = new int[ROAD_NUM][2];
    // Mảng lưu trữ các vị trí cụ thể của đường đi (path)

    public GameTile() {
    }

    public static void ImportMap() {
        getData(map, ROW_NUM, COL_NUM, pathMap);
        getData(tileType, ROW_NUM, COL_NUM, pathTileType);
    }

    public static void ImportRoad() {
        getData(roadLocation, ROAD_NUM, 2, pathTransition);

    }

    public static String getTileType(int x, int y) {
        return Integer.toString(tileType[x][y]);
    }

    public static String getMapType(int x, int y) {
        // map.getType(x, y);
        if (y < ROW_NUM && x < COL_NUM)
            return Integer.toString(map[y][x]);
        else return "";
    }

    public static void setMapType(int x, int y, int n) {
        // map.setType(x, y, n);
        map[y][x] = n;
    }

    public static void getData(int[][] arr, int height, int width, String path) {
        try {
            FileInputStream MapIn = new FileInputStream(path);
            Scanner sc = new Scanner(MapIn);
            for (int i = 0; i < height; i++) {
                String temp = sc.nextLine(); //doc dong mang trong file
                //System.out.println(temp);
                String[] items = temp.split(" "); //tach chuoi thanh cac phan tu chuoi
                for (int j = 0; j < width; j++) arr[i][j] = Integer.parseInt(items[j]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Kiểm tra xem vị trí được truyền vào có xây được tháp không (1 tháp chiếm 2x2)
    // Nếu có trả về vị trí để xây tháp

    public static Point TowerBuildLocation(MouseEvent event) {
        Point point = new Point((int) event.getSceneX() / TILE_WIDTH, (int) event.getSceneY() / TILE_WIDTH);
        String s = getMapType(point.getX(), point.getY());

        // Điều kiện ở đây hơi khó hiểu nên không cần phải đọc đâu @@

        if (!layout.getChildren().contains(border))
            layout.getChildren().add(border);

        if (s.equals("2") && !(getMapType(point.getX() + 1, point.getY()).equals("6") || getMapType(point.getX() + 1, point.getY() + 1).equals("6") || getMapType(point.getX(), point.getY() + 1).equals("6"))) {
            return new Point((point.getX()) * TILE_WIDTH, (point.getY()) * TILE_WIDTH);
        } else if (s.equals("3") && !((getMapType(point.getX() - 1, point.getY()).equals("6") || getMapType(point.getX() - 1, point.getY() + 1).equals("6") || getMapType(point.getX(), point.getY() + 1).equals("6")))) {
            return new Point((point.getX() - 1) * TILE_WIDTH, (point.getY()) * TILE_WIDTH);
        } else if (s.equals("4") && !(getMapType(point.getX() - 1, point.getY() - 1).equals("6") || getMapType(point.getX() - 1, point.getY()).equals("6") || getMapType(point.getX(), point.getY() - 1).equals("6"))) {
            return new Point((point.getX() - 1) * TILE_WIDTH, (point.getY() - 1) * TILE_WIDTH);
        } else if (s.equals("5") && !(getMapType(point.getX(), point.getY() - 1).equals("6") || getMapType(point.getX() + 1, point.getY() - 1).equals("6") || getMapType(point.getX() + 1, point.getY() + 1).equals("6"))) {
            return new Point(point.getX() * TILE_WIDTH, (point.getY() - 1) * TILE_WIDTH);
        } else {
            layout.getChildren().remove(border);
            return null;
        }
    }

    // public static boolean
}
