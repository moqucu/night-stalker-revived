package org.moqucu.games.nightstalker.model.object.test;

import org.junit.jupiter.api.Test;
import org.moqucu.games.nightstalker.model.object.Lives;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class LivesTest {

    private boolean propertyChangeFired = false;

    @Test
    public void startsWithSixLives() {

        final Lives lives = new Lives();
        assertThat(lives.getLives(), is(6));
    }

    @Test
    public void settingLivesFiresPropertyChangeEvent() {

        final Lives lives = new Lives();
        lives.addPropertyChangeListener(
                evt -> {
                    if (evt.getPropertyName().equals("lives"))
                        propertyChangeFired = true;
                }
        );
        lives.setLives(5);
        assertThat(propertyChangeFired, is(true));
        assertThat(lives.getLives(), is(5));
    }


}
