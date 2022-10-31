package org.moqucu.games.nightstalker.utility.test;

import javafx.scene.media.AudioClip;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.moqucu.games.nightstalker.utility.BackGroundMusicLoop;

import static javafx.scene.media.AudioClip.INDEFINITE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class BackGroundMusicLoopTest {

    private int testFlags = 0;
    private final BackGroundMusicLoop backGroundMusicLoop;

    public BackGroundMusicLoopTest() {

        AudioClip audioClip = mock(AudioClip.class);
        Mockito
                .lenient()
                .doAnswer(invocation -> testFlags |= 0B00000001)
                .when(audioClip)
                .setVolume(0.5f);
        Mockito
                .lenient()
                .doAnswer(invocation -> testFlags |= 0B00000010)
                .when(audioClip)
                .setCycleCount(INDEFINITE);
        Mockito
                .lenient()
                .doAnswer(invocation -> testFlags |= 0B00000100)
                .when(audioClip)
                .play();
        Mockito
                .lenient()
                .doAnswer(invocation -> testFlags |= 0B00001000)
                .when(audioClip)
                .stop();

        backGroundMusicLoop = new BackGroundMusicLoop(audioClip);
    }

    @Test
    public void testCall() {

        final int testFlags = this.testFlags & 0B00000111;
        assertThat(testFlags, is(0));
        backGroundMusicLoop.publicCall();
        // assertThat(testFlags, is(7));
    }
}
