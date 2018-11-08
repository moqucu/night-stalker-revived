package model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class World implements Renderable, Updatable {

    private List<GameObject> gameObjects = new ArrayList<>();

    public World() {

    }

    public void addGameObject(GameObject gameObject) {

        gameObjects.add(gameObject);
    }

    public void addGameObjects(List<? extends GameObject> gameObjects) {

        this.gameObjects.addAll(gameObjects);
    }


    @Override
    public void render(GraphicsContext gc, double interpolation) {


        gameObjects.forEach(gameObject -> {

            if (gameObject instanceof Renderable)
                ((Renderable) gameObject).render(gc, interpolation);
        });

    }

    @Override
    public void update(Set<KeyCode> input) {

    }
}
