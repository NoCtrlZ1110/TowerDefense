package TowerDefense;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import static TowerDefense.CONSTANT.*;
import static TowerDefense.GameField.*;
import static TowerDefense.GameTile.resetMap;
import static TowerDefense.GameTile.setMapType;

public class Tower extends GameEntity {
    private int price = 10;
    private int range = 200;
    private double shootingSpeed = 1;
    private double shootingDamage = 1;
    private Point position;
    private Circle rangeCircle = new Circle();
    private Line line = new Line();

    public Tower(String imageUrl) {
        super(imageUrl);
        scaleTo(TOWER_WIDTH, TOWER_WIDTH);
    }

    protected Tower(String imageUrl, int range, double shootingSpeed, double shootingDamage, int price) {
        super(imageUrl);
        scaleTo(TOWER_WIDTH, TOWER_WIDTH);

        this.range = range;
        this.shootingSpeed = shootingSpeed;
        this.shootingDamage = shootingDamage;
        this.price = price;
    }

    public double getShootingSpeed() {
        return shootingSpeed;
    }

    public double getShootingDamage() {
        return shootingDamage;
    }

    public int getPrice() {
        return price;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        if (is_destroyed)
            return;

        this.position = position;
        this.setLocation(position.getX(), position.getY());

        // getMapType(position.getX() / TILE_WIDTH, position.getY() / TILE_WIDTH)); -> 2
        // getMapType(position.getX() / TILE_WIDTH, position.getY() / TILE_WIDTH + 1)); -> 5
        // getMapType(position.getX() / TILE_WIDTH + 1, position.getY() / TILE_WIDTH)); -> 4
        // getMapType(position.getX() / TILE_WIDTH + 1, position.getY() / TILE_WIDTH + 1)); -> 3

        setMapType(position.getX() / TILE_WIDTH, position.getY() / TILE_WIDTH, 6);
        setMapType(position.getX() / TILE_WIDTH, position.getY() / TILE_WIDTH + 1, 6);
        setMapType(position.getX() / TILE_WIDTH + 1, position.getY() / TILE_WIDTH, 6);
        setMapType(position.getX() / TILE_WIDTH + 1, position.getY() / TILE_WIDTH + 1, 6);
    }

    public void showRange() {
        if (is_destroyed)
            return;

        rangeCircle.setRadius(range);
        rangeCircle.setLayoutX(this.getTranslateX() + TOWER_WIDTH / 2);
        rangeCircle.setLayoutY(this.getTranslateY() + TOWER_WIDTH / 2);
        rangeCircle.setFill(Color.TRANSPARENT);
        // rangeCircle.setOpacity(0.6);
        rangeCircle.setStrokeWidth(3);
        rangeCircle.setStroke(Color.ALICEBLUE);

        if (!layout.getChildren().contains(rangeCircle))
            layout.getChildren().add(rangeCircle);
    }

    public void removeRange() {
        if (layout.getChildren().contains(rangeCircle))
            layout.getChildren().remove(rangeCircle);
    }

    public void showTower() {
        if (this.is_destroyed) {
            layout.getChildren().remove(this);
            return;
        }
        if (!layout.getChildren().contains(this))
            layout.getChildren().add(this);
    }

    public void destroy() {
        // khôi phục trạng thái cũ trước khi đặt tháp
        resetMap(this.position.getX() / TILE_WIDTH, this.position.getY() / TILE_WIDTH);
        removeRange();
        layout.getChildren().remove(line);
        super.destroy();
    }

    public Enemy findTarget() {
        Enemy _target = null;
        double min_distance = range;
        for (Enemy enemy: enemies) {
            Point t = new Point(position.getX()+TOWER_WIDTH/2,position.getY()+TOWER_WIDTH/2);
            Point e = new Point(enemy.getLocation().getX()+TILE_WIDTH/2,enemy.getLocation().getY()+TILE_WIDTH/2);
            if (enemy.exists() && t.getDistance(e) <= min_distance) {
                _target = enemy;
                min_distance = t.getDistance(e);
            }
        }
        if (_target != null) {
            Point t = new Point(position.getX()+TOWER_WIDTH/2,position.getY()+TOWER_WIDTH/2);
            Point e = new Point(_target.getLocation().getX()+TILE_WIDTH/2,_target.getLocation().getY()+TILE_WIDTH/2);
            line.setStartX(t.getX());
            line.setEndX(e.getX());
            line.setStartY(t.getY());
            line.setEndY(e.getY());
//            if (!layout.getChildren().contains(line))
//                layout.getChildren().add(line);
        } else
            layout.getChildren().remove(line);

        return _target;
    }

    public void shoot() {
        if (is_destroyed)
            return;

        Enemy target = findTarget();
        if (target != null) {
            Bullet b = new Bullet(this, target);
            b.beShot();
        }
    }

    public boolean isInTower(int x, int y) {
        return (0 <= x - position.getX() && x - position.getX() <= TOWER_WIDTH &&
                0 <= y - position.getY() && y - position.getY() <= TOWER_WIDTH);
    }
    /*
    public boolean isInTower(Point p) {
        // p.getX() = x - x % (TOWER_WIDTH / 2)
        return (Math.abs(p.getX() - position.getX()) <= TOWER_WIDTH / 2 &&
                Math.abs(p.getY() - position.getY()) <= TOWER_WIDTH / 2);
    }
    */

    public String toString() {
        return String.format(
                "Tower[price=%d,range=%d,shootingSpeed=%f,shootingDamage=%f,x=%d,y=%d",
                price, range, shootingSpeed, shootingDamage, position.getX(), position.getY()
        );
    }
}
