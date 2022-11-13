package org.moqucu.games.nightstalker.model.background.test;

import org.junit.jupiter.api.Test;
import org.moqucu.games.nightstalker.model.DisplayableObject;
import org.moqucu.games.nightstalker.model.background.Bunker;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;

public class BunkerTest {

    private final Bunker bunker = new Bunker();

    @Test
    public void bunkerIsInstanceOfGameObject() {

        assertThat(bunker, instanceOf(DisplayableObject.class));
    }
}
