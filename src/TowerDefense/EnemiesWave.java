package TowerDefense;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.ArrayList;

import static TowerDefense.CONSTANT.PREPARE_TIME;
import static TowerDefense.CONSTANT.TILE_WIDTH;
import static TowerDefense.GameField.layout;
import static TowerDefense.GameField.road;
import static TowerDefense.GameField.path;
import static TowerDefense.GameField.isStarted;

public class EnemiesWave {
    public ArrayList<Enemy> enemies;
    private Timeline moveEnemyTimeline = new Timeline();

    public EnemiesWave() {
        enemies = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            // Enemy minion = new Enemy(-TILE_WIDTH, 720, pathRedEnemy);
            Enemy minion;
            if (i % 3 == 0)
                minion = new NormalEnemy(-TILE_WIDTH, 720);
            else if (i % 3 == 1)
                minion = new SmallerEnemy(-TILE_WIDTH, 720);
            else
                minion = new TankerEnemy(-TILE_WIDTH, 720);
            // minion.scaleTo(70, 70);
            // minion.setSpeed(1.2);
            enemies.add(minion);
            layout.getChildren().add(minion);
        }
        Enemy boss = new BossEnemy(-TILE_WIDTH, 720);
        enemies.add(boss);
        layout.getChildren().add(boss);

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
        moveEnemyTimeline.play();
    }

    public double getWaveRate() {
        return enemies.size() / 20.0;
    }

    public void removeEnemy(Enemy enemy) {
        enemies.remove(enemy);
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
