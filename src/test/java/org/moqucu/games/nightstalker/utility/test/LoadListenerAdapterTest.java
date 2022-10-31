package org.moqucu.games.nightstalker.utility.test;

import javafx.fxml.LoadListener;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.moqucu.games.nightstalker.controller.GameController;
import org.moqucu.games.nightstalker.utility.LoadListenerAdapter;
import org.moqucu.games.nightstalker.view.Sprite;
import org.moqucu.games.nightstalker.view.enemy.SpiderSprite;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class LoadListenerAdapterTest {

    private final LoadListener loadListener;

    public LoadListenerAdapterTest() {

        final GameController gameController = mock(GameController.class);
        Mockito
                .lenient()
                .doThrow(new RuntimeException("Hello!"))
                .when(gameController)
                .addSprite(any(Sprite.class));
        loadListener = new LoadListenerAdapter(gameController);
    }

    @Test
    public void testLoadListenerAdapter() {

        Throwable throwable = assertThrows(
                RuntimeException.class,
                () -> loadListener.endElement(new SpiderSprite())
        );
        assertThat(throwable.getMessage(), is("Hello!"));
    }
}
