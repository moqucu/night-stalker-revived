package org.moqucu.games.nightstalker.controller.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.moqucu.games.nightstalker.controller.GameController;
import org.moqucu.games.nightstalker.controller.GameScreenController;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class GameScreenControllerTest {

    private final GameScreenController gameScreenController;

    public GameScreenControllerTest() {

        GameController gameController = mock(GameController.class);
        lenient()
                .when(gameController.switchScene(any()))
                .thenThrow(new RuntimeException("Switch scene!"));

        gameScreenController = new GameScreenController(gameController);
    }

    @Test
    public void testOnMouseClickedEvenSwitchScene() {

        final Throwable throwable
                = assertThrows(RuntimeException.class, () -> gameScreenController.onMouseClicked(null));
        assertThat(throwable.getMessage(), is("Switch scene!"));
    }
}
