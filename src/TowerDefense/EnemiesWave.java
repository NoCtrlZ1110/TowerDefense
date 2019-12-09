package TowerDefense;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;

import static TowerDefense.CONSTANT.TILE_WIDTH;
import static TowerDefense.GameField.path;

public class EnemiesWave {
    private int total_ms_before;
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private int countWaveTotalEnemies = 0;
    private Timeline waveTimeline = new Timeline();

    public EnemiesWave(int pre_sec_delay, String str) {
        total_ms_before = pre_sec_delay * 1000;
        countWaveTotalEnemies = 0;
        if (!str.equals("COMPLETED")) {
            String[] lines = str.split("\n");
            countWaveTotalEnemies = Integer.parseInt(lines[0].substring(lines[0].indexOf(": ") + 2));

            for (int i = 1; i < lines.length; i++) {
                String str_enemy = lines[i];
                if (str_enemy.length() > 0) {
                    Enemy enemy = Enemy.loadFromString(str_enemy);
                    enemy.changePathByStartPoint();
                    // System.out.println(str_enemy);
                    // System.out.println("-> " + enemy);
                    addEnemy(enemy);
                }
            }
            addTimelineEvents();
        }
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

        }
        // System.out.println(total_enemies + " " + total_waves + " " + enemies);
    }

    private void addTimelineEvents() {
        int count_not_moving = 0;
        for (int i = 0; i < enemies.size(); i++) {
            Enemy e = enemies.get(i);
            int added_ms = 0;
            int x = e.getLocation().getX(), y = e.getLocation().getY();
            if (x == -TILE_WIDTH && y == 720) {
                added_ms = count_not_moving * 800;
                count_not_moving++;
            }

            KeyFrame moveEnemy = new KeyFrame(
                Duration.millis(total_ms_before + added_ms),
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

    public void show() {
        for (Enemy enemy: enemies)
            enemy.show();
    }

    private void addEnemy(Enemy enemy) {
        enemies.add(enemy);
        // layout.getChildren().add(enemy);
    }

    public void removeEnemy(Enemy enemy) {
        enemies.remove(enemy);
    }

    public double getWaveRate() {
        return (countWaveTotalEnemies - enemies.size()) / (countWaveTotalEnemies * 1.0);
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
        enemies.forEach(Enemy::stopMoving);
    }

    public String toString() {
        StringBuilder res = new StringBuilder();
        if (!isFinished()) {
            res.append(String.format("TOTAL ENEMIES: %d\n", countWaveTotalEnemies));
            for (Enemy e: enemies)
                res.append(e.toString()).append("\n");
        } else
            res = new StringBuilder("COMPLETED\n");
        return res.toString();
    }
}