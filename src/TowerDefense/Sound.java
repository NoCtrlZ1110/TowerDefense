package TowerDefense;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;

import static TowerDefense.CONSTANT.PREPARE_TIME;

public class Sound {
    private static Media welcomeMusic = new Media(new File("sound/combat_plan.mp3").toURI().toString());
    private static Media supercellSound = new Media(new File("sound/supercell.mp3").toURI().toString());
    private static Media loadingSound = new Media(new File("sound/loading.mp3").toURI().toString());
    private static Media buildingSound = new Media(new File("sound/building_construct.mp3").toURI().toString());
    private static Media buildingFinishSound = new Media(new File("sound/building_finished.mp3").toURI().toString());
    private static Media buttonClickSound = new Media(new File("sound/button_click.mp3").toURI().toString());
    private static Media archerSound = new Media(new File("sound/arrow_hit.mp3").toURI().toString());
    private static Media prepareMusic = new Media(new File("sound/fem_talk.mp3").toURI().toString());


    private static MediaPlayer prepareMusicPlayer = new MediaPlayer(prepareMusic);
    private static MediaPlayer supercellPlayer = new MediaPlayer(supercellSound);
    private static MediaPlayer loadingPlayer = new MediaPlayer(loadingSound);
    private static MediaPlayer welcomePlayer = new MediaPlayer(welcomeMusic);


    public static void playWelcomeMusic() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(200), event -> {
                    supercellPlayer.play();
                }),new KeyFrame(Duration.millis(1800), event -> {
                    loadingPlayer.play();
                }), new KeyFrame(Duration.millis(4300), event -> {
            welcomePlayer.play();
            welcomePlayer.setCycleCount(Animation.INDEFINITE);

        }));
        timeline.play();

    }

    public static void pauseWelcomeMusic() {

//        welcomePlayer.pause();

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(2),
                        new KeyValue(welcomePlayer.volumeProperty(), 0)));
        timeline.play();

    }

    public static void buildingSound() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(0), event -> {
                    MediaPlayer buildingSoundPlayer = new MediaPlayer(buildingSound);

                    buildingSoundPlayer.play();

                }), new KeyFrame(Duration.millis(1800), event -> {
            MediaPlayer buildingFinishSoundPlayer = new MediaPlayer(buildingFinishSound);
            buildingFinishSoundPlayer.play();

        }));
        timeline.play();
    }

    static MediaPlayer buttonClickSoundPlayer = new MediaPlayer(buttonClickSound);
    public static void clickSound() {
        buttonClickSoundPlayer.play();

    }

    static Media combatMusic = new Media(new File("sound/combat.mp3").toURI().toString());
    static Media minion = new Media(new File("sound/Minions have spawned.mp3").toURI().toString());

    static MediaPlayer combatMusicPlayer = new MediaPlayer(combatMusic);
    static MediaPlayer minionSpawn = new MediaPlayer(minion);
    public static void combatMusic() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(0), event ->
                        minionSpawn.play()), new KeyFrame(Duration.millis(1800), event ->
        {
            combatMusicPlayer.play();
            combatMusicPlayer.setCycleCount(Animation.INDEFINITE);
        }
        ));
        timeline.play();

    }

//    public static void archerSound()
//    {
//        MediaPlayer archerSoundPlayer = new MediaPlayer(archerSound);
//        archerSoundPlayer.setVolume(0.4);
//        archerSoundPlayer.play();
//        archerSoundPlayer = null;
//    }

    public static void prepareMusic() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(0), event ->
                        prepareMusicPlayer.play()), new KeyFrame(Duration.seconds(PREPARE_TIME), event ->
                prepareMusicPlayer.stop()
        ));
        timeline.play();

    }



}
