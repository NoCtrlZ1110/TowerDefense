package TowerDefense;

import static TowerDefense.GameField.layout;
import javafx.scene.image.ImageView;

public abstract class GameEntity extends ImageView {
    String imageUrl;
    protected boolean is_destroyed = false;

    public GameEntity() {}

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

    public void scaleTo(int new_width, int new_height) {
        setFitWidth(new_width);
        setFitHeight(new_height);
    }

    public void setLocation(double x, double y) {
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

    public void destroy() {
        layout.getChildren().remove(this);
        this.is_destroyed = true;
    }
}
