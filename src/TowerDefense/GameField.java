package TowerDefense;

import javafx.animation.*;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    static Rectangle border = new Rectangle(100, 100);
    static ArrayList<Tower> towers = new ArrayList<>();
    static ArrayList<Enemy> enemies = new ArrayList<>();

    static int money;
    static int hp = 100;
    private static boolean is_paused = false;

    public static Pane layout = new Pane();
    public static Pane upperLayout = new Pane();

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
                }), new KeyFrame(Duration.millis(850), event -> {
            imageObject logoScr = new imageObject("file:images/logo.png");
            logoScr.scaleTo(960, 540);
            pane.getChildren().add(logoScr);


        }
        ),
                new KeyFrame(Duration.seconds(2), event -> {
                    welcomScr.setOpacity(0);
                    pane.getChildren().add(welcomScr);
                    FadeTransition ft = new FadeTransition(Duration.millis(2000), welcomScr);
                    ft.setFromValue(0);
                    ft.setToValue(1);
                    ft.play();
                }
                ),

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


                }
                )
        );

        stage.setTitle("Tower Defense 1.2");

        stage.setScene(scene);
        stage.getIcons().add(new Image("file:images/love.jpg"));
        stage.setResizable(true);
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


        // [Vẽ ra map] -------------------

        imageObject background = new imageObject("file:images/back.png");
        background.setLocation(0, 0);
        background.scaleTo(TILE_WIDTH * COL_NUM, TILE_WIDTH * ROW_NUM);
        layout.getChildren().add(background);
        imageObject[][] tiled = new imageObject[ROW_NUM][COL_NUM];

        for (int i = 0; i < ROW_NUM; i++)
            for (int j = 0; j < COL_NUM; j++) {
                tiled[i][j] = new imageObject(pathTile + getTileType(i, j) + ".png");
                tiled[i][j].setFitHeight(TILE_WIDTH);
                tiled[i][j].setFitWidth(TILE_WIDTH);
                tiled[i][j].setLocation(j * TILE_WIDTH, i * TILE_WIDTH);
                layout.getChildren().add(tiled[i][j]);
            }
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
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(PREPARE_TIME - 2), event -> combatMusic()));

        for (int i = 0; i < enemies.size(); i++) {
            Enemy e = enemies.get(i);
            KeyFrame moveEnemy = new KeyFrame(Duration.millis(i * 800 + PREPARE_TIME * 1000), event -> e.move(path));
            timeline.getKeyFrames().add(moveEnemy);
        }
        //-----------------------------

        // [Hiện thanh máu liên tục theo thời gian]
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                enemies.forEach(Enemy::showHP);

//                towers.forEach(Tower::shoot);
            }
        };



        Timeline shootTimeLine = new Timeline(new KeyFrame(Duration.millis(20), event -> {

            towers.forEach(Tower::shoot);
        }));
        shootTimeLine.setCycleCount(Animation.INDEFINITE);
        shootTimeLine.play();

        // [?] tại sao cái timer này ko gộp với timeline ở trên?
        // => showHP gộp vào 1 hàm show duy nhất?

        // [Hiện khung chọn vị trí xây tháp] ---

        border.setStroke(Color.WHITESMOKE);
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

            Point point = new Point((int) event.getSceneX() / TILE_WIDTH, (int) event.getSceneY() / TILE_WIDTH);
            //System.out.println(point);
            if (getMapType(point.getX(), point.getY()).equals("6"))
                towers.forEach(t -> {
                    if (Math.abs(point.getX() * TILE_WIDTH - t.getPosition().getX()) <= TILE_WIDTH &&
                            Math.abs(point.getY() * TILE_WIDTH - t.getPosition().getY()) <= TILE_WIDTH)
                        t.showRange();
                    else if (layout.getChildren().contains(t.rangeCircle))
                        layout.getChildren().remove(t.rangeCircle);
                });
            else towers.forEach(t -> {
                if (layout.getChildren().contains(t.rangeCircle))
                    layout.getChildren().remove(t.rangeCircle);
            });
            location = null; // giải phóng bộ nhớ
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
                    upgradeTower();
                }
                System.out.println("waiting for being sold...");
            }
        });

        imageObject pauseImage = new imageObject("file:images/pause.png");

        pauseImage.scaleTo(70, 70);
        Button pauseBtn = new Button("");
        pauseBtn.setLayoutX(1200);
        pauseBtn.setLayoutY(50);
        pauseBtn.setMaxWidth(70);
        pauseBtn.setMaxHeight(70);
        pauseBtn.setGraphic(pauseImage);
        pauseBtn.setOnAction(event -> {
            System.out.println("pause...");
            pauseScreen(stage);
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
        upperLayout = new Pane();
        // pausescreen on top
        imageObject background = new imageObject("file:images/black_background.png");
        upperLayout.getChildren().add(background);

        // layout.getChildren().remove(background);
    }
    // -------------------------

    public static void buyTower() {
        money -= 10;
    }

    public static void sellTower() {
        money += 8;
    }

    public static void placeTower(Point location) {

        imageObject building = new imageObject("file:images/white_building.gif");
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(0), event -> {
                    buildingSound();
                    building.scaleTo(80, 80);
                    building.setLocation(location.getX() + 40, location.getY() + 40);
                    layout.getChildren().add(building);
                    setMapType(location.getX() / TILE_WIDTH, location.getY() / TILE_WIDTH, 6);
                    setMapType(location.getX() / TILE_WIDTH, location.getY() / TILE_WIDTH + 1, 6);
                    setMapType(location.getX() / TILE_WIDTH + 1, location.getY() / TILE_WIDTH, 6);
                    setMapType(location.getX() / TILE_WIDTH + 1, location.getY() / TILE_WIDTH + 1, 6);
                }), new KeyFrame(Duration.millis(1800), event ->
        {
            layout.getChildren().remove(building);
            Tower tower = new Tower("file:images/Archer_Tower17.png");
            tower.setScale(TILE_WIDTH * 2, TILE_WIDTH * 2);
            towers.add(tower);
            tower.showTower(location);
            // tower.showRange();
        }));
        timeline.play();


    }

    public static void removeTower() {
        // Java ko có từ khoá pass, buồn :(
        // chưa nghĩ ra đặt cái gì vào argument :(
    }

    public static void upgradeTower() {
    }

    public static void addEnemiesWave() {
        // [Tạo ra lính] ----------------
        for (int i = 0; i < 20; i++) {
            Enemy minion = new Enemy(-TILE_WIDTH, 720, pathRedEnemy);
            minion.setFitHeight(70);
            minion.setFitWidth(70);
            minion.setSpeed(1.2);
            enemies.add(minion);
            layout.getChildren().add(minion);
        }
    }
}
