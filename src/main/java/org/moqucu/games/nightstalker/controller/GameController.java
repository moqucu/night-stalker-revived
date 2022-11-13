package org.moqucu.games.nightstalker.controller;

import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Callback;
import lombok.Getter;
import lombok.SneakyThrows;
import org.moqucu.games.nightstalker.model.GameObject;
import org.moqucu.games.nightstalker.model.GameWorld;
import org.moqucu.games.nightstalker.utility.BackGroundMusicLoop;
import org.moqucu.games.nightstalker.utility.FxmlView;
import org.moqucu.games.nightstalker.utility.GameLoop;
import org.moqucu.games.nightstalker.utility.LoadListenerAdapter;
import org.moqucu.games.nightstalker.utility.SystemWrapper;
import org.moqucu.games.nightstalker.view.Sprite;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameController {

    private final SystemWrapper systemWrapper;

    private final Stage stage;

    @Getter
    private final GameWorld gameWorld = new GameWorld();

    @Getter
    private final Map<Parent, Scene> scenes = new HashMap<>();

    private final BackGroundMusicLoop backGroundMusicLoop;

    @Getter
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private final Map<Sprite, GameObject> gameElements = new HashMap<>();

    private final GameLoop gameLoop;

    private final Callback<Class<?>, Object> controllerFactory = param -> {

        if (param.equals(SplashScreenController.class))
            return new SplashScreenController(this);
        else
            return new GameScreenController(this);
    };

    private void startBackgroundMusicLoop() {

        if (backGroundMusicLoop != null) {

            Task<Void> backGroundMusicLoop = this.backGroundMusicLoop;
            ExecutorService service = Executors.newFixedThreadPool(1);
            service.execute(backGroundMusicLoop);
        }
    }

    /**
     * Loads the object hierarchy from a FXML document and returns to root node
     * of that hierarchy.
     *
     * @return Parent root node of the FXML document hierarchy
     */
    @SneakyThrows
    private Parent loadViewNodeHierarchy(String fxmlFilePath) {

        final FXMLLoader fxmlLoader = new FXMLLoader();

        fxmlLoader.setControllerFactory(controllerFactory);
        fxmlLoader.setLoadListener(new LoadListenerAdapter(this));

        return fxmlLoader.load(getClass().getResourceAsStream(fxmlFilePath));
    }

    private Scene prepareScene(Parent rootNode) {

        if (!scenes.containsKey(rootNode)) {

            Scene scene = new Scene(rootNode);
            scenes.put(rootNode, scene);
        }

        return scenes.get(rootNode);
    }

    private void show(Parent rootNode, String title) {

        stage.setTitle(title);
        stage.setScene(prepareScene(rootNode));
        stage.sizeToScene();
        stage.centerOnScreen();
        stage.show();
    }

    public void switchScene(final FxmlView view) {

        Parent viewRootNodeHierarchy = loadViewNodeHierarchy(view.getFxmlFile());
        show(viewRootNodeHierarchy, view.getTitle());
    }

    public GameController(
            Stage stage,
            SystemWrapper systemWrapper,
            BackGroundMusicLoop loop,
            GameLoop gameLoop
    ) {

        backGroundMusicLoop = loop;
        this.stage = stage;
        if (stage != null) {

            startBackgroundMusicLoop();
            switchScene(FxmlView.SPLASH_SCREEN);
        }
        this.systemWrapper = systemWrapper;
        this.gameLoop = gameLoop;
        gameLoop.setGameWorld(gameWorld);
    }

    public void addSprite(Sprite sprite) {

        gameElements.put(sprite, sprite.getModel());
        gameWorld.add(sprite.getModel());
    }

    public void startGameLoop() {

        gameLoop.start();
    }

    public void stopGameLoop() {

        gameLoop.stop();
    }

    public void endGame() {

        systemWrapper.exit(0);
    }
}
