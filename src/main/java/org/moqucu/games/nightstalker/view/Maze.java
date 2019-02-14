package org.moqucu.games.nightstalker.view;

import javafx.scene.layout.StackPane;
import org.moqucu.games.nightstalker.data.QuadTree;
import javafx.scene.input.KeyCode;
import org.moqucu.games.nightstalker.view.movable.MovableSprite;

import java.util.*;
import java.util.concurrent.*;

public class Maze extends StackPane implements Updatable {

    private final static double PREF_WIDTH = 640d;

    private final static double PREF_HEIGHT = 384;

    private final ConcurrentMap<Integer, List<Sprite>> allGameObjects = new ConcurrentHashMap<>();

    private final CopyOnWriteArrayList<Sprite> movableSprites = new CopyOnWriteArrayList<>();

    private final QuadTree unmovableSprites;

    public Maze() {

        super();
        setId("org.moqucu.games.nightstalker.maze");
        setWidth(PREF_WIDTH);
        setPrefWidth(PREF_WIDTH);
        setHeight(PREF_HEIGHT);
        setPrefHeight(PREF_HEIGHT);
        unmovableSprites = new QuadTree(getBoundsInLocal());
    }

    public Maze(int width, int height) {

        super();
        setWidth(width);
        setPrefWidth(width);
        setHeight(height);
        setPrefHeight(height);
        unmovableSprites = new QuadTree(getBoundsInLocal());
    }

    void addGameObject(int layer, Sprite gameObject) {

        List<Sprite> layerSpecificGameObjects = allGameObjects.getOrDefault(layer, new ArrayList<>());
        layerSpecificGameObjects.add(gameObject);
        allGameObjects.putIfAbsent(layer, layerSpecificGameObjects);

        if (gameObject instanceof MovableSprite)
            movableSprites.add(gameObject);
        else
            unmovableSprites.insert(gameObject);
    }

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
                    nearbySprites.addAll(getAllSpritesInProximityAndThoseWhoMove((Sprite)gameObject));

                if (gameObject instanceof MovableSprite)
                    nearbySprites.addAll(getAllSpritesInProximityAndThoseWhoMove(
                            ((MovableSprite) gameObject).createShadowSpritePerMovableDirection(deltaTime))
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
