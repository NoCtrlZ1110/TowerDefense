package TowerDefense;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;

import static TowerDefense.CONSTANT.PREPARE_TIME;
import static TowerDefense.CONSTANT.TILE_WIDTH;
import static TowerDefense.GameField.layout;
import static TowerDefense.GameField.road;
import static TowerDefense.GameField.path;
import static TowerDefense.GameField.isStarted;

public class EnemiesWave {
    public ArrayList<Enemy> enemies;
    public Timeline moveEnemyTimeline = new Timeline();
    private int countCreatedEnemies;

    public EnemiesWave(int total_enemies, String... enemy_type) {
        countCreatedEnemies = 0;
        enemies = new ArrayList<>();

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
            enemies.add(minion);
            layout.getChildren().add(minion);

            countCreatedEnemies++;
        }

        moveEnemies();
    }

    private void moveEnemies() {
        moveEnemyTimeline.getKeyFrames().add(new KeyFrame(Duration.seconds(4), new KeyValue(road.opacityProperty(), 0)));
        moveEnemyTimeline.getKeyFrames().add(new KeyFrame(Duration.seconds(5), new KeyValue(road.opacityProperty(), 1)));
        moveEnemyTimeline.getKeyFrames().add(new KeyFrame(Duration.seconds(5), event -> isStarted = true));

        for (int i = 0; i < enemies.size(); i++) {
            Enemy e = enemies.get(i);
            KeyFrame moveEnemy = new KeyFrame(Duration.millis(i * 800 + PREPARE_TIME * 1000), event -> e.move(path));
            moveEnemyTimeline.getKeyFrames().add(moveEnemy);
        }
    }

    private void setTimer() {}

    public double getWaveRate() {
        return countCreatedEnemies / 20.0;
    }

    public void removeEnemy(Enemy enemy) {
        enemies.remove(enemy);
    }

    public void start() {
        moveEnemyTimeline.play();
    }

    public void pause() {
        moveEnemyTimeline.pause();
        enemies.forEach(Enemy::pauseMoving);
    }

    public void resume() {
        if (moveEnemyTimeline.getStatus() != Animation.Status.STOPPED)
            moveEnemyTimeline.play();

        enemies.forEach(Enemy::resumeMoving);
    }
}
