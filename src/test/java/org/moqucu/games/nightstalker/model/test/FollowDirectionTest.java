package org.moqucu.games.nightstalker.model.test;

import org.junit.jupiter.api.Test;
import org.moqucu.games.nightstalker.model.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;

public class FollowDirectionTest extends MazeAlgorithmImplTest {

    private final FollowDirection followDirection = new FollowDirection();

    @Test
    public void followDirectionImplementsMazeAlgorithmImpl() {

        assertThat(followDirection, isA(MazeAlgorithmImpl.class));
    }

    @Test
    public void from_32_32_Right_To_64_32_Right() {

        assertThat(
                followDirection.getNextAbsPos(
                        absMazeGraph,
                        new AbsPosAndDirection(new AbsolutePosition(32.0, 32.0), Direction.Right)
                ),
                is(new AbsPosAndDirection(new AbsolutePosition(64.0, 32.0), Direction.Right))
        );
    }

    @Test
    public void from_64_32_Right_To_96_32_Right() {

        assertThat(
                followDirection.getNextAbsPos(
                        absMazeGraph,
                        new AbsPosAndDirection(new AbsolutePosition(64.0, 32.0), Direction.Right)
                ),
                is(new AbsPosAndDirection(new AbsolutePosition(96.0, 32.0), Direction.Right))
        );
    }

    @Test
    public void from_96_32_Right_To_96_32_Right() {

        assertThat(
                followDirection.getNextAbsPos(
                        absMazeGraph,
                        new AbsPosAndDirection(new AbsolutePosition(96.0, 32.0), Direction.Right)
                ),
                is(new AbsPosAndDirection(new AbsolutePosition(96.0, 32.0), Direction.Right))
        );
    }
}