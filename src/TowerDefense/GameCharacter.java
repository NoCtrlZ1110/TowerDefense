package TowerDefense;

/*
GameCharacter
- bao gồm User và Enemy
- (chỉ) quản lý hp
*/

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static TowerDefense.GameField.layout;

public class GameCharacter extends GameEntity {
    protected double hp;
    protected double hp_max;
    private double hp_bar_width = 0;
    private double hp_bar_height = 0;
    private double hp_bar_x = -1;
    private double hp_bar_y = -1;
    private Rectangle hp_bar = new Rectangle();
    private Rectangle hp_max_bar = new Rectangle();

    public GameCharacter(String imageUrl) {
        super(imageUrl);
        initHpBar();
    }

    public GameCharacter(double hp_max, double hp_bar_width, double hp_bar_height, double hp_bar_x, double hp_bar_y) {
        this.hp_max = hp_max;
        this.hp = hp_max;
        this.hp_bar_width = hp_bar_width;
        this.hp_bar_height = hp_bar_height;
        this.hp_bar_x = hp_bar_x;
        this.hp_bar_y = hp_bar_y;
        initHpBar();
    }

    public GameCharacter(String imageUrl, double hp_max, double hp_bar_width, double hp_bar_height) {
        super(imageUrl);
        this.hp = hp_max;
        this.hp_max = hp_max;
        this.hp_bar_width = hp_bar_width;
        this.hp_bar_height = hp_bar_height;
        initHpBar();
    }

    protected void setHpBarXY(double hp_bar_x, double hp_bar_y) {
        this.hp_bar_x = hp_bar_x;
        this.hp_bar_y = hp_bar_y;
    }

    public boolean isDead() {
        return (hp <= 0);
    }

    public void decreaseHP(double amount) {
        hp -= amount;
    }

    private void initHpBar() {
        hp_max_bar = new Rectangle();
        hp_bar = new Rectangle();

        hp_max_bar.setWidth(this.hp_bar_width);
        hp_max_bar.setHeight(this.hp_bar_height);
        hp_max_bar.setFill(Color.ALICEBLUE);

        hp_bar.setWidth(this.hp_bar_width);
        hp_bar.setHeight(this.hp_bar_height);
        hp_bar.setFill(Color.DARKRED);

        layout.getChildren().add(hp_max_bar);
        layout.getChildren().add(hp_bar);
    }

    public void displayHpBar() {
        if (this.is_destroyed)
            return;

        hp_max_bar.setX(this.hp_bar_x);
        hp_max_bar.setY(this.hp_bar_y);

        hp_bar.setX(this.hp_bar_x);
        hp_bar.setY(this.hp_bar_y);

        hp_bar.setWidth(hp_max_bar.getWidth() * (Math.max(0.0, hp) / hp_max));
        // hp_bar.setFill(Color.DARKRED);
    }

    public void deleteHpBar() {
        // hp_bar.setVisible(false);
        layout.getChildren().remove(hp_bar);
        hp_bar = null;
        // hp_max_bar.setVisible(false);
        layout.getChildren().remove(hp_max_bar);
        hp_max_bar = null;
    }

    public void destroy() {
        deleteHpBar();
        super.destroy();
    }
}
