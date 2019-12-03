package TowerDefense;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;

import static TowerDefense.CONSTANT.TILE_WIDTH;
import static TowerDefense.GameField.layout;
import static TowerDefense.GameField.path;

public class EnemiesWave {
    private int total_ms_before;
    private ArrayList<Enemy> enemies;
    private int countWaveTotalEnemies;
    private int countWaveCreatedEnemies;
    private Timeline waveTimeline = new Timeline();

    public EnemiesWave(int pre_sec_delay, int total_enemies, String... enemy_type) {
        total_ms_before = pre_sec_delay * 1000;
        countWaveTotalEnemies = total_enemies;
        generateEnemies(enemy_type);
    }

    private void generateEnemies(String... enemy_type) {
        countWaveCreatedEnemies = 0;
        enemies = new ArrayList<>();

        Random randomizer = new Random();
        boolean has_boss_type = false;
        for (String t: enemy_type)
            if (t.equals("boss")) {
                has_boss_type = true;
                break;
            }

        for (int i = 1; i <= countWaveTotalEnemies; i++) {
            // Enemy minion = new Enemy(-TILE_WIDTH, 720, pathRedEnemy);
            Enemy minion;
            while (true) {
                int idx = randomizer.nextInt(enemy_type.length);
                boolean is_boss_type = enemy_type[idx].equals("boss");
                // if ((i == total_enemies && is_boss_type) || (i < total_enemies && !is_boss_type)) {
                if (!has_boss_type || (i == countWaveTotalEnemies) == is_boss_type) {
                    minion = Enemy.generateEnemyByType(enemy_type[idx], -TILE_WIDTH, 720);
                    break;
                }
            }
            // minion.scaleTo(70, 70);
            // minion.setSpeed(1.2);
            enemies.add(minion);
            layout.getChildren().add(minion);

            countWaveCreatedEnemies++;
        }
        // System.out.println(total_enemies + " " + total_waves + " " + enemies);
        addTimelineEvents();
    }

    private void addTimelineEvents() {
        for (int i = 0; i < enemies.size(); i++) {
            Enemy e = enemies.get(i);
            KeyFrame moveEnemy = new KeyFrame(
                Duration.millis(total_ms_before + i * 800),
                event -> e.move(path)
            );
            waveTimeline.getKeyFrames().add(moveEnemy);
        }
    }

    public boolean isFinished() {
        return enemies.size() == 0;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public void removeEnemy(Enemy enemy) {
        enemies.remove(enemy);
    }

    public double getWaveRate() {
        return countWaveCreatedEnemies / (countWaveTotalEnemies * 1.0);
    }

    public void start() {
        waveTimeline.play();
    }

    public void pause() {
        waveTimeline.pause();
        enemies.forEach(Enemy::pauseMoving);
    }

    public void resume() {
        if (waveTimeline.getStatus() != Animation.Status.STOPPED)
            waveTimeline.play();

        enemies.forEach(Enemy::resumeMoving);
    }

    public void stop() {
        waveTimeline.stop();
    }

    public String toString() {
        StringBuilder res = new StringBuilder();
        if (!isFinished()) {
            for (Enemy e: enemies)
                res.append(e.toString()).append("\n");
        } else
            res = new StringBuilder("COMPLETED\n");
        return res.toString();
    }
}