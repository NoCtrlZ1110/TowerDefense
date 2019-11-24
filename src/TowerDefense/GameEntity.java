package TowerDefense;

import static TowerDefense.GameField.layout;
import javafx.scene.image.ImageView;

public abstract class GameEntity extends ImageView {
    String imageUrl;
    protected boolean is_destroyed;

    public GameEntity(String imageUrl) {
        super(imageUrl);
        is_destroyed = false;
    }

    public GameEntity(int x, int y, String imageUrl) {
        super.setTranslateX(x);
        super.setTranslateX(y);
        this.imageUrl = imageUrl;
        is_destroyed = false;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void scaleTo(int new_width, int new_height) {
        setFitWidth(new_width);
        setFitHeight(new_height);
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

    public void destroy() {
        layout.getChildren().remove(this);
        this.is_destroyed = true;
    }
}
