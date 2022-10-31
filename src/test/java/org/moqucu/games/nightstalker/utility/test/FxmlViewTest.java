package org.moqucu.games.nightstalker.utility.test;

import org.junit.jupiter.api.Test;
import org.moqucu.games.nightstalker.utility.FxmlView;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class FxmlViewTest {

    private final FxmlView splashScreen = FxmlView.SPLASH_SCREEN;
    private final FxmlView gameScreen = FxmlView.GAME_SCREEN;

    @Test
    public void testSplashScreenTitle() {

        assertThat(splashScreen.getTitle(), is("Night Stalker Revived"));
    }

    @Test
    public void testSplashScreenFxmlLocation() {

        assertThat(splashScreen.getFxmlFile(), is("/fxml/SplashScreen.fxml"));
    }

    @Test
    public void testGameScreenTitle() {

        assertThat(gameScreen.getTitle(), is("Now playing Night Stalker Revived"));
    }

    @Test
    public void testGameScreenFxmlLocation() {

        assertThat(gameScreen.getFxmlFile(), is("/fxml/GameScreen.fxml"));
    }

}
