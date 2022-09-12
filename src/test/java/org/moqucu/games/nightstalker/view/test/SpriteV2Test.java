package org.moqucu.games.nightstalker.view.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.moqucu.games.nightstalker.view.SpriteV2;
import org.testfx.framework.junit5.ApplicationExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;

@ExtendWith(ApplicationExtension.class)
public class SpriteV2Test {

    private final SpriteV2 sprite = new SpriteV2() {
    };

    @Test
    public void hasModelProperty() {

        assertThat(sprite, hasProperty("model"));
    }
}
