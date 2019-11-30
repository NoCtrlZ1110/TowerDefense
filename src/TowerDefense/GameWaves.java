/*
- Giống y hệt GamesWaves_LinkedList nhưng quản lý các EnemiesWave bằng ArrayList
- Không rõ bên nào tốn mem hơn
*/
package TowerDefense;

import javafx.animation.*;
import javafx.util.Duration;

import java.util.ArrayList;

import static TowerDefense.CONSTANT.PREPARE_TIME;
import static TowerDefense.GameField.*;
import static TowerDefense.Shop.*;

public class GameWaves {
    private static final int TIME_BETWEEN_2_WAVES = 2;

    private static int total_waves = 0;
    private static int running_wave_id = 0;
    private ArrayList<EnemiesWave> waves = new ArrayList<>();
    private ArrayList<Enemy> running_wave_enemies = new ArrayList<>();
    private EnemiesWave running_wave;
    private Timeline wavesTimeline = new Timeline();
    private AnimationTimer timer;

    public GameWaves() {
        wavesTimeline.getKeyFrames().add(new KeyFrame(Duration.seconds(4), new KeyValue(road.opacityProperty(), 0)));
        wavesTimeline.getKeyFrames().add(new KeyFrame(Duration.seconds(5), new KeyValue(road.opacityProperty(), 1)));
        wavesTimeline.getKeyFrames().add(new KeyFrame(Duration.seconds(5), event -> isStarted = true));
        wavesTimeline.setCycleCount(1);
    }

    private void setTimer() {
        // [Hiện thanh máu liên tục theo thời gian]
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                /*
                BUG: Hình fire (bullet) và 1 thanh HP xuất hiện ở góc trên trái (0, 0) khi bắn,
                bug chỉ xảy ra với wave != wave cuối
                */
                if (GameField.isGameOver()) {
                    complete();
                } else if (isWaveFinished()) {
                    running_wave_id++;
                    if (running_wave_id < waves.size()) {
                        System.out.println("next wave!");
                        running_wave = waves.get(running_wave_id);
                        running_wave_enemies = running_wave.getEnemies();
                        running_wave.start();
                    } else
                        complete();
                } else {
                    ArrayList<Enemy> _running = new ArrayList<>(running_wave_enemies);
                    _running.forEach(e -> {
                        e.displayHpBar();
                        e.harm();
                    });
                    /*
                    running_wave_enemies = new ArrayList<>();
                    for (Enemy e: _running) {
                        if (e.exists())
                            running_wave_enemies.add(e);
                    }
                    */
                }
                // towers.forEach(Tower::shoot);
                if (!selling) layout.getChildren().remove(using_shovel);
                if (currentItem == 0)
                    layout.getChildren().removeAll(placingTower1, placingTower2, placingTower3);
            }
        };
    }

    public void addEnemiesWave(int total_enemies, String... enemy_type) {
        EnemiesWave wave = new EnemiesWave(
            total_waves == 0 ? PREPARE_TIME : TIME_BETWEEN_2_WAVES,
            total_enemies, enemy_type
        );
        total_waves++;
        waves.add(wave);
    }

    public ArrayList<Enemy> getRunningWaveEnemies() {
        return running_wave_enemies;
    }

    private boolean isWaveFinished() {
        return running_wave_enemies.size() == 0;
    }

    public void removeEnemy(Enemy enemy) {
        running_wave.removeEnemy(enemy);
    }

    public void showWavesBar() {
        // TODO: vẽ WavesBar ở đây, gọi hàm running_wave.getWaveRate() để tính tỉ lệ hoàn thành wave
    }

    public void start() {
        System.out.println("start...");
        System.out.println(layout.getChildren());
        running_wave_id = 0;
        running_wave = waves.get(running_wave_id);
        running_wave_enemies = running_wave.getEnemies();

        setTimer();
        wavesTimeline.play();
        running_wave.start();
        timer.start();
    }

    public void pause() {
        wavesTimeline.pause();
        running_wave.pause();
    }

    public void resume() {
        if (wavesTimeline.getStatus() != Animation.Status.STOPPED)
            wavesTimeline.play();

        running_wave.resume();
    }

    public void complete() {
        timer.stop();
        showCompletedScreen();
    }
}