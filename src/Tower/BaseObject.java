package Tower;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BaseObject extends ImageView {
    String path;

    public BaseObject(String path) {
        super(path);
    }

    public BaseObject(int x, int y, String path) {
        super.setTranslateX(x);
        super.setTranslateX(y);
        this.path = path;
    }


    public void setPath(String path) {
        this.path = path;
    }

    public void setLocation(int x, int y)
    {
        setTranslateX(x);
        setTranslateY(y);
    }

}
