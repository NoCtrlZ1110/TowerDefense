package TowerDefense;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;

import static TowerDefense.CONSTANT.*;
import static TowerDefense.GameTile.*;

public class GameField {
    static Rectangle border = new Rectangle(100, 100);
    static ArrayList<Tower> towers = new ArrayList<>();
    static ArrayList<Enemy> enemies = new ArrayList<>();

    static int money;
    static int hp;

    public static void welcomeScreen(Stage stage) {
        imageObject power = new imageObject("file:images/start.png");
        Pane pane = new Pane();
        Button startBtn = new Button(" Start ");
        startBtn.setGraphic(power);
        startBtn.setLayoutX(90);
        startBtn.setLayoutY(433);
        startBtn.setMinWidth(126);
        startBtn.setOnAction(event -> {
            if (event.getSource() == startBtn) {
                gameScreen(stage);
            }
        });

        imageObject Welcome = new imageObject("file:images/Welcome_screen.png");
        //Welcome.setLocation(100,100);
        pane.getChildren().add(Welcome);
        pane.getChildren().add(startBtn);
        stage.setTitle("Tower Defense 1.0");
        stage.setScene(new Scene(pane, 960, 540));
        stage.getIcons().add(new Image("file:images/love.jpg"));
        stage.show();
    }

    public static Pane layout;

    public static void gameScreen(Stage stage) {
        stage.close();
        layout = new Pane();

        Scene gameScene = new Scene(layout, 1280, 800); // 16 x 10; 80px per block

        // [Vẽ ra map] -------------------
        imageObject[][] tiled = new imageObject[ROW_NUM][COL_NUM];

        for (int i = 0; i < ROW_NUM; i++)
            for (int j = 0; j < COL_NUM; j++) {
                tiled[i][j] = new imageObject(pathTile + getTileType(i, j) + ".png");
                tiled[i][j].setFitHeight(80);
                tiled[i][j].setFitWidth(80);
                tiled[i][j].setLocation(j * 80, i * 80);
                layout.getChildren().add(tiled[i][j]);
            }
        //--------------------------------

        // [Tạo đường đi cho lính] -------

        final Path path = new Path();
        path.getElements().add(new MoveTo(-80, 760));

        for (int i = 0; i < ROAD_NUM; i++)
            path.getElements().add(new LineTo(roadLocation[i][0], roadLocation[i][1]));

        //-------------------------------

        // [Tạo ra lính] ----------------

        for (int i = 0; i < 20; i++) {
            Enemy minion = new Enemy(-80, 720, pathRedEnemy);
            minion.setFitHeight(70);
            minion.setFitWidth(70);
            minion.setSpeed(2);
            enemies.add(minion);
            layout.getChildren().add(minion);
        }

        //-----------------------------

        // [Cho lính di chuyển theo path] ---
        Timeline timeline = new Timeline();
        for (int i = 0; i < enemies.size(); i++) {
            Enemy e = enemies.get(i);
            KeyFrame moveEnemy = new KeyFrame(Duration.millis(i * 800), event -> e.move(path));
            timeline.getKeyFrames().add(moveEnemy);
        }
        //-----------------------------

        // [Hiện thanh máu liên tục theo thời gian]
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                enemies.forEach(Enemy::showHP);
                towers.forEach(Tower::findTarget);
            }
        };
        // [?] tại sao cái timer này ko gộp với timeline ở trên?
        // => showHP gộp vào 1 hàm show duy nhất?

        // [Hiện khung chọn vị trí xây tháp] ---

        border.setStroke(Color.DARKRED);
        border.setStrokeWidth(3);
        border.setFill(Color.TRANSPARENT);
        border.setArcWidth(20.0);
        border.setArcHeight(20.0);

        layout.setOnMouseMoved(event -> {
            Point location = TowerBuildLocation(event);

            if (location != null) {
                gameScene.setCursor(Cursor.HAND);
                border.setX(location.getX() + 33);
                border.setY(location.getY() + 33);
            } else
                gameScene.setCursor(Cursor.DEFAULT);

            Point point = new Point((int) event.getSceneX() / 80, (int) event.getSceneY() / 80);
            System.out.println(point);
            if (getMapType(point.getX(), point.getY()).equals("6"))
                towers.forEach(t -> {
                    if (Math.abs(point.getX() * 80 - t.getPosition().getX()) <= 80 && Math.abs(point.getY() * 80 - t.getPosition().getY()) <= 80)
                        t.showRange();
                    else if (layout.getChildren().contains(t.rangeCircle))
                        layout.getChildren().remove(t.rangeCircle);
                });
            else towers.forEach(t -> {
                if (layout.getChildren().contains(t.rangeCircle))
                    layout.getChildren().remove(t.rangeCircle);
            });
        });

        //-----------------------------


        // [Click để xây tháp] --------

        layout.setOnMouseClicked(event -> {
            // nếu vị trí click có tháp -> bán/upgrade
            //                  ko có tháp -> mua

            Point location = TowerBuildLocation(event);
            if (location != null) {
                placeTower(location);
            } else {
                // kiểm tra thêm có phải đường đi hay không nữa là chắc chắn là đã có tháp :v
                // bán/upgrade tháp ở đây
                // hiệu ứng sẽ là click -> 1 menu ở dưới hiện lên, có upgrade và bán

                /*
                // tìm tower đang ở vị trí này
                Tower local_tower = null; // findTowerAt(location);
                Point point = new Point((int) event.getSceneX() / 80, (int) event.getSceneY() / 80);
                for (Tower t: towers)
                    if (Math.abs(point.getX() * 80 - t.getPosition().getX()) <= 80 && Math.abs(point.getY() * 80 - t.getPosition().getY()) <= 80) {
                        local_tower = t;
                        break;
                    }
                */
                System.out.println("clicked!");

                boolean is_sell_chosen = true;
                if (is_sell_chosen) {
                    // bán: bán với giá = 100% giá mua (có lẽ chỉ 80% thôi)
                    sellTower();
                    removeTower();
                } else {
                    // upgrade: hiện dãy icon đại diện cho tháp
                    // upgrade có thể có giá
                }
                System.out.println("waiting for being sold...");
            }
        });

        // [Thêm icon cho game] ---
        stage.getIcons().add(new Image("file:images/love.jpg"));
        // ------------------------
        stage.setScene(gameScene);
        stage.centerOnScreen();
        timer.start();
        timeline.play();
        stage.show();
    }

    // -------------------------

    public static void buyTower() { money -= 10; }

    public static void sellTower() { money += 8; }

    public static void placeTower(Point location) {
        Tower tower = new Tower("file:images/Tower.png");
        tower.showTower(location);
        // tower.showRange();
        towers.add(tower);
        setMapType(location.getX() / 80, location.getY() / 80, 6);
        setMapType(location.getX() / 80, location.getY() / 80 + 1, 6);
        setMapType(location.getX() / 80 + 1, location.getY() / 80, 6);
        setMapType(location.getX() / 80 + 1, location.getY() / 80 + 1, 6);
    }

    public static void removeTower() {
        // Java ko có từ khoá pass, buồn :(
        // chưa nghĩ ra đặt cái gì vào argument :(
    }

    public void upgradeTower() { }
}
