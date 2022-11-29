package org.moqucu.games.nightstalker.view.object.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.moqucu.games.nightstalker.model.object.Lives;
import org.moqucu.games.nightstalker.model.object.Weapon;
import org.moqucu.games.nightstalker.view.Sprite;
import org.moqucu.games.nightstalker.view.object.LivesLabel;
import org.testfx.framework.junit5.ApplicationExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(ApplicationExtension.class)
public class LivesLabelTest {

    private final LivesLabel livesLabel = new LivesLabel();

    @Test
    public void livesLabelIsASprite() {

        assertThat(livesLabel, isA(Sprite.class));
    }

    @Test
    public void modelRepresentsLives() {

        assertThat(livesLabel.getModel(), isA(Lives.class));
    }

    @Test
    public void ensureThatWrongModelClassTriggersException() {

        final Weapon weapon = new Weapon();
        Throwable throwable = assertThrows(
                RuntimeException.class,
                () -> livesLabel.setModel(weapon)
        );
        assertThat(throwable.getMessage(), is("Model needs to be of type " +
                "class org.moqucu.games.nightstalker.model.object.Lives but was of type " +
                "class org.moqucu.games.nightstalker.model.object.Weapon!"));
    }

    @Test
    public void correctModelClassWorksFine() {

        final Lives lives = new Lives();
        lives.setLives(10);
        livesLabel.setModel(lives);
        assertThat(livesLabel.getText(), is("10"));
    }

    @Test
    public void testPropertyChangePropagation() {

        final Lives lives = new Lives();
        lives.setLives(15);
        livesLabel.setModel(lives);
        assertThat(livesLabel.getText(), is("15"));
        lives.setLives(20);
        assertThat(livesLabel.getText(), is("20"));
    }
}
