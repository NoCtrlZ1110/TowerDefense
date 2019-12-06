import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        int[][] temp = new int[10][16];
        getData(temp, 10, 16, "map/map2.txt");

    }

    public static void getData(int[][] arr, int height, int width, String fileDir) {
        try {
            FileInputStream MapIn = new FileInputStream(fileDir);
            Scanner sc = new Scanner(MapIn);
            for (int i = 0; i < height; i++) {
                String temp = sc.nextLine(); // doc dong mang trong file
                //System.out.println(temp);
                String[] items = temp.split(" "); // tach chuoi thanh cac phan tu chuoi
                for (int j = 0; j < width; j++) {
                    arr[i][j] = Integer.parseInt(items[j]);
                    if (arr[i][j] == 6) System.out.print(j*80+40 +" ");
                    if (arr[i][j] == 6) System.out.println(i*80+40);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}