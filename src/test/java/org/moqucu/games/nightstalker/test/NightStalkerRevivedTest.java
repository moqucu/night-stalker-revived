package org.moqucu.games.nightstalker.test;

import javafx.application.Application;
import org.junit.jupiter.api.Test;
import org.moqucu.games.nightstalker.NightStalkerRevived;
import org.moqucu.games.nightstalker.controller.GameController;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class NightStalkerRevivedTest {

    private final NightStalkerRevived game = new NightStalkerRevived();

    @Test
    public void isInstanceOfApplication() {

        assertThat(game, instanceOf(Application.class));
    }

    @Test
    public void testStartMethod() {

        game.start(null);
        assertThat(game.getGameController(), instanceOf(GameController.class));
    }
}
