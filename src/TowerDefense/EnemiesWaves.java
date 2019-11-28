package TowerDefense;

import javafx.animation.*;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;

import static TowerDefense.CONSTANT.PREPARE_TIME;
import static TowerDefense.CONSTANT.TILE_WIDTH;
import static TowerDefense.GameField.*;
import static TowerDefense.GameField.path;
import static TowerDefense.Shop.*;
import static TowerDefense.Shop.placingTower3;

public class EnemiesWaves {
    private static final int TIME_BETWEEN_2_WAVES = 2;

    private static int total_waves = 0;
    private static int total_ms_before = 0;
    private int countWaveTotalEnemies = 0;
    private int countWaveCreatedEnemies = 0;
    // private ArrayList<EnemiesWave> waves = new ArrayList<EnemiesWave>();
    private ArrayList< ArrayList<Enemy> > enemies = new ArrayList<>();
    private ArrayList<Enemy> running_wave_enemies = new ArrayList<>();
    private int running_wave_enemies_id;
    private Timeline wavesTimeline = new Timeline();
    private AnimationTimer timer;

    public EnemiesWaves() {
        wavesTimeline.getKeyFrames().add(new KeyFrame(Duration.seconds(4), new KeyValue(road.opacityProperty(), 0)));
        wavesTimeline.getKeyFrames().add(new KeyFrame(Duration.seconds(5), new KeyValue(road.opacityProperty(), 1)));
        wavesTimeline.getKeyFrames().add(new KeyFrame(Duration.seconds(5), event -> isStarted = true));
        total_ms_before = PREPARE_TIME * 1000;
    }

    private void setTimer() {
        // [Hiện thanh máu liên tục theo thời gian]
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                // System.out.println(running_wave_enemies_id);
                running_wave_enemies.forEach(e -> {
                    e.displayHpBar();
                    e.harm();
                });
                // towers.forEach(Tower::shoot);
                if (!selling) layout.getChildren().remove(using_shovel);
                // coin.setText(Integer.toString(money)); // đã update coin mỗi khi biến động money
                if (currentItem == 0)
                    layout.getChildren().removeAll(placingTower1, placingTower2, placingTower3);
            }
        };
    }

    public void addEnemiesWave(int total_enemies, String... enemy_type) {
        if (total_waves > 0)
            addDelay();

        total_waves++;
        addEnemies(total_enemies, enemy_type);
    }

    private void addEnemies(int total_enemies, String... enemy_type) {
        countWaveCreatedEnemies = 0;
        countWaveTotalEnemies = total_enemies;
        ArrayList<Enemy> new_enemies = new ArrayList<>();

        Random randomizer = new Random();
        boolean has_boss_type = false;
        for (String t: enemy_type)
            if (t.equals("boss")) {
                has_boss_type = true;
                break;
            }

        for (int i = 1; i <= total_enemies; i++) {
            // Enemy minion = new Enemy(-TILE_WIDTH, 720, pathRedEnemy);
            Enemy minion;
            while (true) {
                int idx = randomizer.nextInt(enemy_type.length);
                boolean is_boss_type = enemy_type[idx].equals("boss");
                // if ((i == total_enemies && is_boss_type) || (i < total_enemies && !is_boss_type)) {
                if (!has_boss_type || (i == total_enemies) == is_boss_type) {
                    minion = Enemy.generateEnemyByType(enemy_type[idx], -TILE_WIDTH, 720);
                    break;
                }
            }
            // minion.scaleTo(70, 70);
            // minion.setSpeed(1.2);
            new_enemies.add(minion);
            layout.getChildren().add(minion);

            countWaveCreatedEnemies++;
        }
        // System.out.println(total_enemies + " " + total_waves + " " + new_enemies);
        addTimelineEvents(new_enemies);
        enemies.add(new_enemies);
    }

    private void addTimelineEvents(ArrayList<Enemy> new_enemies) {
        for (int i = 0; i < new_enemies.size(); i++) {
            Enemy e = new_enemies.get(i);
            KeyFrame moveEnemy = new KeyFrame(
                Duration.millis(total_ms_before + i * 800),
                event -> e.move(path)
            );
            wavesTimeline.getKeyFrames().add(moveEnemy);
        }
        total_ms_before += (800 * new_enemies.size() * (new_enemies.size() - 1)) >> 1;
    }

    public void addDelay() {
        wavesTimeline.getKeyFrames().add(new KeyFrame(
            Duration.millis(total_ms_before + TIME_BETWEEN_2_WAVES * 1000),
            event -> {
                System.out.println("delaying...");
                running_wave_enemies_id++;
                running_wave_enemies = enemies.get(running_wave_enemies_id);
            })
        );
        total_ms_before += TIME_BETWEEN_2_WAVES * 1000;
    }

    public ArrayList<Enemy> getRunningWaveEnemies() {
        return running_wave_enemies;
    }

    public void removeEnemy(Enemy enemy) {
        running_wave_enemies.remove(enemy);
    }

    public double getWaveRate() {
        return countWaveCreatedEnemies / (countWaveTotalEnemies * 1.0);
    }

    public void showWavesBar() {
        // TODO: vẽ WavesBar ở đây, gọi hàm getWaveRate() để tính tỉ lệ hoàn thành wave
    }

    public void start() {
        System.out.println("start...");
        running_wave_enemies_id = 0;
        running_wave_enemies = enemies.get(running_wave_enemies_id);
        setTimer();
        wavesTimeline.play();
        timer.start();

        wavesTimeline.setOnFinished(event -> showCompletedScreen());
    }

    public void pause() {
        wavesTimeline.pause();
        running_wave_enemies.forEach(Enemy::pauseMoving);
    }

    public void resume() {
        if (wavesTimeline.getStatus() != Animation.Status.STOPPED)
            wavesTimeline.play();

        running_wave_enemies.forEach(Enemy::resumeMoving);
    }
}