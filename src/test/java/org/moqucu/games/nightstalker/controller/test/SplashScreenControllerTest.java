package org.moqucu.games.nightstalker.controller.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.moqucu.games.nightstalker.controller.GameController;
import org.moqucu.games.nightstalker.controller.SplashScreenController;
import org.moqucu.games.nightstalker.utility.FxmlView;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class SplashScreenControllerTest {

    private int testFlags = 0;
    private final SplashScreenController splashScreenController;

    public SplashScreenControllerTest() {

        GameController gameController = mock(GameController.class);

        Mockito
                .lenient()
                .doAnswer(invocation -> testFlags |= 0B00000001)
                .when(gameController)
                .switchScene(FxmlView.GAME_SCREEN);

        Mockito
                .lenient()
                .doAnswer(invocation -> testFlags |= 0B00000010)
                .when(gameController)
                .stopGameLoop();

        Mockito
                .lenient()
                .doAnswer(invocation -> testFlags |= 0B00000100)
                .when(gameController)
                .endGame();

        Mockito
                .lenient()
                .doAnswer(invocation -> testFlags |= 0B00001000)
                .when(gameController)
                .startGameLoop();

        splashScreenController = new SplashScreenController(gameController);
    }

    @Test
    public void testQuitButtonPressed() {

        this.testFlags = 0;
        splashScreenController.quitButtonPressed(null);
        assertThat(this.testFlags, is(0B00000110));
    }

    @Test
    public void testPlayButtonPressed() {

        this.testFlags = 0;
        splashScreenController.playButtonPressed(null);
        assertThat(this.testFlags, is(0B00001001));
    }
}
