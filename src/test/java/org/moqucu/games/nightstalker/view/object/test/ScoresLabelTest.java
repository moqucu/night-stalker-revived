package org.moqucu.games.nightstalker.view.object.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.moqucu.games.nightstalker.model.object.Scores;
import org.moqucu.games.nightstalker.model.object.Weapon;
import org.moqucu.games.nightstalker.view.Sprite;
import org.moqucu.games.nightstalker.view.object.ScoresLabel;
import org.testfx.framework.junit5.ApplicationExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(ApplicationExtension.class)
public class ScoresLabelTest {

    private final ScoresLabel scoresLabel = new ScoresLabel();

    @Test
    public void scoresLabelIsASprite() {

        assertThat(scoresLabel, isA(Sprite.class));
    }

    @Test
    public void modelRepresentsScores() {

        assertThat(scoresLabel.getModel(), isA(Scores.class));
    }

    @Test
    public void ensureThatWrongModelClassTriggersException() {

        final Weapon weapon = new Weapon();
        Throwable throwable = assertThrows(
                RuntimeException.class,
                () -> scoresLabel.setModel(weapon)
        );
        assertThat(throwable.getMessage(), is("Model needs to be of type " +
                "class org.moqucu.games.nightstalker.model.object.Scores but was of type " +
                "class org.moqucu.games.nightstalker.model.object.Weapon!"));
    }

    @Test
    public void correctModelClassWorksFine() {

        final Scores scores = new Scores();
        scores.setScores(1005);
        scoresLabel.setModel(scores);
        assertThat(scoresLabel.getText(), is("1005"));
    }

    @Test
    public void testPropertyChangePropagation() {

        final Scores scores = new Scores();
        scores.setScores(1500);
        scoresLabel.setModel(scores);
        assertThat(scoresLabel.getText(), is("1500"));
        scores.setScores(2000);
        assertThat(scoresLabel.getText(), is("2000"));
    }
}
