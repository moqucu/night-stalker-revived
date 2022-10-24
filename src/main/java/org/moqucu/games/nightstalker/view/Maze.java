package org.moqucu.games.nightstalker.view;

import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

import java.util.*;
import java.util.concurrent.*;

/**
 * Game maze represented as a JavaX stack pane. Keeps track of all added collidable sprites, hittable sprites,
 * approachable sprites, and bullets. Provides service methods for accessing any of these.
 */
@Log4j2
public class Maze extends StackPane {

    public final static double PREF_WIDTH = 640d;

    public final static double PREF_HEIGHT = 384d;

    public static final String ID = "org.moqucu.games.nightstalker.maze";

    private final ConcurrentMap<SpriteV2, SpriteV2> sprites = new ConcurrentHashMap<>();

    @SneakyThrows
    public Maze() {

        super();
        setId(ID);

        setWidth(PREF_WIDTH);
        setPrefWidth(PREF_WIDTH);
        setHeight(PREF_HEIGHT);
        setPrefHeight(PREF_HEIGHT);

        this.getChildren().addListener((ListChangeListener<Node>) change -> {
            change.next();
            change.getAddedSubList().forEach(addedPane -> {
                if (addedPane instanceof Pane) {

                    ((Pane) addedPane)
                            .getChildren()
                            .filtered(node -> node instanceof SpriteV2)
                            .forEach(sprite -> {

                                sprites.putIfAbsent(((SpriteV2) sprite), ((SpriteV2) sprite));
                                log.debug(
                                        "Added game object of type {} to set.",
                                        sprite.getClass().getName()
                                );
                            });
                }
            });
        });
    }

    public Set<SpriteV2> getSprites() {

        return sprites.keySet();
    }
}
