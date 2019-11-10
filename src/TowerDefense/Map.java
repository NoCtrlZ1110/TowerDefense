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

public class Map {
    static int[][] map = new int[ROW_NUM][COL_NUM];
    static int[][] tileType = new int[ROW_NUM][COL_NUM];
    static int[][] roadLocation = new int[15][2];


    public Map() {
    }

    public static void ImportMap() {
        getData(map, ROW_NUM, COL_NUM, pathMap);
        getData(tileType, ROW_NUM, COL_NUM, pathTileType);
    }

    public static void ImportRoad() {
        getData(roadLocation, 15, 2, pathTransition);

    }

    public static String getTileType(int x, int y) {
        return Integer.toString(tileType[x][y]);
    }

    public static String getMapType(int x, int y) {
        if (y < ROW_NUM && x < COL_NUM)
            return Integer.toString(map[y][x]);
        else return "";
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

        Point point = new Point((int) event.getSceneX() / 80, (int) event.getSceneY() / 80);
        String s = getMapType(point.getX(), point.getY());

        if (!layout.getChildren().contains(border)) layout.getChildren().add(border);
        if (s.equals("2")) {
//
//            border.setX((point.getX()) * 80 + 33);
//            border.setY(point.getY() * 80 + 33);

            return new Point((point.getX()) * 80, (point.getY()) * 80);
        } else if (s.equals("3")) {
//            border.setX((point.getX() - 1) * 80 + 33);
//            border.setY(point.getY() * 80 + 33);
            return new Point((point.getX() - 1) * 80, (point.getY()) * 80);
        } else if (s.equals("4")) {
//            border.setX((point.getX() - 1) * 80 + 33);
//            border.setY((point.getY() - 1) * 80 + 33);
            return new Point((point.getX() - 1) * 80, (point.getY() - 1) * 80);
        } else if (s.equals("5")) {
//            border.setX((point.getX()) * 80 + 33);
//            border.setY((point.getY() - 1) * 80 + 33);
            return new Point(point.getX() * 80, (point.getY() - 1) * 80);
        } else {
            layout.getChildren().remove(border);
            return null;
            //gameScene.setCursor(Cursor.DEFAULT);
        }

    }

}
