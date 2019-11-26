package TowerDefense;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Cursor;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;

//import static TowerDefense.GameField.*;
import static TowerDefense.CONSTANT.PREPARE_TIME;
import static TowerDefense.GameField.pauseGame;
import static TowerDefense.GameField.road;

public class Sound {
    static boolean isMuted = false;

    private static Media welcomeMusic = new Media(new File("sound/PvZintro.mp3").toURI().toString());
    private static Media supercellSound = new Media(new File("sound/supercell.mp3").toURI().toString());
    private static Media loadingSound = new Media(new File("sound/loading.mp3").toURI().toString());
    private static Media buildingSound = new Media(new File("sound/building_construct.mp3").toURI().toString());
    private static Media buildingFinishSound = new Media(new File("sound/building_finished.mp3").toURI().toString());
    private static Media buttonClickSound = new Media(new File("sound/button_click.mp3").toURI().toString());
    //    private static Media archerSound = new Media(new File("sound/arrow_hit.mp3").toURI().toString());
    private static Media prepareMusic = new Media(new File("sound/Grasswalk.mp3").toURI().toString());
    private static Media combatMusic = new Media(new File("sound/combat.mp3").toURI().toString());
    private static Media minion = new Media(new File("sound/Minions have spawned.mp3").toURI().toString());

    private static MediaPlayer combatMusicPlayer = new MediaPlayer(combatMusic);
    private static MediaPlayer minionSpawn = new MediaPlayer(minion);
    private static MediaPlayer buttonClickSoundPlayer = new MediaPlayer(buttonClickSound);
    private static MediaPlayer prepareMusicPlayer = new MediaPlayer(prepareMusic);
    private static MediaPlayer superCellPlayer = new MediaPlayer(supercellSound);
    private static MediaPlayer loadingPlayer = new MediaPlayer(loadingSound);
    private static MediaPlayer welcomePlayer = new MediaPlayer(welcomeMusic);

    public static void mute() {
        combatMusicPlayer.setVolume(0);
        minionSpawn.setVolume(0);
        buttonClickSoundPlayer.setVolume(0);
        prepareMusicPlayer.setVolume(0);
        superCellPlayer.setVolume(0);
        loadingPlayer.setVolume(0);
        welcomePlayer.setVolume(0);
    }

    public static void unMute() {
        combatMusicPlayer.setVolume(1);
        minionSpawn.setVolume(1);
        buttonClickSoundPlayer.setVolume(1);
        prepareMusicPlayer.setVolume(1);
        superCellPlayer.setVolume(1);
        loadingPlayer.setVolume(1);
        welcomePlayer.setVolume(1);
    }

    public static void playWelcomeMusic() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(200), event -> {
                    superCellPlayer.play();
                }), new KeyFrame(Duration.millis(1800), event -> {
            loadingPlayer.play();
        }), new KeyFrame(Duration.millis(4500), event -> {
            welcomePlayer.play();
            welcomePlayer.setCycleCount(Animation.INDEFINITE);
        }));
        timeline.play();
    }

    public static void pauseWelcomeMusic() {

        // welcomePlayer.pause();

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(2),
                        new KeyValue(welcomePlayer.volumeProperty(), 0)), new KeyFrame(Duration.seconds(2), e -> welcomePlayer.stop()));
        timeline.play();
    }

    static Timeline gameScreenMusicTimeline;
    public static void playGameScreenMusic()
    {
        gameScreenMusicTimeline = new Timeline(new KeyFrame(Duration.seconds(0), event ->  prepareMusicPlayer.play()),
                new KeyFrame(Duration.seconds(PREPARE_TIME), event -> combatMusic()),
                new KeyFrame(Duration.seconds(PREPARE_TIME),new KeyValue(prepareMusicPlayer.volumeProperty(),1)),
                new KeyFrame(Duration.seconds(PREPARE_TIME+2),new KeyValue(prepareMusicPlayer.volumeProperty(),0))     ,
                new KeyFrame(Duration.seconds(PREPARE_TIME+2),event -> prepareMusicPlayer.stop())
        );
        gameScreenMusicTimeline.play();
    }

    public static void buildingSound() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(0), event -> {
                    MediaPlayer buildingSoundPlayer = new MediaPlayer(buildingSound);
                    if (isMuted) buildingSoundPlayer.setVolume(0);
                    buildingSoundPlayer.play();

                }), new KeyFrame(Duration.millis(1800), event -> {
            MediaPlayer buildingFinishSoundPlayer = new MediaPlayer(buildingFinishSound);
            if (isMuted) buildingFinishSoundPlayer.setVolume(0);
            buildingFinishSoundPlayer.play();
        }));
        timeline.play();
    }


    public static void clickSound() {
        buttonClickSoundPlayer.play();
    }


    public static void combatMusic() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(0), event -> minionSpawn.play()),
                new KeyFrame(Duration.millis(1400), event -> {
                    combatMusicPlayer.play();
                    combatMusicPlayer.setCycleCount(Animation.INDEFINITE);
                }), new KeyFrame(Duration.seconds(2),event -> prepareMusicPlayer.stop())
        );
        timeline.play();
    }




    static imageObject muteBtn = new imageObject("file:images/mute.png");
    static imageObject speakerBtn = new imageObject("file:images/speaker.png");


    static void showMuteBtn(Pane layout) {
        muteBtn.setLocation((int) layout.getWidth() - 70, 30);
        speakerBtn.setLocation((int) layout.getWidth() - 70, 30);

        muteBtn.scaleTo(40, 40);
        speakerBtn.scaleTo(40, 40);


        if (!layout.getChildren().contains(muteBtn)) layout.getChildren().add(muteBtn);
        if (!layout.getChildren().contains(speakerBtn)) layout.getChildren().add(speakerBtn);

        if (isMuted) {
            mute();
            muteBtn.setVisible(true);
            speakerBtn.setVisible(false);
        } else {
            unMute();
            muteBtn.setVisible(false);
            speakerBtn.setVisible(true);
        }
        muteBtn.setOnMouseEntered(event -> muteBtn.setCursor(Cursor.HAND));
        muteBtn.setOnMouseExited(event -> muteBtn.setCursor(Cursor.DEFAULT));
        speakerBtn.setOnMouseEntered(event -> speakerBtn.setCursor(Cursor.HAND));
        speakerBtn.setOnMouseExited(event -> speakerBtn.setCursor(Cursor.DEFAULT));


        muteBtn.setOnMouseClicked(event -> {
            isMuted = false;
            unMute();
            showMuteBtn(layout);
        });
        speakerBtn.setOnMouseClicked(event -> {
            isMuted = true;
            mute();
            showMuteBtn(layout);
        });




    }
}
