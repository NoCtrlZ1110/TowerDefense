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
import static TowerDefense.Shop.*;
import static TowerDefense.Sound.*;

public class GameField {
    static Rectangle border = new Rectangle(BORDER_WIDTH, BORDER_WIDTH);
    static ArrayList<Tower> towers = new ArrayList<>();
    static ArrayList<Enemy> enemies = new ArrayList<>();

    static int money = 1000;
    static int hp = 100;
    private static boolean is_paused = false;

    public static Pane layout = new Pane();

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
                    //showMuteBtn(pane);
                }),
                new KeyFrame(Duration.seconds(2), event -> {
                    welcomScr.setOpacity(0);
                    muteBtn.setOpacity(0);
                    speakerBtn.setOpacity(0);
                    pane.getChildren().add(welcomScr);

                    FadeTransition ft = new FadeTransition(Duration.millis(2000), welcomScr);
                    FadeTransition ft2 = new FadeTransition(Duration.millis(2000), muteBtn);
                    FadeTransition ft3 = new FadeTransition(Duration.millis(2000), speakerBtn);

                    ft.setFromValue(0);
                    ft.setToValue(1);
                    ft.play();
                    ft2.setFromValue(0);
                    ft2.setToValue(1);
                    ft2.play();
                    ft3.setFromValue(0);
                    ft3.setToValue(1);
                    ft3.play();
                    showMuteBtn(pane);
                }),
                new KeyFrame(Duration.seconds(3), event -> {
                    imageObject startBtn = new imageObject("file:images/startBtn.png");
                    startBtn.setLocation(73, 437);
                    startBtn.scaleTo(184, 56);
                    startBtn.setOpacity(0);
                    pane.getChildren().add(startBtn);
                    showMuteBtn(pane);


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

        stage.setTitle("Tower Defense 1.5");

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
        Scene gameScene = new Scene(layout, TILE_WIDTH * COL_NUM, TILE_WIDTH * ROW_NUM);

        // [Vẽ ra map] -------------------

        imageObject background = new imageObject("file:images/back.png");
        background.setLocation(0, 0);
        background.scaleTo(TILE_WIDTH * COL_NUM, TILE_WIDTH * ROW_NUM);
        layout.getChildren().add(background);
        imageObject[][] tiled = new imageObject[ROW_NUM][COL_NUM];

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
                enemies.forEach(e -> {
                    e.showHP();
                    e.harm();
                });
                // towers.forEach(Tower::shoot);
                if (!selling) layout.getChildren().remove(using_shovel);
                coin.setText(Integer.toString(money));
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

        showMuteBtn(layout);


        layout.setOnMouseMoved(event -> {
            Point location = TowerBuildLocation(event);
            Point point = getLocationFromMouseEvent(event);

            if (selling) {
                if (!layout.getChildren().contains(using_shovel)) layout.getChildren().add(using_shovel);
                using_shovel.setLocation((int)event.getSceneX()-5,(int) event.getSceneY()-82);
            }
            else
            {
                layout.getChildren().remove(using_shovel);

            }

            if (location != null) {
                layout.setCursor(Cursor.HAND);
                border.setX(location.getX() + 33);
                border.setY(location.getY() + 33);
            } else if (isTowerPlaced(point)) {
                layout.setCursor(Cursor.HAND);
            } else {
                layout.setCursor(Cursor.DEFAULT);
            }
            // System.out.println(point);
            if (isTowerPlaced(point))
                towers.forEach(t -> {
                    if (t.isInTower((int) event.getSceneX(), (int) event.getSceneY()))
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
            if (location != null) {
                border.setX(location.getX() + 33);
                border.setY(location.getY() + 33);
                // mua: hiện dãy icon đại diện cho tháp
//                System.out.println("clicked");
                // => chọn loại tower (thêm tham số, có thể là string)
                if (!selling) buyTowerAt(location);
                selling = false;
            } else
            {
                Point checkingPoint = getLocationFromMouseEvent(event);
                if (isTowerPlaced(checkingPoint)) {
                    // bán/upgrade tháp ở đây
                    // hiệu ứng sẽ là click -> 1 menu ở dưới hiện lên, có upgrade và bán

                    int x = (int) event.getSceneX();
                    int y = (int) event.getSceneY();
                    // boolean is_sell_chosen = true;// --->>> tôi sử dụng biến selling nhé :>
                    if (selling) {
                        // bán: bán với giá = x% giá mua (có lẽ chỉ 80% thôi)
                        sellTowerAt(x, y);
                        selling = false;
                    } else {

                        // upgrade: hiện dãy icon đại diện cho tháp
                        // upgrade có thể có giá
                        upgradeTowerAt(x, y);
                    }
                } else if (isRoadPlaced(checkingPoint)) selling = false;
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

        showShopBar();

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
        if (hp <= 0) {
            System.out.println("Game over!");
        }
    }

    public static Tower findTowerAt(int x, int y) {
        for (Tower t : towers) {
            if (t.isInTower(x, y)) {
                return t;
            }
        }
        return null;
    }

    public static void buyTowerAt(Point location) {
        // Tower tower = new Tower("file:images/Tower.png");
        Tower tower = new Tower("file:images/Archer_Tower17.png");
        if (money >= tower.getPrice()) {
            money -= tower.getPrice();
            placeTower(tower, location);
        }
    }

    public static void sellTowerAt(int x, int y) {
        Tower tower = findTowerAt(x, y);
        if (tower != null) {
            money += (int) (tower.getPrice() * SELL_RATE);
            towers.remove(tower);
            tower.destroy();
        }
    }

    public static void placeTower(Tower tower, Point location) {
        imageObject building = new imageObject("file:images/white_building.gif");
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(0), event -> {
                    buildingSound();
                    building.scaleTo(TILE_WIDTH, TILE_WIDTH);
                    building.setLocation(location.getX() + TILE_WIDTH / 2, location.getY() + TILE_WIDTH / 2);
                    layout.getChildren().add(building);
                    setMapType(location.getX() / TILE_WIDTH, location.getY() / TILE_WIDTH, 6);
                    setMapType(location.getX() / TILE_WIDTH, location.getY() / TILE_WIDTH + 1, 6);
                    setMapType(location.getX() / TILE_WIDTH + 1, location.getY() / TILE_WIDTH, 6);
                    setMapType(location.getX() / TILE_WIDTH + 1, location.getY() / TILE_WIDTH + 1, 6);

                    tower.setPosition(location);
                }), new KeyFrame(Duration.millis(1800), event -> {
            layout.getChildren().remove(building);
            tower.showTower();
            // tower.showRange();
            towers.add(tower);
        }
        ));
        timeline.play();
    }

    public static void upgradeTowerAt(int x, int y) {
        Tower tower = findTowerAt(x, y);
        if (tower != null) {
            // money -= ...;
//            System.out.println("I'm waiting for you...");
        }
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
