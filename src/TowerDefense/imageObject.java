package TowerDefense;

public class imageObject extends GameEntity {
    public imageObject(String path) {
        super(path);
    }

    public void scaleTo(int new_width, int new_height) {
        setFitWidth(new_width);
        setFitHeight(new_height);
    }
    /*
    public void setLayoutPosition(int x, int y) {
        setLayoutX(x);
        setLayoutY(y);
    }
    */
}
