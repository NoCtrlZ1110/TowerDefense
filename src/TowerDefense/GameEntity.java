package TowerDefense;

import javafx.scene.image.ImageView;

public abstract class GameEntity extends ImageView {
    String url;

    public GameEntity(String url) {
        super(url);
    }

    public GameEntity(int x, int y, String url) {
        super.setTranslateX(x);
        super.setTranslateX(y);
        this.url = url;
    }

    public void setPath(String url) {
        this.url = url;
    }

    public void setScale(int x, int y)
    {
        this.setFitHeight(x);
        this.setFitWidth(y);
    }

    public void setLocation(int x, int y) {
        setTranslateX(x);
        setTranslateY(y);
    }

    public double GetX() {
        return this.getTranslateX();
    }

    public double GetY() {
        return this.getTranslateY();
    }

    public void showLocation() {
        System.out.println("x: " + getTranslateX() + " y: " + getTranslateY());
    }

    public Point getLocation() {
        return new Point((int) getTranslateX(), (int) getTranslateY());
    }

}
