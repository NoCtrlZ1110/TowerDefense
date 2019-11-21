package TowerDefense;

import javafx.animation.*;
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
import java.util.concurrent.atomic.AtomicReference;

import static TowerDefense.CONSTANT.*;
import static TowerDefense.GameTile.*;
import static TowerDefense.Sound.*;

public class GameField {
    static Rectangle border = new Rectangle(BORDER_WIDTH, BORDER_WIDTH);
    static ArrayList<Tower> towers = new ArrayList<>();
    static ArrayList<Enemy> enemies = new ArrayList<>();

    private static int money = 100;
    private static int hp = 100;
    private static boolean is_paused = false;

    public static Pane layout = new Pane();

    // thêm property stage: Stage

    public static void welcomeScreen(Stage stage) {
        Pane pane = new Pane();

        Scene scene = new Scene(pane, 960, 540);

        imageObject welcomScr = new imageObject("file:images/welcome1.png");
        welcomScr.scaleTo(960, 540);

        Timeline timeline = new Timeline(
            new KeyFrame(Duration.millis(0), event -> {
                imageObject blackScr = new imageObject("file:images/black.png");
                blackScr.scaleTo(960, 540);
                pane.getChildren().add(blackScr);
            }),
            new KeyFrame(Duration.millis(850), event -> {
                imageObject logoScr = new imageObject("file:images/logo.png");
                logoScr.scaleTo(960, 540);
                pane.getChildren().add(logoScr);
            }),
            new KeyFrame(Duration.seconds(2), event -> {
                welcomScr.setOpacity(0);
                pane.getChildren().add(welcomScr);
                FadeTransition ft = new FadeTransition(Duration.millis(2000), welcomScr);
                ft.setFromValue(0);
                ft.setToValue(1);
                ft.play();
            }),
            new KeyFrame(Duration.seconds(3), event -> {
                imageObject startBtn = new imageObject("file:images/startBtn.png");
                startBtn.setLocation(73, 437);
                startBtn.scaleTo(184, 56);
                startBtn.setOpacity(0);
                pane.getChildren().add(startBtn);
                startBtn.setOnMouseEntered(event1 -> {
                    startBtn.setOpacity(1);
                    scene.setCursor(Cursor.HAND);
                });

                startBtn.setOnMouseExited(event1 -> {
                    startBtn.setOpacity(0);
                    scene.setCursor(Cursor.DEFAULT);
                });

                startBtn.setOnMouseClicked(event1 -> {
                    clickSound();
                    gameScreen(stage);
                });
            })
        );

        stage.setTitle("Tower Defense 1.2");

        stage.setScene(scene);
        stage.getIcons().add(new Image("file:images/love.jpg"));
        stage.setResizable(true);
        stage.setMinWidth(960);
        stage.setMaxWidth(960);
        stage.setMinHeight(540);
        stage.setMaxHeight(540);
        stage.show();

        timeline.play();
        playWelcomeMusic();
    }

    public static void gameScreen(Stage stage) {
        pauseWelcomeMusic();
        stage.close();
        // layout = new Pane();

        // Scene gameScene = new Scene(layout, 1280, 800); // 16 x 10; 80px per block
        Scene gameScene = new Scene(layout, TILE_WIDTH * COL_NUM, TILE_WIDTH * ROW_NUM);
        stage.setMinWidth(TILE_WIDTH * COL_NUM);
        stage.setMaxWidth(TILE_WIDTH * COL_NUM);
        stage.setMinHeight(TILE_WIDTH * ROW_NUM);
        stage.setMaxHeight(TILE_WIDTH * ROW_NUM);

        imageObject background = new imageObject("file:images/back.png");
        background.setLocation(0, 0);
        background.scaleTo(TILE_WIDTH * COL_NUM, TILE_WIDTH * ROW_NUM);
        layout.getChildren().add(background);

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

        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(0), event -> prepareMusic()));
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(PREPARE_TIME-2), event -> combatMusic()));

        for (int i = 0; i < enemies.size(); i++) {
            Enemy e = enemies.get(i);
            KeyFrame moveEnemy = new KeyFrame(Duration.millis(i * 800 + PREPARE_TIME*1000), event -> e.move(path));
            timeline.getKeyFrames().add(moveEnemy);
        }
        //-----------------------------

        // [Hiện thanh máu liên tục theo thời gian]
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                enemies.forEach(e -> {
                    e.showHP();
                    e.harm();
                });
                // towers.forEach(Tower::shoot);
            }
        };

        Timeline shootTimeLine = new Timeline(new KeyFrame(Duration.millis(20), event -> {
            towers.forEach(Tower::shoot);
        }));
        shootTimeLine.setCycleCount(Animation.INDEFINITE);
        shootTimeLine.play();

        // [Hiện khung chọn vị trí xây tháp] ---

        border.setStroke(Color.WHITESMOKE);
        border.setStrokeWidth(3);
        border.setFill(Color.TRANSPARENT);
        border.setArcWidth(20.0);
        border.setArcHeight(20.0);

        layout.setOnMouseMoved(event -> {
            Point location = TowerBuildLocation(event);
            Point point = getLocationFromMouseEvent(event);

            if (location != null) {
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

            towers.forEach(t -> {
                // if (isTowerPlaced(point) && t.isInTower((int)event.getSceneX(), (int)event.getSceneY()))
                if (t.isInTower((int)event.getSceneX(), (int)event.getSceneY()))
                    t.showRange();
                else
                    t.removeRange();
            });
        });

        layout.setOnMouseClicked(event -> {
            // nếu vị trí click có tháp -> bán/upgrade
            //                  ko có tháp -> mua

            Point location = TowerBuildLocation(event);

            if (location != null) {
                border.setX(location.getX() + 33);
                border.setY(location.getY() + 33);
                // mua: hiện dãy icon đại diện cho tháp
                // => chọn loại tower (thêm tham số, có thể là string)
                Roadside r = new Roadside(location.getX(), location.getY());
                r.buyTower("");
            } else if (isTowerPlaced(getLocationFromMouseEvent(event))) {
                // bán/upgrade tháp ở đây
                // hiệu ứng sẽ là click -> 1 menu ở dưới hiện lên, có upgrade và bán

                int x = (int)event.getSceneX();
                int y = (int)event.getSceneY();
                Roadside r = new Roadside(x, y);
                boolean is_sell_chosen = true;
                if (is_sell_chosen) {
                    // bán: bán với giá = x% giá mua (có lẽ chỉ 80% thôi)
                    r.sellPlacedTower();
                } else {
                    // upgrade: hiện dãy icon đại diện cho tháp
                    // upgrade có thể có giá
                    r.upgradePlacedTower();
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

    public static void showGameOverScreen() {
        System.out.println("Game over!");
    }

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

    public static void decreaseHP(double amount) {
        hp -= amount;
        displayHPBox();
        if (hp <= 0) {
            showGameOverScreen();
        }
    }

    private static void displayHPBox() {
        System.out.println("new hp = " + hp);
        // effect + thay đổi GUI ở đây
    }

    public static int getMoney() {
        return money;
    }

    public static void increaseMoney(int amount) {
        money += amount;
        displayMoneyBox();
    }

    public static void decreaseMoney(int amount) {
        money -= amount;
        displayMoneyBox();
    }

    private static void displayMoneyBox() {
        System.out.println("new money = " + money);
        // effect + thay đổi GUI ở đây
    }

    public static void addEnemiesWave() {
        // [Tạo ra lính] ----------------
        for (int i = 0; i < 20; i++) {
            // Enemy minion = new Enemy(-TILE_WIDTH, 720, pathRedEnemy);
            Enemy minion;
            if (i % 3 == 0)
                minion = new NormalEnemy(-TILE_WIDTH, 720);
            else if (i % 3 == 1)
                minion = new SmallerEnemy(-TILE_WIDTH, 720);
            else
                minion = new TankerEnemy(-TILE_WIDTH, 720);
            // minion.scaleTo(70, 70);
            // minion.setSpeed(1.2);
            enemies.add(minion);
            layout.getChildren().add(minion);
        }
    }
}
