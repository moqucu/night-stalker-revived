package model;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.media.AudioClip;

import java.util.*;
import java.util.concurrent.*;

import static javafx.scene.media.AudioClip.INDEFINITE;

public class World implements Renderable, Updatable {

    private final ConcurrentMap<Integer, List<GameObject>> allGameObjects = new ConcurrentHashMap<>();

    private final CopyOnWriteArrayList<Sprite> movableSprites = new CopyOnWriteArrayList<>();

    private final QuadTree unmovableSprites;
    private final Rectangle2D boundary;
    private AudioClip audio = new AudioClip(World.class.getResource("/sounds/background.wav").toString());

    public World(int width, int height) {

        boundary = new Rectangle2D(0, 0, (double) width, (double) height);
        unmovableSprites = new QuadTree(boundary);
        unmovableSprites.clear();

        ExecutorService service = Executors.newFixedThreadPool(4);
        service.execute(() -> {

            audio.setVolume(0.5f);
            audio.setCycleCount(INDEFINITE);
            audio.play();
        });
    }

    public void addGameObject(int layer, Sprite gameObject) {

        List<GameObject> layerSpecificGameObjects = allGameObjects.getOrDefault(layer, new ArrayList<>());
        layerSpecificGameObjects.add(gameObject);
        allGameObjects.putIfAbsent(layer, layerSpecificGameObjects);

        if (gameObject instanceof MovableSprite)
            movableSprites.add(gameObject);
        else
            unmovableSprites.insert(gameObject);
    }

    public void addGameObjects(int layer, List<? extends Sprite> gameObjects) {

        List<GameObject> layerSpecificGameObjects = this.allGameObjects.getOrDefault(layer, new ArrayList<>());
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
    public void render(GraphicsContext gc, double deltaTime) {

        gc.clearRect(boundary.getMinX(), boundary.getMinY(), boundary.getWidth(), boundary.getHeight());
        allGameObjects
                .keySet()
                .stream()
                .sorted((f1, f2) -> Integer.compare(f2, f1))
                .forEach(key -> allGameObjects.get(key).forEach(gameObject -> {

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

    public double getWidth() {

        return boundary.getWidth();
    }

    public double getHeight() {

        return boundary.getHeight();
    }
}
