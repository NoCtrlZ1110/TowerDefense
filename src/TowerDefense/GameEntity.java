package TowerDefense;

import javafx.scene.image.ImageView;

public abstract class GameEntity extends ImageView {
    String imageUrl;

    public GameEntity(String imageUrl) {
        super(imageUrl);
    }

    public GameEntity(int x, int y, String imageUrl) {
        super.setTranslateX(x);
        super.setTranslateX(y);
        this.imageUrl = imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setLocation(int x, int y) {
        setTranslateX(x);
        setTranslateY(y);
    }

    public Point getLocation() {
        return new Point((int)getTranslateX(), (int)getTranslateY());
    }

    public void showLocation() {
        System.out.println("x: " + getTranslateX() + " y: " + getTranslateY());
    }
    public double GetX() {
        return this.getTranslateX();
    }
    public double GetY() {
        return this.getTranslateY();
    }
}
