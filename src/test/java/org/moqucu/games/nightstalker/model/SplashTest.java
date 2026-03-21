package org.moqucu.games.nightstalker.model;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;

public class SplashTest {

    @Test
    public void splashInheritsFromDisplayableObject() {

        assertThat(new Splash(), isA(DisplayableObject.class));
    }

    @Test
    public void pointsToCorrectImageMap() {

        assertThat(new Splash().getImageMapFileName(), is("/images/splash.jpeg"));
    }
}
