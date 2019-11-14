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

    public boolean isTowerPlacedAt(int x, int y) {
        return "6".equals(getType(x, y));
    }

    public void reset(int x, int y) {
        map[y][x] = original_map[y][x];
        map[y + 1][x] = original_map[y + 1][x];
        map[y][x + 1] = original_map[y][x + 1];
        map[y + 1][x + 1] = original_map[y + 1][x + 1];
    }

    public void backup() {
        if (original_map == null) {
            original_map = new int[this.row_num][this.col_num];

            for (int i = 0; i < this.row_num; i++) {
                System.arraycopy(map[i], 0, original_map[i],0, this.col_num);
            }
        }
    }
}
