package org.moqucu.games.nightstalker.controller;

import javafx.animation.AnimationTimer;
import lombok.extern.log4j.Log4j2;
import org.moqucu.games.nightstalker.model.GameWorld;
import org.moqucu.games.nightstalker.view.Maze;

import java.util.concurrent.atomic.AtomicLong;

@Log4j2
public class GameLoop {

    private final long startNanoTime = System.nanoTime();

    private final AtomicLong lastNanoTime = new AtomicLong(System.nanoTime());

    private final AnimationTimer gameLoop;

    public GameLoop(Maze maze, GameWorld gameWorld) {

        maze.getSprites().forEach(sprite -> gameWorld.add(sprite.getModel()));

        gameLoop = new AnimationTimer() {

            public void handle(long currentNanoTime) {
                /* calculate time since last update */
                double deltaTimeSinceStart = (currentNanoTime - startNanoTime) / 1000000.0;
                double deltaTime = (currentNanoTime - lastNanoTime.getAndSet(currentNanoTime)) / 1000000.0;

                gameWorld.pulse(deltaTime);

                log.trace("Delta time since start: {}", deltaTimeSinceStart);
                log.trace("Delta time since last call: {}", deltaTime);
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
