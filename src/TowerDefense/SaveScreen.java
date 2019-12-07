package TowerDefense;

import javafx.scene.Cursor;
import javafx.scene.layout.Pane;

import static TowerDefense.GameField.isStarted;
import static TowerDefense.GameField.layout;
import static TowerDefense.PauseScreen.hidePauseMenu;

public class SaveScreen {
    private static boolean is_yesno_clicked = false;
    private static Pane savePane = new Pane();
    private static imageObject saveMenu = new imageObject("file:images/PauseMenu/frame.png");
    private static imageObject yesBtn = new imageObject("file:images/PauseMenu/resume.png");
    private static imageObject noBtn = new imageObject("file:images/PauseMenu/quit.png");
    private static imageObject cancelBtn = new imageObject("file:images/PauseMenu/back.png");

    public static void showSaveMenu() {
        savePane.setLayoutX(180);
        savePane.setLayoutY(100);
        saveMenu.setLocation(savePane.getLayoutX(), savePane.getLayoutY());
        yesBtn.setLocation(savePane.getLayoutX() + 125, savePane.getLayoutY() + 160);
        noBtn.setLocation(savePane.getLayoutX() + 258, savePane.getLayoutY() + 145);
        cancelBtn.setLocation(savePane.getLayoutX() + 415, savePane.getLayoutY() + 160);

        if (!savePane.getChildren().contains(saveMenu)) savePane.getChildren().add(saveMenu);
        if (!savePane.getChildren().contains(yesBtn)) savePane.getChildren().add(yesBtn);
        if (!savePane.getChildren().contains(noBtn)) savePane.getChildren().add(noBtn);
        if (!savePane.getChildren().contains(cancelBtn)) savePane.getChildren().add(cancelBtn);

        if (!layout.getChildren().contains(savePane)) layout.getChildren().add(savePane);

        yesBtn.setOnMouseEntered(event -> yesBtn.setCursor(Cursor.HAND));
        yesBtn.setOnMouseExited(event -> yesBtn.setCursor(Cursor.DEFAULT));
        noBtn.setOnMouseEntered(event -> noBtn.setCursor(Cursor.HAND));
        noBtn.setOnMouseExited(event -> noBtn.setCursor(Cursor.DEFAULT));
        cancelBtn.setOnMouseEntered(event -> cancelBtn.setCursor(Cursor.HAND));
        cancelBtn.setOnMouseExited(event -> cancelBtn.setCursor(Cursor.DEFAULT));

        yesBtn.setOnMouseClicked(event -> {
            System.out.println("-> yes");
            if (isStarted)
                GameField.saveGame();
            is_yesno_clicked = true;
            hideSaveMenu();
            hidePauseMenu();
        });
        noBtn.setOnMouseClicked(event -> {
            System.out.println("-> no");
            is_yesno_clicked = true;
            hideSaveMenu();
            hidePauseMenu();
        });
        cancelBtn.setOnMouseClicked(event -> {
            System.out.println("-> cancel");
            is_yesno_clicked = false;
            hideSaveMenu();
        });
    }

    public static boolean isYesNo() {
        return is_yesno_clicked;
    }

    private static void hideSaveMenu() {
        layout.getChildren().remove(savePane);
    }
}
