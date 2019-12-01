package TowerDefense;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.shape.Line;
import javafx.util.Duration;

import static TowerDefense.CONSTANT.TILE_WIDTH;
import static TowerDefense.CONSTANT.TOWER_WIDTH;
import static TowerDefense.GameField.layout;

public class Laser extends Bullet {
    // laser thực chất là bê line từ Tower sang
    private static final double MAX_TIME = 10;
    private Line line = new Line();

    public Laser(Tower source, Enemy target) {
        super();
        this.source = source;
        this.target = target;
        this.speed = source.getShootingSpeed();
        this.damage = source.getShootingDamage();

        line.setStartX(this.source.getPosition().getX() + TOWER_WIDTH/2.);
        line.setStartY(this.source.getPosition().getY() + TOWER_WIDTH/2.);
        line.setEndX(this.target.getLocation().getX() + TILE_WIDTH*0.5);
        line.setEndY(this.target.getLocation().getY() + TILE_WIDTH*0.5);
        if (!layout.getChildren().contains(line))
            layout.getChildren().add(line);
    }

    public void beShot() {
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.millis(0), event -> {
            }),
            new KeyFrame(Duration.millis(MAX_TIME), event -> {
                if (target != null)
                    target.beShotBy(this);

                destroy();
            })
        );
        timeline.play();
    }

    public void destroy() {
        layout.getChildren().remove(line);
        this.is_destroyed = true;
    }
}
