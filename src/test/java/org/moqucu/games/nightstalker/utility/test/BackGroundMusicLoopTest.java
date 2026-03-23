package org.moqucu.games.nightstalker.utility.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.moqucu.games.nightstalker.utility.BackGroundMusicLoop;

import javax.sound.sampled.Clip;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class BackGroundMusicLoopTest {

    private int testFlags = 0;
    private final BackGroundMusicLoop backGroundMusicLoop;

    public BackGroundMusicLoopTest() {

        Clip clip = mock(Clip.class);
        Mockito
                .lenient()
                .doAnswer(invocation -> testFlags |= 0B00000001)
                .when(clip)
                .setFramePosition(0);
        Mockito
                .lenient()
                .doAnswer(invocation -> testFlags |= 0B00000010)
                .when(clip)
                .loop(Clip.LOOP_CONTINUOUSLY);
        Mockito
                .lenient()
                .doAnswer(invocation -> testFlags |= 0B00000100)
                .when(clip)
                .stop();

        backGroundMusicLoop = new BackGroundMusicLoop(clip);
    }

    @Test
    public void testCall() {

        final int testFlags = this.testFlags & 0B00000011;
        assertThat(testFlags, is(0));
        backGroundMusicLoop.publicCall();
        assertThat(this.testFlags, is(3));
    }
}
