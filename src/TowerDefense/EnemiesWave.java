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
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private int countWaveTotalEnemies = 0;
    private int countWaveCreatedEnemies = 0;
    private Timeline waveTimeline = new Timeline();
    private boolean is_pre_delaying = true;

    private EnemiesWave(int pre_sec_delay) {
        total_ms_before = pre_sec_delay * 1000;
    }

    public EnemiesWave(int pre_sec_delay, int total_enemies, String... enemy_type) {
        total_ms_before = pre_sec_delay * 1000;
        countWaveTotalEnemies = total_enemies;
        generateEnemies(enemy_type);
        addTimelineEvents();
    }

    private void generateEnemies(String... enemy_type) {
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
                    minion = Enemy.generateByType(enemy_type[idx], -TILE_WIDTH, 720);
                    break;
                }
            }
            // minion.scaleTo(70, 70);
            // minion.setSpeed(1.2);
            addEnemy(minion);

            countWaveCreatedEnemies++;
        }
        // System.out.println(total_enemies + " " + total_waves + " " + enemies);
    }

    private void addTimelineEvents() {
        waveTimeline.getKeyFrames().add(new KeyFrame(
            Duration.millis(total_ms_before),
            event -> is_pre_delaying = true
        ));
        for (int i = 0; i < enemies.size(); i++) {
            Enemy e = enemies.get(i);
            KeyFrame moveEnemy = new KeyFrame(
                Duration.millis(total_ms_before + i * 800),
                event -> {
                    is_pre_delaying = false;
                    e.move(path);
                }
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

    private void addEnemy(Enemy enemy) {
        enemies.add(enemy);
        layout.getChildren().add(enemy);
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
            res.append(String.format(
                "PRE-LOAD TIMES (S): %d\n",
                (is_pre_delaying ? total_ms_before / 1000 : 0)
            ));

            for (Enemy e: enemies)
                res.append(e.toString()).append("\n");
        } else
            res = new StringBuilder("COMPLETED\n");
        return res.toString();
    }

    public static EnemiesWave loadFromString(String str) {
        EnemiesWave res = new EnemiesWave(0);
        if (!str.equals("COMPLETED")) {
            String[] lines = str.split("\n");
            int pre_sec_delay = Integer.parseInt(lines[0].substring(lines[0].indexOf(": ") + 2));
            res = new EnemiesWave(pre_sec_delay);

            for (int i = 1; i < lines.length; i++) {
                String str_enemy = lines[i];
                if (str_enemy.length() > 0) {
                    Enemy enemy = Enemy.loadFromString(str_enemy);
                    // System.out.println(str_enemy);
                    // System.out.println("-> " + enemy);
                    res.addEnemy(enemy);
                }
            }
            res.addTimelineEvents();
        }
        return res;
    }
}