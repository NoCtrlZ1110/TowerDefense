package TowerDefense;

import javafx.scene.Cursor;
import javafx.scene.layout.Pane;

import static TowerDefense.GameField.isStarted;
import static TowerDefense.GameField.layout;
import static TowerDefense.PauseScreen.hidePauseMenu;
import static TowerDefense.PauseScreen.is_quit;

public class SaveScreen {
    private static Pane savePane = new Pane();
    private static imageObject saveMenu = new imageObject("file:images/SaveMenu/frame.png");
    private static imageObject yesBtn = new imageObject("file:images/SaveMenu/yes.png");
    private static imageObject noBtn = new imageObject("file:images/SaveMenu/no.png");
    private static imageObject cancelBtn = new imageObject("file:images/SaveMenu/cancel.png");

    public static void showSaveMenu() {
        savePane.setLayoutX(180);
        savePane.setLayoutY(100);
        saveMenu.setLocation(savePane.getLayoutX(), savePane.getLayoutY());
        yesBtn.setLocation(savePane.getLayoutX() + 125, savePane.getLayoutY() + 160);
        noBtn.setLocation(savePane.getLayoutX() + 270, savePane.getLayoutY() + 160);
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

            hideSaveMenu();
            if (is_quit)
                GameStage.closePrimaryStage();
            else
                hidePauseMenu();
        });
        noBtn.setOnMouseClicked(event -> {
            System.out.println("-> no");
            hideSaveMenu();
            if (is_quit)
                GameStage.closePrimaryStage();
            else
                hidePauseMenu();
        });
        cancelBtn.setOnMouseClicked(event -> {
            System.out.println("-> cancel");
            hideSaveMenu();
        });
    }

    private static void hideSaveMenu() {
        layout.getChildren().remove(savePane);
    }
}
