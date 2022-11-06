package org.moqucu.games.nightstalker.utility.test;

import javafx.animation.AnimationTimer;
import org.junit.jupiter.api.Test;
import org.moqucu.games.nightstalker.model.GameWorld;
import org.moqucu.games.nightstalker.utility.GameLoop;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;

public class GameLoopTest {

    private final GameLoop gameLoop = new GameLoop();

    @Test
    public void gameLoopIsAnAnimationTimer() {

        assertThat(gameLoop, isA(AnimationTimer.class));
    }


    @Test
    public void timeIsTrackedCorrectly() {

        final long lastNanoTime = gameLoop.getLastNanoTime().get();
        final GameWorld gameWorld = new GameWorld();
        final double gameTime = gameWorld.getTime();

        gameLoop.setGameWorld(gameWorld);
        gameLoop.handle(lastNanoTime + 1234);

        assertThat(gameLoop.getLastNanoTime().get(), is(lastNanoTime + 1234));
        assertThat(gameWorld.getTime(), is(gameTime + 1234/1000000.0));
    }
}
