package org.moqucu.games.nightstalker.view;

import lombok.extern.log4j.Log4j2;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import org.moqucu.games.nightstalker.configuration.SpringFXMLLoader;
import org.moqucu.games.nightstalker.controller.FxmlView;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Log4j2
public class StageManager {

    private final Stage primaryStage;
    private final SpringFXMLLoader springFXMLLoader;
    private Map<Parent, Scene> scenes = new HashMap<>();

    public StageManager(SpringFXMLLoader springFXMLLoader, Stage primaryStage) {

        this.springFXMLLoader = springFXMLLoader;
        this.primaryStage = primaryStage;
    }

    public void switchScene(final FxmlView view) {

        Parent viewRootNodeHierarchy = loadViewNodeHierarchy(view.getFxmlFile());
        log.info(viewRootNodeHierarchy.getId());
        show(viewRootNodeHierarchy, view.getTitle());
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

        return Optional.of(springFXMLLoader.load(fxmlFilePath)).get();
    }
}
