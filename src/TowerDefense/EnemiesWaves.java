package TowerDefense;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Arrays;

public class EnemiesWaves {
    // private ArrayList<EnemiesWave> waves = new ArrayList<EnemiesWave>();
    private int TIME_BETWEEN_2_WAVES = 2;

    public EnemiesWaves(EnemiesWave... waves) {
        // TODO: chỉnh sửa logic ở cẩ EnemiesWave
        for (EnemiesWave wave: waves) {
            wave.start();
            wave.moveEnemyTimeline.setOnFinished(event -> {
                // Timeline delayTimeline = new Timeline();
                // delayTimeline.getKeyFrames().add(new KeyFrame(Duration.seconds(TIME_BETWEEN_2_WAVES), event -> {}));
                try {
                    Thread.sleep(TIME_BETWEEN_2_WAVES * 1000);
                } catch (InterruptedException e) {
                    // nothing
                }
            });
        }
    }

    public void showWavesBar() {
        // TODO: vẽ WavesBar ở đây, gọi hàm getWaveRate() để tính tỉ lệ hoàn thành wave
    }

    public void pause() {

    }
}
