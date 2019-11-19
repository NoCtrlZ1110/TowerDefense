package TowerDefense;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import static TowerDefense.CONSTANT.*;
import static TowerDefense.GameField.*;
import static TowerDefense.GameTile.resetMap;
import static TowerDefense.GameTile.setMapType;

public class Tower extends GameEntity {
    int price = 10;
    Point position;
    int range = 200;
    Circle rangeCircle = new Circle();
    private Line line = new Line();
    private boolean is_destroyed = false;

    public Tower(String imageUrl) {
        super(imageUrl);
        setScale(TOWER_WIDTH, TOWER_WIDTH);
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
        //rangeCircle.setOpacity(0.6);
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
        layout.getChildren().remove(this);
        this.is_destroyed = true;
    }

    public Enemy findTarget() {
        for (Enemy enemy: enemies) {
            Point t = new Point(getPosition().getX()+TOWER_WIDTH/2,getPosition().getY()+TOWER_WIDTH/2);
            Point e = new Point(enemy.getLocation().getX()+TILE_WIDTH/2,enemy.getLocation().getY()+TILE_WIDTH/2);
            if (t.getDistance(e) <= range) {
                line.setStartX(t.getX());
                line.setEndX(e.getX());
                line.setStartY(t.getY());
                line.setEndY(e.getY());
                if (!layout.getChildren().contains(line))
                    layout.getChildren().add(line);

                return enemy;
            }
            e = null;
            t = null;
        }
        layout.getChildren().remove(line);
        return null;
    }

    public void shoot() {
        if (is_destroyed)
            return;

        Enemy target = findTarget();
        if (target != null) {
            /*
            Point t = new Point(getPosition().getX()+TOWER_WIDTH/2,getPosition().getY()+TOWER_WIDTH/2);
            Point e = new Point(target.getLocation().getX()+TILE_WIDTH/2,target.getLocation().getY()+TILE_WIDTH/2);
            Bullet b = new Bullet(1, 1, t.getX(), t.getY(), e.getX(), e.getY());
            */
            Bullet b = new Bullet(1, 1, this, target);
            b.beShot();
            // target.beShotBy(b);
            if (target.isDead()) { // b.getTarget().isDead()
                target.deleteHPbar();

                money += target.getKilledBonus();
                System.out.println("new money = " + money);
                enemies.remove(target);
            }
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
}
