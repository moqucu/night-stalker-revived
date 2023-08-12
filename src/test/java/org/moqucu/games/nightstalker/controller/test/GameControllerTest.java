package org.moqucu.games.nightstalker.controller.test;

import javafx.stage.Stage;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.moqucu.games.nightstalker.controller.GameController;
import org.moqucu.games.nightstalker.model.GameWorld;
import org.moqucu.games.nightstalker.utility.FxmlView;
import org.moqucu.games.nightstalker.utility.GameLoop;
import org.moqucu.games.nightstalker.utility.SystemWrapper;
import org.moqucu.games.nightstalker.view.enemy.SpiderSprite;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

@ExtendWith(ApplicationExtension.class)
public class GameControllerTest {

    private int testFlags = 0;

    private GameController gameController;

    private final Stage stage;

    private final SystemWrapper systemWrapper;

    private final GameLoop gameLoop;

    private final GameWorld gameWorld;

    @Start
    private void start(Stage stage) {

        gameController = new GameController(this.stage, systemWrapper, null, gameLoop, gameWorld);
    }

    @SneakyThrows
    public GameControllerTest() {

        stage = mock(Stage.class);
        Mockito
                .lenient()
                .doAnswer(invocation -> testFlags |= 0B000000001)
                .when(stage)
                .setTitle(any());
        Mockito
                .lenient()
                .doAnswer(invocation -> testFlags |= 0B000000010)
                .when(stage)
                .setScene(any());
        Mockito
                .lenient()
                .doAnswer(invocation -> testFlags |= 0B000000100)
                .when(stage)
                .sizeToScene();
        Mockito
                .lenient()
                .doAnswer(invocation -> testFlags |= 0B000001000)
                .when(stage)
                .centerOnScreen();
        Mockito
                .lenient()
                .doAnswer(invocation -> testFlags |= 0B000010000)
                .when(stage)
                .show();

        systemWrapper = mock(SystemWrapper.class);
        Mockito
                .lenient()
                .doAnswer(invocation -> testFlags |= 0B000100000)
                .when(systemWrapper)
                .exit(0);

        gameLoop = mock(GameLoop.class);
        Mockito
                .lenient()
                .doAnswer(invocation -> testFlags |= 0B001000000)
                .when(gameLoop)
                .start();
        Mockito
                .lenient()
                .doAnswer(invocation -> testFlags |= 0B010000000)
                .when(gameLoop)
                .stop();

        gameWorld = mock(GameWorld.class);
        Mockito
                .lenient()
                .doAnswer(invocation -> testFlags |= 0B100000000)
                .when(gameWorld)
                .reset();
    }

    @Test
    public void testSwitchScene() {

        this.testFlags = 0;
        gameController.switchScene(FxmlView.GAME_SCREEN);
        assertThat(this.testFlags, is(31));
        assertThat(gameController.getScenes().size(), is(2));
    }

    @Test
    public void testEndGame() {

        this.testFlags = 0;
        gameController.endGame();
        assertThat(this.testFlags, is(32));
    }

    @Test
    public void testStartGameLoop() {

        this.testFlags = 0;
        gameController.startGameLoop();
        assertThat(this.testFlags, is(64));
    }

    @Test
    public void testStopGameLoop() {

        this.testFlags = 0;
        gameController.stopGameLoop();
        assertThat(this.testFlags, is(128));
    }

    @Test
    public void testAddSprite() {

        final SpiderSprite spiderSprite = new SpiderSprite();
        gameController.addSprite(spiderSprite);
        assertThat(
                gameController.getGameWorld().getObjects().containsValue(spiderSprite.getModel()),
                is(true)
        );
        assertThat(
                gameController.getGameElements().containsKey(spiderSprite),
                is(true)
        );
    }

    @Test
    public void testResetGameWorld() {

        this.testFlags = 0;
        gameController.resetGameWorld();
        assertThat(this.testFlags, is(256));
    }
}
