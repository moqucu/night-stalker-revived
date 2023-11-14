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
    public void from_70_32_Right_To_96_32_Right() {

        assertThat(
                followDirection.getNextAbsPos(
                        absMazeGraph,
                        new AbsPosAndDirection(new AbsolutePosition(70.0, 32.0), Direction.Right)
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

    @Test
    public void from_32_00_Top_To_32_00_Top() {

        assertThat(
                followDirection.getNextAbsPos(
                        absMazeGraph,
                        new AbsPosAndDirection(new AbsolutePosition(32.0, 00.0), Direction.Up)
                ),
                is(new AbsPosAndDirection(new AbsolutePosition(32.0, 00.0), Direction.Up))
        );
    }

    @Test
    public void from_68_32_Up_To_96_32_Up() {

        assertThat(
                followDirection.getNextAbsPos(
                        absMazeGraph,
                        new AbsPosAndDirection(new AbsolutePosition(68.0, 32.0), Direction.Up)
                ),
                is(new AbsPosAndDirection(new AbsolutePosition(96.0, 32.0), Direction.Up))
        );
    }

    @Test
    public void from_40_32_Down_To_32_32_Down() {

        assertThat(
                followDirection.getNextAbsPos(
                        absMazeGraph,
                        new AbsPosAndDirection(new AbsolutePosition(40.0, 32.0), Direction.Down)
                ),
                is(new AbsPosAndDirection(new AbsolutePosition(32.0, 32.0), Direction.Down))
        );
    }
}
