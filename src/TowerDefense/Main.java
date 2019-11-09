import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.stage.Stage;

public class Main extends Application {
    Path path;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        Scene scene = new Scene(root, 300, 250);

        path = new Path();
        path.setStrokeWidth(1);
        path.setStroke(Color.BLACK);

        scene.setOnMouseClicked(mouseHandler);
        scene.setOnMouseDragged(mouseHandler);
        scene.setOnMouseEntered(mouseHandler);
        scene.setOnMouseExited(mouseHandler);
        scene.setOnMouseMoved(mouseHandler);
        scene.setOnMousePressed(mouseHandler);
        scene.setOnMouseReleased(mouseHandler);

        //root.getChildren().add(path);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent mouseEvent) {
            System.out.println(mouseEvent.getEventType() + "\n"
                    + "X : Y - " + mouseEvent.getX() + " : " + mouseEvent.getY() + "\n"
                    + "SceneX : SceneY - " + mouseEvent.getSceneX() + " : " + mouseEvent.getSceneY() + "\n"
                    + "ScreenX : ScreenY - " + mouseEvent.getScreenX() + " : " + mouseEvent.getScreenY());


        }

    };

}