package org.moqucu.games.nightstalker.model.object;

import lombok.Getter;
import org.moqucu.games.nightstalker.model.GameObject;

@Getter
public class Scores extends GameObject {

    private int scores = 0;

    public void setScores(int scores) {

        final int oldScores = this.scores;
        this.scores = scores;

        propertyChangeSupport.firePropertyChange("scores", oldScores, scores);
    }
}
