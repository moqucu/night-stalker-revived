package org.moqucu.games.nightstalker.utility;

import javafx.animation.AnimationTimer;
import lombok.Getter;
import lombok.Setter;
import org.moqucu.games.nightstalker.model.GameWorld;

import java.util.concurrent.atomic.AtomicLong;

public class GameLoop extends AnimationTimer {

    @Getter
    private final AtomicLong lastNanoTime = new AtomicLong(System.nanoTime());

    @Setter
    private GameWorld gameWorld;

    @Override
    public void handle(long currentNanoTime) {

        double deltaTime = (currentNanoTime - lastNanoTime.getAndSet(currentNanoTime)) / 1000000.0;
        gameWorld.pulse(deltaTime);
    }
}
