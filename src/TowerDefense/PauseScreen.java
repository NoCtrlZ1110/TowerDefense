package TowerDefense;

import javafx.scene.Cursor;
import javafx.scene.layout.Pane;

import static TowerDefense.CONSTANT.COL_NUM;
import static TowerDefense.CONSTANT.TILE_WIDTH;
import static TowerDefense.GameField.*;

public class PauseScreen {
    static boolean is_quit = false;

    static ImageObject playBtn = new ImageObject("file:images/play.png");
    static ImageObject pauseBtn = new ImageObject("file:images/pause.png");

    public static void showPauseBtn() {
        playBtn.setLocation(TILE_WIDTH * COL_NUM - 70, 80);
        playBtn.scaleTo(40, 40);
        playBtn.setOnMouseEntered(event -> playBtn.setCursor(Cursor.HAND));
        playBtn.setOnMouseExited(event -> playBtn.setCursor(Cursor.DEFAULT));
        if (!layout.getChildren().contains(playBtn))
            layout.getChildren().add(playBtn);
        playBtn.setVisible(false);

        pauseBtn.setLocation(TILE_WIDTH * COL_NUM - 70, 80);
        pauseBtn.scaleTo(40, 40);
        pauseBtn.setOnMouseEntered(event -> pauseBtn.setCursor(Cursor.HAND));
        pauseBtn.setOnMouseExited(event -> pauseBtn.setCursor(Cursor.DEFAULT));
        if (!layout.getChildren().contains(pauseBtn))
            layout.getChildren().add(pauseBtn);
        pauseBtn.setVisible(true);

        pauseBtn.setOnMouseClicked(event -> {
            pauseGame();
            showPauseMenu();
            pauseBtn.setVisible(false);
            //playBtn.setVisible(true);
        });
//        playBtn.setOnMouseClicked(event -> {
//            resumeGame();
//            hidePauseMenu();
//            pauseBtn.setVisible(true);
//            playBtn.setVisible(false);
//        });
    }

    static Pane pausePane = new Pane();
    static ImageObject pauseMenu = new ImageObject("file:images/PauseMenu/frame.png");
    static ImageObject pauseBlack = new ImageObject("file:images/PauseMenu/Black.png");
    static ImageObject saveBtn = new ImageObject("file:images/PauseMenu/save.png");
    static ImageObject resumeBtn = new ImageObject("file:images/PauseMenu/resume.png");
    static ImageObject quitBtn = new ImageObject("file:images/PauseMenu/quit.png");

    public static void showPauseMenu() {
        pausePane.setLayoutX(180);
        pausePane.setLayoutY(100);
        pauseMenu.setLocation(pausePane.getLayoutX(), pausePane.getLayoutY());
        pauseBlack.setLocation(0, 0);
        pauseBlack.setOpacity(0.5);
        saveBtn.setLocation(pausePane.getLayoutX() + 125, pausePane.getLayoutY() + 160);
        resumeBtn.setLocation(pausePane.getLayoutX() + 258, pausePane.getLayoutY() + 145);
        quitBtn.setLocation(pausePane.getLayoutX() + 415, pausePane.getLayoutY() + 160);

        if (!layout.getChildren().contains(pauseBlack)) layout.getChildren().add(pauseBlack);
        if (!pausePane.getChildren().contains(pauseMenu)) pausePane.getChildren().add(pauseMenu);
        if (!pausePane.getChildren().contains(saveBtn)) pausePane.getChildren().add(saveBtn);
        if (!pausePane.getChildren().contains(resumeBtn)) pausePane.getChildren().add(resumeBtn);
        if (!pausePane.getChildren().contains(quitBtn)) pausePane.getChildren().add(quitBtn);

        if (!layout.getChildren().contains(pausePane)) layout.getChildren().add(pausePane);

        saveBtn.setOnMouseEntered(event -> saveBtn.setCursor(Cursor.HAND));
        saveBtn.setOnMouseExited(event -> saveBtn.setCursor(Cursor.DEFAULT));
        resumeBtn.setOnMouseEntered(event -> resumeBtn.setCursor(Cursor.HAND));
        resumeBtn.setOnMouseExited(event -> resumeBtn.setCursor(Cursor.DEFAULT));
        quitBtn.setOnMouseEntered(event -> quitBtn.setCursor(Cursor.HAND));
        quitBtn.setOnMouseExited(event -> quitBtn.setCursor(Cursor.DEFAULT));

        saveBtn.setOnMouseClicked(event -> {
            is_quit = false;
            System.out.println("-> Save");
            SaveScreen.showSaveMenu();
            // GameStage.welcomeScreen();
            pauseBtn.setVisible(true);
        });
        resumeBtn.setOnMouseClicked(event -> {
            is_quit = false;
            resumeGame();
            hidePauseMenu();
            pauseBtn.setVisible(true);
        });
        quitBtn.setOnMouseClicked(event -> {
            System.out.println("-> Quit");
            is_quit = true;
            // hỏi save trước khi quit
            if (isStarted) {
                SaveScreen.showSaveMenu();
            } else
                GameStage.closePrimaryStage();
        });
    }

    public static void refreshPauseMenu() {
        layout.getChildren().remove(pausePane);
        layout.getChildren().remove(pauseBlack);
        layout.getChildren().addAll(pauseBlack, pausePane);
    }

    public static void hidePauseMenu() {
        layout.getChildren().remove(pausePane);
        layout.getChildren().remove(pauseBlack);
    }
    /*
    ImageObject background = new ImageObject("file:images/black_background.png");
    background.setLocation(0, 0);
    background.scaleTo(TILE_WIDTH * COL_NUM, TILE_WIDTH * ROW_NUM);
    layout.getChildren().add(background);

    // layout.getChildren().remove(background);
    */
}
