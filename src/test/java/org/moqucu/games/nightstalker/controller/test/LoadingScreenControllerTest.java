package org.moqucu.games.nightstalker.controller.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.moqucu.games.nightstalker.controller.GameController;
import org.moqucu.games.nightstalker.controller.LoadingScreenController;
import org.moqucu.games.nightstalker.utility.FxmlView;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class LoadingScreenControllerTest {

    private final LoadingScreenController loadingScreenController;

    public LoadingScreenControllerTest() {

        GameController gameController = mock(GameController.class);

        Mockito
                .lenient()
                .when(gameController.switchScene(FxmlView.SPLASH_SCREEN))
                .thenThrow(new RuntimeException("Switched to SPLASH_SCREEN!"));

        loadingScreenController = new LoadingScreenController(gameController);
    }

    @Test
    public void testTransitionToSplashScreen() {

        final Throwable throwable = assertThrows(
                RuntimeException.class,
                () -> loadingScreenController.transitionToSplashScreen()
        );
        assertThat(throwable.getMessage(), is("Switched to SPLASH_SCREEN!"));
    }
}
