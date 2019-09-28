package org.moqucu.games.nightstalker.test;

import org.junit.jupiter.api.Test;
import org.moqucu.games.nightstalker.sprite.Sprite;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;

public class SpriteTest {

    private final Sprite sprite = new Sprite() {
    };

    private final Random random = new Random();

    @Test
    void spriteShallContainStillImageIndexProperty() {

        // Given

        int randomIndex = random.nextInt();

        // When

        sprite.setStillImageIndex(randomIndex);

        // Then
        assertEquals(randomIndex, sprite.getStillImageIndex());
    }

    @Test
    void thisTestShallFail() {

        // Then

        fail();
    }
}
