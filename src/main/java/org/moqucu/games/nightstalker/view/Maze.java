package org.moqucu.games.nightstalker.view;

import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.moqucu.games.nightstalker.sprite.AnimatedSprite;
import org.moqucu.games.nightstalker.sprite.Collidable;
import org.moqucu.games.nightstalker.sprite.Hittable;
import org.moqucu.games.nightstalker.sprite.object.Bullet;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Log4j2
public class Maze extends StackPane {

    private final static double PREF_WIDTH = 640d;

    private final static double PREF_HEIGHT = 384d;

    private final ConcurrentMap<Collidable, Collidable> collidableSprites = new ConcurrentHashMap<>();

    private final ConcurrentMap<Hittable, Hittable> hittableSprites = new ConcurrentHashMap<>();

    private final ConcurrentMap<Bullet, Bullet> bullets = new ConcurrentHashMap<>();

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
                            .filtered(node -> node instanceof Collidable)
                            .forEach(sprite -> {

                                collidableSprites.putIfAbsent((Collidable) sprite, (Collidable) sprite);
                                log.debug(
                                        "Added collidable sprite of type {} to set.",
                                        sprite.getClass().getName()
                                );
                            });

                    ((Pane) addedPane)
                            .getChildren()
                            .filtered(node -> node instanceof Hittable)
                            .forEach(sprite -> {

                                hittableSprites.putIfAbsent((Hittable) sprite, (Hittable) sprite);
                                log.debug(
                                        "Added hittable sprite of type {} to set.",
                                        sprite.getClass().getName()
                                );
                            });

                    ((Pane) addedPane)
                            .getChildren()
                            .filtered(node -> node instanceof Bullet)
                            .forEach(sprite -> {

                                bullets.putIfAbsent((Bullet) sprite, (Bullet) sprite);
                                log.debug(
                                        "Added bullet of type {} to set.",
                                        sprite.getClass().getName()
                                );
                            });


                    ((Pane) addedPane).getChildren().addListener((ListChangeListener<Node>) change1 -> {
                        change1.next();
                        change1.getAddedSubList().forEach(addedChild -> {

                            if (addedChild instanceof AnimatedSprite) {

                                collidableSprites.putIfAbsent((Collidable) addedChild, (Collidable) addedChild);
                                log.debug(
                                        "Added collidable sprite of type {} to set.",
                                        addedChild.getClass().getName()
                                );
                            }

                            if (addedChild instanceof Hittable) {

                                hittableSprites.putIfAbsent((Hittable) addedChild, (Hittable) addedChild);
                                log.debug(
                                        "Added hittable sprite of type {} to set.",
                                        addedChild.getClass().getName()
                                );
                            }

                            if (addedChild instanceof Bullet) {

                                bullets.putIfAbsent((Bullet) addedChild, (Bullet) addedChild);
                                log.debug(
                                        "Added bullet of type {} to set.",
                                        addedChild.getClass().getName()
                                );
                            }
                        });
                    });
                }
            });
        });
    }

    public Set<Collidable> getAllCurrentlyCollidableSprites() {

        return collidableSprites
                .keySet()
                .stream()
                .filter(Collidable::isCollidable)
                .collect(Collectors.toSet());
    }

    public Set<Hittable> getAllHittableSprites() {

        return hittableSprites
                .keySet()
                .stream()
                .filter(Hittable::isHittable)
                .collect(Collectors.toSet());
    }

    public Set<Bullet> getAllBullets() {

        return bullets.keySet();
    }
}
