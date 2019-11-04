package TowerDefense;

public class Enemy extends GameEntity {
    public Enemy(String path) {
        super(path);
    }

    public Enemy(int x, int y, String path) {
        super(path);
        setLocation(x,y);
    }


}
