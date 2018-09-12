package remi;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import remi.display.DisplayController;
import remi.game.GameCoordinator;

import java.io.IOException;
import java.time.Duration;

public class mainApp extends Application {

    private static DisplayController controller = new DisplayController();
    private GameCoordinator coordinator;

    @Override
    public void start(Stage primaryStage) throws IOException {

        primaryStage.setResizable(false);
        primaryStage.setTitle("Remi Tile");
        FXMLLoader loader = new FXMLLoader();
        loader.setController(controller);
        BorderPane rootPane = loader.load(getClass().getResourceAsStream("display/display.fxml"));
        primaryStage.setScene(new Scene(rootPane));
        primaryStage.show();
        coordinator = new GameCoordinator();

        AnimationTimer a = new AnimationTimer() {
            private long nextTime = 0;

            @Override
            public void handle(long now) {

                if(now > nextTime) {
                    coordinator.start();
                    nextTime = now + Duration.ofMillis(1).toNanos();
                }
            }
        };
        a.start();
    }

    /* A single display controller is used to interact with the display files. Necessary to avoid any parallel
     * instantiation issues with JavaFX and multiple fxml files*/
    public static DisplayController getDisplayController() {
        return controller;
    }

    public static void main(String[] args) {
        launch(args);
    }
}

