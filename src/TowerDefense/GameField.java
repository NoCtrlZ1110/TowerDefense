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
import javafx.stage.StageStyle;
import javafx.util.Duration;

import javax.naming.TimeLimitExceededException;
import java.util.ArrayList;

import static TowerDefense.CONSTANT.*;
import static TowerDefense.GameTile.*;

public class GameField {
    static Rectangle border = new Rectangle(BORDER_WIDTH, BORDER_WIDTH);
    static ArrayList<Tower> towers = new ArrayList<>();
    static ArrayList<Enemy> enemies = new ArrayList<>();

    static int money;
    static int hp = 100;
    private static boolean is_paused = false;

    public static Pane layout = new Pane();

    public static void welcomeScreen(Stage stage) {
        Pane pane = new Pane();
        imageObject power = new imageObject("file:images/start.png");
        Button startBtn = new Button(" Start ", power);
        startBtn.setLayoutX(90);
        startBtn.setLayoutY(433);
        startBtn.setMinWidth(126);
        startBtn.setOnAction(event -> {
            if (event.getSource() == startBtn) {
                gameScreen(stage);
            }
        });

        imageObject Welcome = new imageObject("file:images/Welcome_screen.png");
        // Welcome.setLocation(100, 100);
        pane.getChildren().add(Welcome);
        pane.getChildren().add(startBtn);
        stage.setTitle("Tower Defense 1.2");
        stage.setScene(new Scene(pane, 960, 540));
        // stage.initStyle(StageStyle.UTILITY);
        stage.getIcons().add(new Image("file:images/love.jpg"));
        stage.setResizable(true);
        stage.setMinWidth(960);
        stage.setMaxWidth(960);
        stage.setMinHeight(540);
        stage.setMaxHeight(540);
        stage.show();
    }

    public static void gameScreen(Stage stage) {
        stage.close();
        // layout = new Pane();

        // Scene gameScene = new Scene(layout, 1280, 800); // 16 x 10; 80px per block
        Scene gameScene = new Scene(layout, TILE_WIDTH * COL_NUM, TILE_WIDTH * ROW_NUM);
        stage.setMinWidth(TILE_WIDTH * COL_NUM);
        stage.setMaxWidth(TILE_WIDTH * COL_NUM);
        stage.setMinHeight(TILE_WIDTH * ROW_NUM);
        stage.setMaxHeight(TILE_WIDTH * ROW_NUM);

        drawMap();
        //--------------------------------

        // [Tạo đường đi cho lính] -------

        final Path path = new Path();
        path.getElements().add(new MoveTo(-TILE_WIDTH, 760));

        for (int i = 0; i < ROAD_NUM; i++)
            path.getElements().add(new LineTo(roadLocation[i][0], roadLocation[i][1]));

        //-------------------------------
        addEnemiesWave();
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
                towers.forEach(Tower::shoot);
            }
        };
        // [Hiện khung chọn vị trí xây tháp] ---

        border.setStroke(Color.DARKRED);
        border.setStrokeWidth(3);
        border.setFill(Color.TRANSPARENT);
        border.setArcWidth(20.0);
        border.setArcHeight(20.0);

        layout.setOnMouseMoved(event -> {
            Point location = TowerBuildLocation(event);
            Point point = getLocationFromMouseEvent(event);

            if (location != null) {
                // System.out.println(location.getX() + " " + location.getY());
                gameScene.setCursor(Cursor.HAND);
                border.setX(location.getX() + 33);
                border.setY(location.getY() + 33);
            } else if (isTowerPlaced(point)) {
                gameScene.setCursor(Cursor.HAND);
            }
            else {
                gameScene.setCursor(Cursor.DEFAULT);
            }
            // System.out.println(point);
            if (isTowerPlaced(point))
                towers.forEach(t -> {
                    if (Math.abs(point.getX() * TILE_WIDTH - t.getPosition().getX()) <= TILE_WIDTH &&
                            Math.abs(point.getY() * TILE_WIDTH - t.getPosition().getY()) <= TILE_WIDTH)
                        t.showRange();
                    else
                        t.removeRange();
                });
            else
                towers.forEach(Tower::removeRange);
        });

        layout.setOnMouseClicked(event -> {
            // nếu vị trí click có tháp -> bán/upgrade
            //                  ko có tháp -> mua

            Point location = TowerBuildLocation(event);
            System.out.println("clicked " + location);
            if (location != null) {
                border.setX(location.getX() + 33);
                border.setY(location.getY() + 33);
                // bonus: chọn loại tower
                Tower tower = new Tower("file:images/Tower.png");
                buyTower(tower);
                placeTower(tower, location);
            } else if (isTowerPlaced(getLocationFromMouseEvent(event))) {
                // bán/upgrade tháp ở đây
                // hiệu ứng sẽ là click -> 1 menu ở dưới hiện lên, có upgrade và bán

                // tìm tower đang ở vị trí này
                int x = (int)event.getSceneX();
                int y = (int)event.getSceneY();
                boolean is_sell_chosen = true;
                if (is_sell_chosen) {
                    // bán: bán với giá = x% giá mua (có lẽ chỉ 80% thôi)
                    sellTowerAt(x, y);
                } else {
                    // upgrade: hiện dãy icon đại diện cho tháp
                    // upgrade có thể có giá
                    upgradeTowerAt(x, y);
                }
            }
        });

        imageObject pauseImage = new imageObject("file:images/pause.png");
        pauseImage.scaleTo(70, 70);
        Button pauseBtn = new Button("", pauseImage);
        pauseBtn.setLayoutX(1200);
        pauseBtn.setLayoutY(50);
        pauseBtn.setMaxWidth(70);
        pauseBtn.setMaxHeight(70);
        pauseBtn.setOnAction(event -> {
            System.out.println("pause...");
            // pauseScreen(stage);
        });
        // layout.getChildren().add(pauseBtn);

        // [Thêm icon cho game] ---
        stage.getIcons().add(new Image("file:images/love.jpg"));
        // ------------------------
        stage.setScene(gameScene);
        stage.centerOnScreen();
        timer.start();
        timeline.play();
        stage.show();
    }

    public static void pauseScreen(Stage stage) {
        Pane upperLayout = new Pane();
        // pausescreen on top
        imageObject background = new imageObject("file:images/black_background.png");
        upperLayout.getChildren().add(background);

        // layout.getChildren().remove(background);
    }
    // -------------------------

    public static void drawMap() {
        imageObject[][] tiled = new imageObject[ROW_NUM][COL_NUM];

        for (int i = 0; i < ROW_NUM; i++)
            for (int j = 0; j < COL_NUM; j++) {
                tiled[i][j] = new imageObject(pathTile + getTileType(i, j) + ".png");
                tiled[i][j].scaleTo(TILE_WIDTH, TILE_WIDTH);
                tiled[i][j].setLocation(j * TILE_WIDTH, i * TILE_WIDTH);
                layout.getChildren().add(tiled[i][j]);
            }
    }

    public static Tower findTowerAt(int x, int y) {
        for (Tower t: towers) {
            if (t.isXYInTower(x, y)) {
                return t;
            }
        }
        return null;
    }

    public static void buyTower(Tower tower) { // , Point location) {
        money -= tower.getPrice();
    }

    public static void sellTowerAt(int x, int y) {
        Tower tower = findTowerAt(x, y);
        if (tower != null) {
            money += (int)(tower.getPrice() * 0.8);
            towers.remove(tower);
            tower.destroy();
        }
    }

    public static void placeTower(Tower tower, Point location) {
        tower.placeAt(location);
        // tower.showTower(); // đã show trong placeAt
        // tower.showRange();
        towers.add(tower);
    }

    public static void upgradeTowerAt(int x, int y) {
        System.out.println("I'm waiting for you...");
    }

    public static void addEnemiesWave() {
        // [Tạo ra lính] ----------------
        for (int i = 0; i < 20; i++) {
            // Enemy minion = new Enemy(-TILE_WIDTH, 720, pathRedEnemy);
            Enemy minion = new NormalEnemy(-TILE_WIDTH, 720);
            minion.setFitHeight(70);
            minion.setFitWidth(70);
            minion.setSpeed(1.2);
            enemies.add(minion);
            layout.getChildren().add(minion);
        }
    }
}
