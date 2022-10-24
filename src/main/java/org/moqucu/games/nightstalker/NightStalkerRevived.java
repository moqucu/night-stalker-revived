package org.moqucu.games.nightstalker;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.stage.Stage;
import org.moqucu.games.nightstalker.utility.BackGroundMusicLoop;
import org.moqucu.games.nightstalker.view.FxmlView;
import org.moqucu.games.nightstalker.view.StageManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NightStalkerRevived extends Application {

    private StageManager stageManager;

    private void displayInitialScene() {

        stageManager.switchScene(FxmlView.SPLASH_SCREEN);
    }

    @Override
    public void start(Stage primaryStage) {

        stageManager = new StageManager(primaryStage);
        displayInitialScene();

        Task<Void> backGroundMusicLoop = new BackGroundMusicLoop();
        ExecutorService service = Executors.newFixedThreadPool(4);
        service.execute(backGroundMusicLoop);
    }

    public static void main(String[] args) {

        launch(args);
    }
}
