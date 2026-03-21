package org.moqucu.games.nightstalker.view.test;

import org.junit.jupiter.api.Test;
import org.moqucu.games.nightstalker.model.Splash;
import org.moqucu.games.nightstalker.view.DisplayableSprite;
import org.moqucu.games.nightstalker.view.SplashSprite;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isA;

public class SplashSpriteTest {

    @Test
    public void splashSpriteIsOfTypeSprite() {

        assertThat(new SplashSprite(), isA(DisplayableSprite.class));
    }

    @Test
    public void modelIsOfTypeSplash() {

        assertThat(new SplashSprite().getModel(), isA(Splash.class));
    }
}
