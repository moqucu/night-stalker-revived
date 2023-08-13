package org.moqucu.games.nightstalker.controller.test;

import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.moqucu.games.nightstalker.controller.GameController;
import org.moqucu.games.nightstalker.controller.GameScreenController;
import org.moqucu.games.nightstalker.utility.GameLoop;
import org.moqucu.games.nightstalker.utility.SystemWrapper;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

@ExtendWith(MockitoExtension.class)
public class GameScreenControllerTest {

    private int testFlags = 0;
    private final GameScreenController gameScreenController;

    public GameScreenControllerTest() {

        Stage stage = mock(Stage.class);

        GameController gameController = spy(new GameController(stage, new SystemWrapper(), null, new GameLoop()));
        Mockito
                .lenient()
                .doAnswer(invocation -> testFlags |= 0B00000001)
                .when(gameController)
                .switchScene(any());
        Mockito
                .lenient()
                .doAnswer(invocation -> testFlags |= 0B00000010)
                .when(gameController)
                .resetGameWorld();

        gameScreenController = new GameScreenController(gameController);
    }

    @Test
    public void testSwitchScene() {

        final int testFlags = this.testFlags & 0B00000001;
        assertThat(testFlags, is(0));
        gameScreenController.onMouseClicked(null);
        assertThat(this.testFlags, is(1));
    }
}
