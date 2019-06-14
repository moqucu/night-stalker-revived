package org.moqucu.games.nightstalker.controller;

import javafx.animation.AnimationTimer;
import lombok.extern.log4j.Log4j2;
import org.moqucu.games.nightstalker.sprite.Collidable;
import org.moqucu.games.nightstalker.sprite.Sprite;
import org.moqucu.games.nightstalker.view.Maze;

import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

@Log4j2
public class GameLoop {

    private final long startNanoTime = System.nanoTime();

    private final AtomicLong lastNanoTime = new AtomicLong(System.nanoTime());

    private final AnimationTimer gameLoop;

    public GameLoop(Maze maze) {

        gameLoop = new AnimationTimer() {

            public void handle(long currentNanoTime) {

                /* calculate time since last update */
                double deltaTimeSinceStart = (currentNanoTime - startNanoTime) / 1000000000.0;
                double deltaTime = (currentNanoTime - lastNanoTime.getAndSet(currentNanoTime)) / 1000000000.0;

                log.trace("Delta time since start: {}", deltaTimeSinceStart);
                log.trace("Delta time sincle last call: {}", deltaTime);

                maze.getAllHittableSprites().forEach(

                        hittableSprite -> {
                            Set<Collidable> collidableSprites = maze.getAllCurrentlyCollidableSprites();
                            if (hittableSprite instanceof Collidable)
                                collidableSprites.remove(hittableSprite);
                            collidableSprites.forEach(collidableSprite -> {
                                if (((Sprite)hittableSprite).getBoundsInParent()
                                        .intersects(((Sprite)collidableSprite).getBoundsInParent()))
                                    hittableSprite.hitBy(collidableSprite);

                            });

                        }
                );
            }
        };
    }

    public void start() {

        gameLoop.start();
    }

    public void stop() {

        gameLoop.stop();
    }
}
