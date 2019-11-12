package TowerDefense;

public class Map {
    private int[][] map;
    private int row_num;
    private int col_num;

    public Map(int row_num, int col_num) {
        this.row_num = row_num;
        this.col_num = col_num;
        map = new int[this.row_num][this.col_num];
        /* Các loại giá trị của mảng map:
           0: không có gì
           1: đường đi
           2: vị trí có thể đặt tháp
           3: bên phải của 2
           4: góc dưới bên phải của 2
           5: bên dưới của 2
           6: vị trí đã có tháp
        */
    }

    public int[][] getCoreTable() {
        return map;
    }

    public String getType(int x, int y) {
        if (y < this.row_num && x < this.col_num)
            return Integer.toString(map[y][x]);
        else
            return "";
    }

    public void setType(int x, int y, int n) {
        map[y][x] = n;
    }

    public boolean isTowerPlaced(int x, int y) {
        return "6".equals(getType(x, y));
    }
}
