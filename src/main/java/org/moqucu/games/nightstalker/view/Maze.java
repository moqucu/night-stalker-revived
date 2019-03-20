package org.moqucu.games.nightstalker.view;

import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

import java.util.*;
import java.util.concurrent.*;

@Log4j2
public class Maze extends StackPane {

    private final static double PREF_WIDTH = 640d;

    private final static double PREF_HEIGHT = 384d;

    private final ConcurrentMap<AnimatedSprite, AnimatedSprite> animatedSprites = new ConcurrentHashMap<>();

    private final ConcurrentMap<Updatable, Updatable> updatableSprites = new ConcurrentHashMap<>();

    @SneakyThrows
    public Maze() {

        super();
        setId("org.moqucu.games.nightstalker.maze");

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
                            .filtered(node -> node instanceof AnimatedSprite)
                            .forEach(sprite -> {

                                animatedSprites.putIfAbsent((AnimatedSprite) sprite, (AnimatedSprite) sprite);
                                log.debug(
                                        "Added animated sprite of type {} to set.",
                                        sprite.getClass().getName()
                                );
                            });

                    ((Pane) addedPane)
                            .getChildren()
                            .filtered(node -> node instanceof Updatable)
                            .forEach(sprite -> {

                                updatableSprites.putIfAbsent((Updatable) sprite, (Updatable) sprite);
                                log.debug(
                                        "Added updatable sprite of type {} to set.",
                                        sprite.getClass().getName()
                                );
                            });

                    ((Pane) addedPane).getChildren().addListener((ListChangeListener<Node>) change1 -> {
                        change1.next();
                        change1.getAddedSubList().forEach(addedChild -> {

                            if (addedChild instanceof AnimatedSprite) {

                                animatedSprites.putIfAbsent((AnimatedSprite) addedChild, (AnimatedSprite) addedChild);
                                log.debug(
                                        "Added animated sprite of type {} to set.",
                                        addedChild.getClass().getName()
                                );
                            }

                            if (addedChild instanceof Updatable) {

                                updatableSprites.putIfAbsent((Updatable) addedChild, (Updatable) addedChild);
                                log.debug(
                                        "Added updatable sprite of type {} to set.",
                                        addedChild.getClass().getName()
                                );
                            }
                        });
                    });
                }
            });
        });
    }

    public Set<AnimatedSprite> getAllAnimatedSprites() {

        return animatedSprites.keySet();
    }

    public Set<Updatable> getAllUpdatableSprites() {

        return updatableSprites.keySet();
    }
}
