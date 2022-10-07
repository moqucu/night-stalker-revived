package org.moqucu.games.nightstalker.view;

import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.moqucu.games.nightstalker.model.Bullet;
import org.moqucu.games.nightstalker.sprite.AnimatedSprite;
import org.moqucu.games.nightstalker.sprite.Approachable;
import org.moqucu.games.nightstalker.sprite.Collidable;
import org.moqucu.games.nightstalker.model.Hittable;
import org.moqucu.games.nightstalker.sprite.object.BulletSprite;
import org.moqucu.games.nightstalker.sprite.enemy.GreyRobot;
import org.moqucu.games.nightstalker.sprite.hero.NightStalker;
import org.moqucu.games.nightstalker.sprite.object.RobotBullet;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * Game maze represented as a JavaX stack pane. Keeps track of all added collidable sprites, hittable sprites,
 * approachable sprites, and bullets. Provides service methods for accessing any of these.
 */
@Log4j2
public class Maze extends StackPane {

    public final static double PREF_WIDTH = 640d;

    public final static double PREF_HEIGHT = 384d;

    public static final String ID = "org.moqucu.games.nightstalker.maze";

    private final ConcurrentMap<Collidable, Collidable> collidableSprites = new ConcurrentHashMap<>();

    private final ConcurrentMap<Hittable, Hittable> hittableSprites = new ConcurrentHashMap<>();

    private final ConcurrentMap<Approachable, Approachable> approachableSprites = new ConcurrentHashMap<>();

    private final ConcurrentMap<Bullet, Bullet> bullets = new ConcurrentHashMap<>();

    private final ConcurrentMap<GreyRobot, GreyRobot> robots = new ConcurrentHashMap<>();

    private NightStalker player;

    private final ConcurrentMap<RobotBullet, RobotBullet> robotBullets = new ConcurrentHashMap<>();

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
                            .filtered(node -> node instanceof Approachable)
                            .forEach(sprite -> {

                                approachableSprites.putIfAbsent((Approachable) sprite, (Approachable) sprite);
                                log.debug(
                                        "Added approachable sprite of type {} to set.",
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

                    ((Pane) addedPane)
                            .getChildren()
                            .filtered(node -> node instanceof GreyRobot)
                            .forEach(sprite -> {

                                robots.putIfAbsent((GreyRobot) sprite, (GreyRobot) sprite);
                                log.debug(
                                        "Added ArtificiallyMovableSprite of type {} to set.",
                                        sprite.getClass().getName()
                                );
                            });

                    ((Pane) addedPane)
                            .getChildren()
                            .filtered(node -> node instanceof NightStalker)
                            .forEach(sprite -> {

                                player = (NightStalker) sprite;
                                log.debug(
                                        "Added player of type {} to set.",
                                        sprite.getClass().getName()
                                );
                            });

                    ((Pane) addedPane)
                            .getChildren()
                            .filtered(node -> node instanceof RobotBullet)
                            .forEach(sprite -> {

                                robotBullets.putIfAbsent((RobotBullet) sprite, (RobotBullet) sprite);
                                log.debug(
                                        "Added ArtificiallyMovableSprite of type {} to set.",
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

                            if (addedChild instanceof Approachable) {

                                approachableSprites.putIfAbsent((Approachable) addedChild, (Approachable) addedChild);
                                log.debug(
                                        "Added approachable sprite of type {} to set.",
                                        addedChild.getClass().getName()
                                );
                            }

                            if (addedChild instanceof BulletSprite) {

                                bullets.putIfAbsent((BulletSprite) addedChild, (BulletSprite) addedChild);
                                log.debug(
                                        "Added bullet of type {} to set.",
                                        addedChild.getClass().getName()
                                );
                            }
                            if (addedChild instanceof GreyRobot) {
                                robots.putIfAbsent((GreyRobot) addedChild, (GreyRobot) addedChild);
                                log.debug(
                                        "Added GreyRobot of type {} to set.",
                                        addedChild.getClass().getName()
                                );
                            }
                            if (addedChild instanceof NightStalker) {

                                player = (NightStalker) addedChild;
                                log.debug(
                                        "Added NightStalker of type {} to set.",
                                        addedChild.getClass().getName()
                                );
                            }
                            if (addedChild instanceof RobotBullet) {
                                robotBullets.putIfAbsent((RobotBullet) addedChild, (RobotBullet) addedChild);
                                log.debug(
                                        "Added RobotBullet of type {} to set.",
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

    public Set<Approachable> getAllApproachableSprites() {

        return new HashSet<>(approachableSprites
                .keySet());
    }

    public Set<Bullet> getAllBullets() {

        return bullets.keySet();
    }

    public Set<GreyRobot> getAllRobots() {
        return robots.keySet();
    }

    public NightStalker getPlayer() {
        return player;
    }

    public Set<RobotBullet> getAllRobotBullets() {
        return robotBullets.keySet();
    }

    public Set<SpriteV2> getSprites() {

        return sprites.keySet();
    }
}
