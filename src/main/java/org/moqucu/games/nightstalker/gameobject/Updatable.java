package org.moqucu.games.nightstalker.gameobject;

import javafx.scene.input.KeyCode;

import java.util.List;
import java.util.Set;

public interface Updatable {

    void update(double deltaTimeSinceStart, double deltaTime, Set<KeyCode> input, List<Sprite> nearbySprites);
}