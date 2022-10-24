package org.moqucu.games.nightstalker.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.shape.CubicCurve;
import lombok.extern.log4j.Log4j2;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import org.moqucu.games.nightstalker.controller.GameScreenController;
import org.moqucu.games.nightstalker.controller.SplashScreenController;
import org.moqucu.games.nightstalker.utility.LoadListenerAdapter;
import org.moqucu.games.nightstalker.view.object.LivesLabel;
import org.moqucu.games.nightstalker.view.object.ScoreLabel;
import org.moqucu.games.nightstalker.utility.SpriteCreationListener;

import java.util.HashMap;
import java.util.Map;

@Log4j2
public class StageManager implements SpriteCreationListener {

    private final Stage primaryStage;
    private final Map<Parent, Scene> scenes = new HashMap<>();

    public StageManager(Stage primaryStage) {

        this.primaryStage = primaryStage;
        this.registerSpriteClass(Group.class);
        this.registerSpriteClass(CubicCurve.class);

        this.registerSpriteClass(LivesLabel.class);
        this.registerSpriteClass(ScoreLabel.class);
    }

    public Parent switchScene(final FxmlView view) {

        Parent viewRootNodeHierarchy = loadViewNodeHierarchy(view.getFxmlFile());
        log.debug(viewRootNodeHierarchy.getId());

        show(viewRootNodeHierarchy, view.getTitle());

        return viewRootNodeHierarchy;
    }

    private void show(Parent rootNode, String title) {

        primaryStage.setTitle(title);
        primaryStage.setScene(prepareScene(rootNode));
        primaryStage.sizeToScene();
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    private Scene prepareScene(Parent rootNode) {

        if (!scenes.containsKey(rootNode)) {

            Scene scene = new Scene(rootNode);
            scenes.put(rootNode, scene);
        }

        return scenes.get(rootNode);
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

        fxmlLoader.setControllerFactory(
                param -> {

                    if (param.equals(SplashScreenController.class))
                        return new SplashScreenController(this);
                    else
                        return new GameScreenController(this);
                }
        );

        fxmlLoader.setLoadListener(new LoadListenerAdapter(this));

        return fxmlLoader.load(getClass().getResourceAsStream(fxmlFilePath));
    }
}
