package org.moqucu.games.nightstalker.controller;

import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Callback;
import lombok.Getter;
import lombok.SneakyThrows;
import org.moqucu.games.nightstalker.model.Direction;
import org.moqucu.games.nightstalker.model.GameObject;
import org.moqucu.games.nightstalker.model.GameWorld;
import org.moqucu.games.nightstalker.model.hero.NightStalker;
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
    private final GameWorld gameWorld;

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

    private Scene show(Parent rootNode, String title) {

        stage.setTitle(title);
        final Scene scene = prepareScene(rootNode);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.centerOnScreen();
        stage.show();

        return scene;
    }

    public Scene switchScene(final FxmlView view) {

        Parent viewRootNodeHierarchy = loadViewNodeHierarchy(view.getFxmlFile());
        return show(viewRootNodeHierarchy, view.getTitle());
    }

    public GameController(
            Stage stage,
            SystemWrapper systemWrapper,
            BackGroundMusicLoop loop,
            GameLoop gameLoop,
            GameWorld gameWorld
    ) {

        backGroundMusicLoop = loop;
        this.stage = stage;
        if (stage != null) {

            startBackgroundMusicLoop();
            switchScene(FxmlView.SPLASH_SCREEN);
        }
        this.systemWrapper = systemWrapper;
        this.gameLoop = gameLoop;
        this.gameWorld = gameWorld;
        gameLoop.setGameWorld(gameWorld);
    }


    public GameController(
            Stage stage,
            SystemWrapper systemWrapper,
            BackGroundMusicLoop loop,
            GameLoop gameLoop
    ) {

        this(stage,systemWrapper, loop, gameLoop, new GameWorld());
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

    public void resetGameWorld() {

        gameWorld.reset();
    }

    public void runNightStalkerWith(Direction direction) {

        final NightStalker nightStalker = (NightStalker) gameWorld.getObjects()
                .values()
                .stream()
                .filter(gameObject -> gameObject instanceof NightStalker)
                .findFirst()
                .orElseThrow();
        switch (direction) {
            case Up -> nightStalker.setUpPressed(true);
            case Down -> nightStalker.setDownPressed(true);
            case Left -> nightStalker.setLeftPressed(true);
            case Right -> nightStalker.setRightPressed(true);
        }
    }

    public void stopNightStalker(Direction direction) {

        final NightStalker nightStalker = (NightStalker) gameWorld.getObjects()
                .values()
                .stream()
                .filter(gameObject -> gameObject instanceof NightStalker)
                .findFirst()
                .orElseThrow();
        switch (direction) {
            case Up -> nightStalker.setUpPressed(false);
            case Down -> nightStalker.setDownPressed(false);
            case Left -> nightStalker.setLeftPressed(false);
            case Right -> nightStalker.setRightPressed(false);
        }
    }
}
