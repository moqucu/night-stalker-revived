package org.moqucu.games.nightstalker.model.object.test;

import org.junit.jupiter.api.Test;
import org.moqucu.games.nightstalker.model.object.Scores;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ScoresTest {

    private boolean propertyChangeFired = false;

    @Test
    public void startsWithZeroScores() {

        final Scores scores = new Scores();
        assertThat(scores.getScores(), is(0));
    }

    @Test
    public void settingLivesFiresPropertyChangeEvent() {

        final Scores scores = new Scores();
        scores.addPropertyChangeListener(
                evt -> {
                    if (evt.getPropertyName().equals("scores"))
                        propertyChangeFired = true;
                }
        );
        scores.setScores(500);
        assertThat(propertyChangeFired, is(true));
        assertThat(scores.getScores(), is(500));
    }


}
