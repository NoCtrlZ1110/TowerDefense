package Tower;

import javafx.scene.Group;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static Tower.CONSTANT.*;

public class Map {
    static int[][] map = new int[ROW_NUM][COL_NUM];

    public Map() {
    }
    public static void ImportMap()
    {
        getData(map, ROW_NUM, COL_NUM, pathMap);
    }
    public static int getType(int x, int y)
    {
        return map[x][y];
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
}
