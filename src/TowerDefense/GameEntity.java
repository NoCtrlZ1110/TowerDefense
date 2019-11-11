package TowerDefense;

import javafx.scene.image.ImageView;

public abstract class GameEntity extends ImageView {
    String path;

    public GameEntity(String path) {
        super(path);
    }

    public GameEntity(int x, int y, String path) {
        super.setTranslateX(x);
        super.setTranslateX(y);
        this.path = path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setLocation(int x, int y) {
        setTranslateX(x);
        setTranslateY(y);
    }

    public double GetX()
    {
        return this.getTranslateX();
    }

    public double GetY()
    {
        return this.getTranslateY();
    }
    public void showLocation() {
        System.out.println("x: " + getTranslateX() + " y: " + getTranslateY());
    }
    public Point getLocation() {
        return new Point((int)getTranslateX(),(int)getTranslateY());
    }

}
