package org.moqucu.games.nightstalker;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.stage.Stage;
import org.moqucu.games.nightstalker.utility.BackGroundMusicLoop;
import org.moqucu.games.nightstalker.view.FxmlView;
import org.moqucu.games.nightstalker.view.StageManager;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
public class NightStalkerRevived extends Application {

    private ConfigurableApplicationContext springContext;

    private StageManager stageManager;

    public static String translate(String relativePath) {

        return NightStalkerRevived.class.getResource(relativePath).toExternalForm();
    }

    private ConfigurableApplicationContext bootstrapSpringApplicationContext() {

        SpringApplicationBuilder builder = new SpringApplicationBuilder(NightStalkerRevived.class);
        String[] args = getParameters().getRaw().toArray(new String[0]);
        builder.headless(false);

        return builder.run(args);
    }

    @Override
    public void init() {

        springContext = bootstrapSpringApplicationContext();
    }

    private void displayInitialScene() {

        stageManager.switchScene(FxmlView.SPLASH_SCREEN);
    }

    @Override
    public void start(Stage primaryStage) {

        stageManager = springContext.getBean(StageManager.class, primaryStage);
        displayInitialScene();

        Task backGroundMusicLoop = new BackGroundMusicLoop();
        ExecutorService service = Executors.newFixedThreadPool(4);
        service.execute(backGroundMusicLoop);
    }

    @Override
    public void stop() {

        springContext.close();
    }

    public static void main(String[] args) {

        Application.launch(args);
    }
}
