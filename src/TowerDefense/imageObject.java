package TowerDefense;

public class imageObject extends GameEntity {
    public imageObject(String path) {
        super(path);
    }

    public void scale(int new_width, int new_height) {
        setFitWidth(new_width);
        setFitHeight(new_height);
    }
}
