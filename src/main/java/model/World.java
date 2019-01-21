package model;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.media.AudioClip;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static javafx.scene.media.AudioClip.INDEFINITE;

public class World implements Renderable, Updatable {

    private Map<Integer, List<GameObject>> gameObjects = new HashMap<>();
    private final QuadTree quadTree;
    private final Rectangle2D boundary;
    private AudioClip audio = new AudioClip(World.class.getResource("/sounds/background.wav").toString());

    public World(int width, int height) {

        boundary = new Rectangle2D(0, 0, (double) width, (double) height);
        quadTree = new QuadTree(boundary);

        ExecutorService service = Executors.newFixedThreadPool(4);
        service.execute(() -> {

            audio.setVolume(0.5f);
            audio.setCycleCount(INDEFINITE);
            audio.play();
        });
    }

    public void addGameObject(int layer, Sprite gameObject) {

        List<GameObject> layerSpecificGameObjects = gameObjects.getOrDefault(layer, new ArrayList<>());
        layerSpecificGameObjects.add(gameObject);
        gameObjects.putIfAbsent(layer, layerSpecificGameObjects);
        quadTree.insert(gameObject);
    }

    public void addGameObjects(int layer, List<? extends Sprite> gameObjects) {

        List<GameObject> layerSpecificGameObjects = this.gameObjects.getOrDefault(layer, new ArrayList<>());
        layerSpecificGameObjects.addAll(gameObjects);
        this.gameObjects.putIfAbsent(layer, layerSpecificGameObjects);
        gameObjects.forEach(quadTree::insert);
    }

    @Override
    public void render(GraphicsContext gc, double deltaTime) {

        gc.clearRect(boundary.getMinX(), boundary.getMinY(), boundary.getWidth(), boundary.getHeight());
        gameObjects
                .keySet()
                .stream()
                .sorted((f1, f2) -> Integer.compare(f2, f1))
                .forEach(key -> gameObjects.get(key).forEach(gameObject -> {

                    if (gameObject instanceof Renderable)
                        ((Renderable) gameObject).render(gc, deltaTime);
                }));
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

        gameObjects.keySet().forEach(key -> gameObjects.get(key).forEach(gameObject -> {

            if (gameObject instanceof Updatable)
                ((Updatable) gameObject).update(
                        deltaTimeSinceStart,
                        deltaTime,
                        input,
                        gameObject instanceof Sprite ? quadTree.findNearbyGameObjects((Sprite) gameObject) : null
                );
        }));
    }

    public List<Sprite> getAllSprites() {

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

    public double getWidth() {

        return boundary.getWidth();
    }

    public double getHeight() {

        return boundary.getHeight();
    }

    public QuadTree getQuadTree() {

        return quadTree;
    }
}
