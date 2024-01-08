package org.moqucu.games.nightstalker.model.object;

import lombok.Getter;
import org.moqucu.games.nightstalker.model.GameObject;

@Getter
public class Lives extends GameObject {

    private int lives = 6;

    public void setLives(int lives) {

        final int oldLives = this.lives;
        this.lives = lives;

        propertyChangeSupport.firePropertyChange("lives", oldLives, lives);
    }
}
