package org.moqucu.games.nightstalker.model.test;

import org.junit.jupiter.api.Test;
import org.moqucu.games.nightstalker.model.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class MazeAlgorithmFactoryTest {

    private final MazeAlgorithmFactory mazeAlgorithmFactory = MazeAlgorithmFactory.getInstance();

    @Test
    public void whenConfrontedWithNoneEnumItReturnsNull() {

        assertThat(mazeAlgorithmFactory.createMazeAlgorithm(MazeAlgorithm.None), nullValue(MazeAlgorithmImpl.class));
    }

    @Test
    public void whenConfrontedWithOuterRingEnumItReturnsOuterRing() {

        assertThat(mazeAlgorithmFactory.createMazeAlgorithm(MazeAlgorithm.OuterRing), isA(OuterRing.class));
    }

    @Test
    public void whenConfrontedWithRandomEnumItReturnsRandom() {

        assertThat(mazeAlgorithmFactory.createMazeAlgorithm(MazeAlgorithm.Random), isA(Random.class));
    }

    @Test
    public void whenConfrontedWithFollowDirectionEnumItReturnsFollowDirection() {

        assertThat(mazeAlgorithmFactory.createMazeAlgorithm(MazeAlgorithm.FollowDirection), isA(FollowDirection.class));
    }
}
