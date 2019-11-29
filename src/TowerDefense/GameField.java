package TowerDefense;

import javafx.animation.*;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

import static TowerDefense.CONSTANT.*;
import static TowerDefense.GameTile.*;
import static TowerDefense.PauseScreen.showPauseBtn;
import static TowerDefense.Shop.*;
import static TowerDefense.Sound.*;

public class GameField {
    static Rectangle border = new Rectangle(BORDER_WIDTH, BORDER_WIDTH);
    private static ArrayList<Tower> towers = new ArrayList<>();
    private static GameWaves game_waves;

    private static int money = 100;
    private static final double HP_MAX = 100;
    private static GameCharacter user;
    public static boolean isPaused = false;
    public static boolean isStarted = false;

    public static Pane layout = new Pane();

    // thêm property stage: Stage

    final static Path path = new Path();
    final static imageObject road = new imageObject("file:images/road.png");
    static Timeline shootTimeLine;

    public static void gameScreen(Stage stage) {
        pauseWelcomeMusic();
        stage.close();
        Scene gameScene = new Scene(layout, TILE_WIDTH * COL_NUM, TILE_WIDTH * ROW_NUM);

        imageObject background = new imageObject("file:images/back.png");
        background.setLocation(0, 0);
        background.scaleTo(TILE_WIDTH * COL_NUM, TILE_WIDTH * ROW_NUM);
        road.setOpacity(0);
        layout.getChildren().addAll(background, road);
        playGameScreenMusic();

        user = new GameCharacter(HP_MAX, 200, 10, 190, 50);
        user.displayHpBar();
        // drawMap();
        //--------------------------------

        // [Tạo đường đi cho lính] -------

        path.getElements().add(new MoveTo(-TILE_WIDTH, 760));

        for (int i = 0; i < ROAD_NUM; i++)
            path.getElements().add(new LineTo(roadLocation[i][0], roadLocation[i][1]));

        //-------------------------------
        runEnemiesWaves();

        //-----------------------------
        // [Shoot timeline]
        shootTimeLine = new Timeline(new KeyFrame(Duration.millis(20), event -> {
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
        showPauseBtn();

        layout.setOnMouseMoved(event -> {
            if (isStarted && !isPaused) {
                Point location = TowerBuildLocation(event);
                Point point = getLocationFromMouseEvent(event);

                if (currentItem == 0) {
                    layout.getChildren().removeAll(placingTower1, placingTower2, placingTower3);
                } else if (currentItem == 1) {
                    if (!layout.getChildren().contains(placingTower1))
                        layout.getChildren().add(placingTower1);
                    placingTower1.setLocation((int) event.getSceneX(), (int) event.getSceneY());

                } else if (currentItem == 2) {
                    if (!layout.getChildren().contains(placingTower2))
                        layout.getChildren().add(placingTower2);
                    placingTower2.setLocation((int) event.getSceneX(), (int) event.getSceneY());

                } else if (currentItem == 3) {
                    if (!layout.getChildren().contains(placingTower3))
                        layout.getChildren().add(placingTower3);
                    placingTower3.setLocation((int) event.getSceneX(), (int) event.getSceneY());
                }

                if (selling) {
                    if (!layout.getChildren().contains(using_shovel)) layout.getChildren().add(using_shovel);
                    using_shovel.setLocation((int) event.getSceneX() - 5, (int) event.getSceneY() - 82);
                } else {
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
                towers.forEach(t -> {
                    // if (isTowerPlaced(point) && t.isInTower((int)event.getSceneX(), (int)event.getSceneY()))
                    if (t.isInTower((int) event.getSceneX(), (int) event.getSceneY()))
                        t.showRange();
                    else
                        t.removeRange();
                });
            }
        });

        layout.setOnMouseClicked(event -> {
            // nếu vị trí click có tháp -> bán/upgrade
            //                  ko có tháp -> mua
            if (!isPaused && isStarted) { // isStarted &&
                Point location = TowerBuildLocation(event);
                if (location != null) {
                    border.setX(location.getX() + 33);
                    border.setY(location.getY() + 33);
                    // mua: hiện dãy icon đại diện cho tháp
                    // => chọn loại tower (thêm tham số, có thể là string)
                    if (!selling) {
                        Roadside r = new Roadside(location.getX(), location.getY());
                        if (currentItem == 1) {
                            r.buyTower("normal");
                            currentItem = 0;
                            selectedItem.setVisible(false);
                        } else if (currentItem == 2) {
                            r.buyTower("sniper");
                            currentItem = 0;
                            selectedItem.setVisible(false);
                        } else if (currentItem == 3) {
                            r.buyTower("machinegun");
                            currentItem = 0;
                            selectedItem.setVisible(false);
                        }
                    }
                    selling = false;
                } else {
                    Point checkingPoint = getLocationFromMouseEvent(event);
                    if (isTowerPlaced(checkingPoint)) {
                        // bán/upgrade tháp ở đây
                        // hiệu ứng sẽ là click -> 1 menu ở dưới hiện lên, có upgrade và bán

                        int x = (int) event.getSceneX();
                        int y = (int) event.getSceneY();
                        Roadside r = new Roadside(x, y);
                        if (selling) {
                            // bán: bán với giá = x% giá mua (có lẽ chỉ 80% thôi)
                            r.sellPlacedTower();
                            selling = false;
                        } else {
                            // upgrade: hiện dãy icon đại diện cho tháp
                            // upgrade có thể có giá
                            r.upgradePlacedTower();
                            selling = false;
                        }
                    } else if (isRoadPlaced(checkingPoint))
                        selling = false;
                }
            }
        });

        // ------------------------
        showShopBar();
        // ------------------------
        // [Thêm icon cho game] ---
        stage.getIcons().add(new Image("file:images/love.jpg"));
        stage.setScene(gameScene);
        stage.centerOnScreen();
        stage.show();
    }

    public static void showCompletedScreen() {
        pauseGame(); // đề phòng các timer vẫn chạy, dễ sinh lỗi do running_wave = null
        System.out.println("You have cleared this map!");
    }

    public static void showGameOverScreen() {
        pauseGame();
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

    public static ArrayList<Tower> getTowers() {
        return towers;
    }

    public static void addTower(Tower tower) {
        towers.add(tower);
    }

    public static void removeTower(Tower tower) {
        towers.remove(tower);
    }

    public static ArrayList<Enemy> getEnemies() {
        return game_waves.getRunningWaveEnemies();
    }

    public static void removeEnemy(Enemy enemy) {
        game_waves.removeEnemy(enemy);
    }

    public static void decreaseUserHP(double amount) {
        user.decreaseHP(amount);
        user.displayHpBar();
        if (user.isDead()) {
            showGameOverScreen();
        }
    }

    public static boolean isGameOver() {
        return user.isDead();
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

    public static void displayMoneyBox() {
        // System.out.println("new money = " + money);
        coin.setText(Integer.toString(money));
    }

    private static void saveGame() {
        if (isPaused) {
            try {
                FileWriter fo = new FileWriter("save.txt");
                fo.write("MAP: <map directory>\n");
                fo.write("TOWERS: <all exists towers' properties, using toString>\n");
                fo.write("GAME PROGRESS: <all waves + enemies' properties, using toString>\n");
                fo.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void loadGame() {
        try {
            File file = new File("save.txt");
            Scanner fi = new Scanner(file);

            while (fi.hasNextLine()) {
                String line = fi.nextLine() + "\n";
                // code xử lí ở đây
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void pauseGame() {
        isPaused = true;

        gameScreenMusicTimeline.pause();
        shootTimeLine.pause();
        game_waves.pause();
    }

    public static void resumeGame() {
        isPaused = false;

        if (gameScreenMusicTimeline.getStatus() != Animation.Status.STOPPED)
            gameScreenMusicTimeline.play();
        shootTimeLine.play();
        game_waves.resume();
    }

    public static void runEnemiesWaves() {
        game_waves = new GameWaves();
        game_waves.addEnemiesWave(10, "normal");
        game_waves.addEnemiesWave(10, "smaller");
        game_waves.addEnemiesWave(15, "normal", "smaller");
        game_waves.start();
    }
}
