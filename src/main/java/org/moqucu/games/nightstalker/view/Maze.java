package org.moqucu.games.nightstalker.view;

import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.moqucu.games.nightstalker.model.MazeGraph;
import org.moqucu.games.nightstalker.model.QuadTree;
import javafx.scene.input.KeyCode;
import org.moqucu.games.nightstalker.view.movable.ArtificiallyMovedSprite;
import org.moqucu.games.nightstalker.view.movable.DirectedSprite;
import org.springframework.core.io.ClassPathResource;

import java.util.*;
import java.util.concurrent.*;

@Log4j2
public class Maze extends StackPane implements Updatable {

    private final static double PREF_WIDTH = 640d;

    private final static double PREF_HEIGHT = 384;

    private final ConcurrentMap<Integer, List<Sprite>> allGameObjects = new ConcurrentHashMap<>();

    private final CopyOnWriteArrayList<Sprite> movableSprites = new CopyOnWriteArrayList<>();

    private final QuadTree unmovableSprites;

    private final MazeGraph mazeGraph;

    @SneakyThrows
    public Maze() {

        super();
        setId("org.moqucu.games.nightstalker.maze");
        mazeGraph = new MazeGraph((new ClassPathResource("org/moqucu/games/nightstalker/data/maze-graph.json").getInputStream()));

        this.getChildren().addListener((ListChangeListener<Node>) change -> {
            change.next();
            change.getAddedSubList().forEach(addedPane -> {
                if (addedPane instanceof Pane) {
                    log.info("pane added to Maze; is of type {}", addedPane.getClass().getName());
                    log.info("Pane has this many children: {}", ((Pane)addedPane).getChildren().size());
                    ((Pane) addedPane)
                            .getChildren()
                            .filtered(node -> node instanceof ArtificiallyMovedSprite)
                            .forEach(sprite -> ((ArtificiallyMovedSprite)sprite).setMazeGraph(mazeGraph));

                    ((Pane) addedPane).getChildren().addListener(new ListChangeListener<>() {

                        @Override
                        public void onChanged(Change<? extends Node> change) {
                            change.next();
                            change.getAddedSubList().forEach(addedChild -> {
                                log.info("child added to Pane; is of type {}", addedChild.getClass().getName());
                                if (addedChild instanceof ArtificiallyMovedSprite)
                                    ((ArtificiallyMovedSprite) addedChild).setMazeGraph(mazeGraph);
                            });
                        }
                    });
                }
            });
        });

        setWidth(PREF_WIDTH);
        setPrefWidth(PREF_WIDTH);
        setHeight(PREF_HEIGHT);
        setPrefHeight(PREF_HEIGHT);
        unmovableSprites = new QuadTree(getBoundsInLocal());
    }

    public Maze(int width, int height) {

        this();
        setWidth(width);
        setPrefWidth(width);
        setHeight(height);
        setPrefHeight(height);
    }

    // todo: trace added children in QuadTree
    void addGameObject(int layer, Sprite gameObject) {

        List<Sprite> layerSpecificGameObjects = allGameObjects.getOrDefault(layer, new ArrayList<>());
        layerSpecificGameObjects.add(gameObject);
        allGameObjects.putIfAbsent(layer, layerSpecificGameObjects);

        if (gameObject instanceof DirectedSprite)
            movableSprites.add(gameObject);
        else
            unmovableSprites.insert(gameObject);
    }

    // todo: trace added children in QuadTree
    void addGameObjects(int layer, List<? extends Sprite> gameObjects) {

        List<Sprite> layerSpecificGameObjects = this.allGameObjects.getOrDefault(layer, new ArrayList<>());
        layerSpecificGameObjects.addAll(gameObjects);
        this.allGameObjects.putIfAbsent(layer, layerSpecificGameObjects);
        gameObjects.forEach(gameObject -> addGameObject(layer, gameObject));
    }

    private List<Sprite> getAllSpritesInProximityAndThoseWhoMove(Sprite sprite) {

        List<Sprite> allSprites = new ArrayList<>();
        allSprites.addAll(unmovableSprites.findNearbyGameObjects(sprite));
        allSprites.addAll(movableSprites);

        return allSprites;
    }

    private List<Sprite> getAllSpritesInProximityAndThoseWhoMove(List<Sprite> sprites) {

        List<Sprite> allSprites = new ArrayList<>();
        sprites.forEach(sprite -> allSprites.addAll(getAllSpritesInProximityAndThoseWhoMove(sprite)));

        return allSprites;
    }

    @Override
    public void update(
            double deltaTimeSinceStart,
            double deltaTime,
            Set<KeyCode> input,
            List<Sprite> nearbyObjects
    ) {

        if (input.size() > 0 && (input.contains(KeyCode.ESCAPE) || input.contains(KeyCode.Q)))
            System.exit(0);

        allGameObjects.keySet().forEach(key -> allGameObjects.get(key).forEach(gameObject -> {

            if (gameObject instanceof Updatable) {

                List<Sprite> nearbySprites = new ArrayList<>();

                if (gameObject instanceof Sprite)
                    nearbySprites.addAll(getAllSpritesInProximityAndThoseWhoMove((Sprite) gameObject));

                if (gameObject instanceof DirectedSprite)
                    nearbySprites.addAll(getAllSpritesInProximityAndThoseWhoMove(
                            ((DirectedSprite) gameObject).createShadowSpritePerMovableDirection(deltaTime))
                    );

                ((Updatable) gameObject).update(
                        deltaTimeSinceStart,
                        deltaTime,
                        input,
                        nearbySprites
                );
            }
        }));
    }
}
