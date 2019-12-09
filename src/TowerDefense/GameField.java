package TowerDefense;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static TowerDefense.CONSTANT.*;
import static TowerDefense.GameTile.*;
import static TowerDefense.PauseScreen.showPauseBtn;
import static TowerDefense.Shop.*;
import static TowerDefense.Sound.*;

public class GameField {
    static Rectangle border = new Rectangle(BORDER_WIDTH, BORDER_WIDTH);
    private static ArrayList<Tower> towers = new ArrayList<>();
    private static GameWaves game_waves = null;

    private static int money = 20;
    private static double hp_max = 100;
    private static Player user = null;
    public static boolean isPaused = false;
    public static boolean isStarted = false;

    public static Pane layout = new Pane();
    public static Scene gameScene = new Scene(layout, TILE_WIDTH * COL_NUM, TILE_WIDTH * ROW_NUM);

    final static Path path = new Path();
    final static imageObject logo = new imageObject("file:images/transparent_logo.png");
    // final static imageObject road = new imageObject("file:images/road.png");
    static imageObject road = null;
    private static Timeline gameTimeline;
    private static Timeline shootTimeline;
    static int world_select = 0; // = 0;
    // CHỌN WORLD BẰNG CÁCH THAY ĐỔI BIẾN "world_select"

    public static void gameScreen(Stage stage) {
        pauseWelcomeMusic();
        stage.close();

        createNewGame();
        // drawMap();
        //-----------------------------
        imageObject background = new imageObject("file:images/back.png");
        background.setLocation(0, 0);
        background.scaleTo(TILE_WIDTH * COL_NUM, TILE_WIDTH * ROW_NUM);
        logo.setOpacity(0);
        logo.setLocation(430, 250);
        logo.scaleTo(420, 165);
        layout.getChildren().addAll(background, logo, road);
        playGameScreenMusic();
        //--------------------------------
        // Animation ------------------
        gameTimeline = new Timeline();
        gameTimeline.getKeyFrames().add(new KeyFrame(Duration.seconds(2), new KeyValue(logo.opacityProperty(), 0)));
        gameTimeline.getKeyFrames().add(new KeyFrame(Duration.seconds(4), new KeyValue(logo.opacityProperty(), 1)));
        gameTimeline.getKeyFrames().add(new KeyFrame(Duration.seconds(5), new KeyValue(logo.opacityProperty(), 1)));
        gameTimeline.getKeyFrames().add(new KeyFrame(Duration.seconds(6), new KeyValue(logo.opacityProperty(), 0)));
        gameTimeline.getKeyFrames().add(new KeyFrame(Duration.seconds(7), new KeyValue(road.opacityProperty(), 0)));
        gameTimeline.getKeyFrames().add(new KeyFrame(Duration.seconds(8), new KeyValue(road.opacityProperty(), 1)));
        gameTimeline.getKeyFrames().add(new KeyFrame(Duration.seconds(8), event -> showExistedItems()));
        gameTimeline.getKeyFrames().add(new KeyFrame(Duration.seconds(PREPARE_TIME), event -> isStarted = true));
        // gameTimeline.setCycleCount(1);

        // [Shoot timeline]
        shootTimeline = new Timeline(new KeyFrame(Duration.millis(20), event -> {
            if (isStarted)
                towers.forEach(Tower::shoot);
        }));
        shootTimeline.setCycleCount(Animation.INDEFINITE);
        shootTimeline.play();

        //-------------------------------
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
                Shop.showIconWithMouse(event);

                Point location = TowerBuildLocation(event);
                Point point = getLocationFromMouseEvent(event);

                if (location != null) {
                    layout.setCursor(Cursor.HAND);
                    border.setX(location.getX() + 33);
                    border.setY(location.getY() + 33);
                } else if (isTowerPlaced(point)) {
                    layout.setCursor(Cursor.HAND);
                } else {
                    layout.setCursor(Cursor.DEFAULT);
                }
                towers.forEach(t -> {
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
            if (isStarted && !isPaused) {
                Point location = TowerBuildLocation(event);
                if (location != null) {
                    border.setX(location.getX() + 33);
                    border.setY(location.getY() + 33);
                    if (buying) {
                        Shop.buyTowerAt(location.getX(), location.getY());
                        // các dòng hiệu ứng khác đã đưa vào trong hàm trên
                    }
                    selling = false;
                } else {
                    Point checkingPoint = getLocationFromMouseEvent(event);
                    if (isTowerPlaced(checkingPoint)) {
                        // bán/upgrade tháp ở đây
                        // hiệu ứng sẽ là click -> 1 menu ở dưới hiện lên, có upgrade và bán

                        int x = (int) event.getSceneX();
                        int y = (int) event.getSceneY();
                        if (selling) {
                            // bán: bán với giá = x% giá mua (có lẽ chỉ 80% thôi)
                            Shop.sellTowerAt(x, y);
                            // các dòng hiệu ứng khác đã đưa vào trong hàm trên
                        } else {
                            // upgrade: hiện dãy icon đại diện cho tháp
                            // upgrade có thể có giá
                            Tower tower = getTowerPlacedAt(x, y);
                            if (tower != null)
                                tower.upgrade();
                        }
                    } else if (isRoadPlaced(checkingPoint)) {
                        Shop.cancelBuying();
                        Shop.cancelSelling();
                    }
                }
            }
        });

        gameScene.setOnKeyPressed(ke -> {
            if (ke.getCode() == KeyCode.ESCAPE) {
                // System.out.println("escape!");
                if (selling) {
                    // huỷ bán
                    layout.setCursor(Cursor.DEFAULT);
                    cancelSelling();
                } else if (buying) {
                    // huỷ mua
                    layout.setCursor(Cursor.DEFAULT);
                    cancelBuying();
                }
            }
        });

        // ------------------------
        showShopBar();
        gameTimeline.play();
        // ------------------------

        // [Thêm icon cho game] ---
        stage.getIcons().add(new Image("file:images/love.jpg"));
        stage.setTitle("Tower Defense 2.0");
        stage.setScene(gameScene);
        stage.centerOnScreen();
        stage.show();
    }

    public static void showCompletedScreen() {
        // stopGame(); // đã stop trước khi showCompletedScreen()
        winMusic();
        System.out.println("You have cleared this map!");
    }

    public static void showGameOverScreen() {
        stopGame();
        System.out.println("Game over!");
    }

    public static void addTower(Tower tower) {
        towers.add(tower);
    }

    public static void removeTower(Tower tower) {
        towers.remove(tower);
    }

    public static Tower getTowerPlacedAt(int x, int y) {
        // x = x - x % TILE_WIDTH;
        // y = y - y % TILE_WIDTH;

        for (Tower t: towers) {
            if (t.isInTower(x, y)) {
                return t;
            }
        }
        return null;
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

    public static void saveGame() {
        if (isPaused) {
            System.out.println("saving...");
            try {
                Date date_now = new Date();
                long time_now = date_now.getTime();

                FileWriter fo = new FileWriter(String.format("saved_game/save_%d.txt", time_now));
                fo.write(String.format("USER: money=%d,%s\n", money, user.toString()));
                fo.write(String.format("WORLD: %d\n", world_select));
                fo.write("TOWERS:\n");
                for (Tower t : towers)
                    fo.write(t.toString() + "\n");

                fo.write("GAME PROGRESS:\n");
                fo.write(game_waves.toString() + "\n");
                fo.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean loadGame() {
        try {
            System.out.println("loading...");

            FileChooser fileChooser = new FileChooser();
            configureFileChooser(fileChooser);
            Platform.setImplicitExit(false); // https://stackoverflow.com/a/21308629
            File chosen_file = fileChooser.showOpenDialog(SelectRoad.getMasterWindow());
            Platform.setImplicitExit(true); // chống lag
            // File chosen_file = new File("saved_game/save.txt");
            // System.out.println(chosen_file);
            if (chosen_file == null) // thoát select file mà không chọn
                return false;

            Scanner fi = new Scanner(chosen_file);
            StringBuilder temp = new StringBuilder();
            while (fi.hasNextLine()) {
                String line = fi.nextLine() + "\n";
                temp.append(line);
            }
            String content = temp.toString();
            String[] splited = content.split("\n?(WORLD|TOWERS|GAME PROGRESS):\\s");
            /*
            splited[0]: USER: money=130,hp=100.000000,hp_max=100.000000
            splited[1]: world_select
            splited[2]: towers
            splited[3]: waves
            */
            if (!splited[3].startsWith("COMPLETED")) {
                Matcher user_matcher = Pattern.compile("USER: money=(.+),hp=(.+),hp_max=(.+)").matcher(splited[0]);
                if (user_matcher.find()) {
                    money = Integer.parseInt(user_matcher.group(1));
                    double hp = Double.parseDouble(user_matcher.group(2));
                    hp_max = Double.parseDouble(user_matcher.group(3));
                    user = new Player(hp, hp_max);
                    // System.out.println(money + " " + hp + " " + hp_max);
                }
                world_select = Integer.parseInt(splited[1]);
                // System.out.println(world_select);
                World.setupWorld();

                String[] towers_str = splited[2].split("\n");
                for (String tower_str: towers_str) {
                    Tower tower = Tower.loadFromString(tower_str);

                    towers.add(tower);
                    // System.out.println(tower);
                    // tower.show();
                }
                game_waves = new GameWaves(splited[3]);
                // game_waves.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (game_waves != null);
    }

    private static void configureFileChooser(FileChooser f) {
        f.setTitle("Open saved game");
        f.setInitialDirectory(new File("saved_game/"));
        f.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Text file", "*.txt")
        );
    }

    private static void showExistedItems() {
        user.show();

        for (Tower tower: towers)
            tower.show();

        game_waves.show();
    }

    public static void pauseGame() {
        isPaused = true;

        gameScreenMusicTimeline.pause();
        gameTimeline.pause();
        shootTimeline.pause();
        if (game_waves != null)
            game_waves.pause();
    }

    public static void resumeGame() {
        isPaused = false;

        if (gameScreenMusicTimeline.getStatus() != Animation.Status.STOPPED)
            gameScreenMusicTimeline.play();
        if (gameTimeline.getStatus() != Animation.Status.STOPPED)
            gameTimeline.play();
        if (shootTimeline.getStatus() != Animation.Status.STOPPED)
            shootTimeline.play();
        if (game_waves != null)
            game_waves.resume();
    }

    public static void stopGame() {
        isPaused = true;

        gameScreenMusicTimeline.stop();
        gameTimeline.stop();
        shootTimeline.stop();
        if (game_waves != null)
            game_waves.stop();
    }

    private static void createNewGame() {
        if (game_waves == null) { // chưa được load
            World.setupWorld();

            user = new Player(hp_max, hp_max);

            game_waves = new GameWaves();
            game_waves.addEnemiesWave(15, "normal");
            game_waves.addEnemiesWave(15, "smaller");
            game_waves.addEnemiesWave(15, "normal", "smaller");
            game_waves.addEnemiesWave(15, "normal", "tanker");
            game_waves.addEnemiesWave(10, "tanker");
            game_waves.addEnemiesWave(1, "boss");
        }
        user.show();
        game_waves.start();
    }
}
