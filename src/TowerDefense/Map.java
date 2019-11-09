package TowerDefense;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static TowerDefense.CONSTANT.*;

public class Map {
    static int[][] map = new int[ROW_NUM][COL_NUM];
    static int[][] tileType = new int[ROW_NUM][COL_NUM];
    static int[][] roadLocation = new int[15][2];


    public Map() {
    }
    public static void ImportMap()
    {
        getData(map, ROW_NUM, COL_NUM, pathMap);
        getData(tileType,ROW_NUM, COL_NUM,pathTileType);
    }
    public static void ImportRoad()
    {
        getData(roadLocation, 15, 2, pathTransition);
        for (int i = 0; i< 15; i++)
                {System.out.print(roadLocation[i][0]-40+" ");
        System.out.println(roadLocation[i][1]-40);}
    }
    public static String getTileType(int x, int y)
    {
        return Integer.toString(tileType[x][y]);
    }
    public static String getMapType(int x, int y)
    {
        return Integer.toString(map[x][y]);
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
