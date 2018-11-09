package model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

import java.util.*;
import java.util.stream.Collectors;

public class World implements Renderable, Updatable {

    private Map<Integer, List<GameObject>> gameObjects = new HashMap<>();

    public World() {
    }

    public void addGameObject(int layer, GameObject gameObject) {

        List<GameObject> layerSpecificGameObjects = gameObjects.getOrDefault(layer, new ArrayList<>());
        layerSpecificGameObjects.add(gameObject);
        gameObjects.putIfAbsent(layer, layerSpecificGameObjects);
    }

    public void addGameObjects(int layer, List<? extends GameObject> gameObjects) {

        List<GameObject> layerSpecificGameObjects = this.gameObjects.getOrDefault(layer, new ArrayList<>());
        layerSpecificGameObjects.addAll(gameObjects);
        this.gameObjects.putIfAbsent(layer, layerSpecificGameObjects);
    }

    @Override
    public void render(GraphicsContext gc, double interpolation) {

        gc.clearRect(0, 0, 640, 384);
        gameObjects
                .keySet()
                .stream()
                .sorted((f1, f2) -> Integer.compare(f2, f1))
                .forEach(key -> gameObjects.get(key).forEach(gameObject -> {

                    if (gameObject instanceof Renderable)
                        ((Renderable) gameObject).render(gc, interpolation);
                }));
    }

    @Override
    public void update(Set<KeyCode> input, List<Sprite> sprites) {

        if (input.size() > 0 && (input.contains(KeyCode.ESCAPE) || input.contains(KeyCode.Q)))
            System.exit(0);

        gameObjects.keySet().forEach(key -> gameObjects.get(key).forEach(gameObject -> {

            if (gameObject instanceof Updatable)
                ((Updatable) gameObject).update(input, sprites);
        }));
    }

    public List<Sprite> getSprites() {

        List<Sprite> sprites = new ArrayList<>();
        gameObjects.keySet().forEach(key -> sprites.addAll(
                gameObjects
                        .get(key)
                        .stream()
                        .filter(gameObject -> gameObject instanceof Sprite)
                        .map(sc -> (Sprite) sc)
                        .collect(Collectors.toList()))
        );

        return sprites;
    }
}
