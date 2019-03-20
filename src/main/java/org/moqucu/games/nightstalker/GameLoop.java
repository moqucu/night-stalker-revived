package org.moqucu.games.nightstalker;

import javafx.animation.AnimationTimer;
import org.moqucu.games.nightstalker.view.Maze;

import java.util.concurrent.atomic.AtomicLong;

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

                maze.getAllUpdatableSprites().forEach(
                        updatableSprite -> updatableSprite.update(
                                deltaTimeSinceStart,
                                deltaTime,
                                null,
                                maze.getAllAnimatedSprites()
                        )
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
