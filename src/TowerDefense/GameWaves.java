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
                if (running_wave.isFinished()) {
                    running_wave_id++;
                    if (running_wave_id < waves.size()) {
                        System.out.println("next wave!");
                        running_wave = waves.get(running_wave_id);
                        running_wave.start();
                    } else
                        complete();
                } else {
                    running_wave.getEnemies().forEach(e -> {
                        e.displayHpBar();
                        e.harm();
                    });
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
        return running_wave.getEnemies();
    }

    public void removeEnemy(Enemy enemy) {
        running_wave.removeEnemy(enemy);
    }

    public void showWavesBar() {
        // TODO: vẽ WavesBar ở đây, gọi hàm getWaveRate() để tính tỉ lệ hoàn thành wave
    }

    public void start() {
        System.out.println("start...");
        running_wave_id = 0;
        running_wave = waves.get(running_wave_id);

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
        wavesTimeline.play();
        running_wave.resume();
    }

    public void complete() {
        timer.stop();
        showCompletedScreen();
    }
}