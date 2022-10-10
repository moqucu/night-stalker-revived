package org.moqucu.games.nightstalker.model.enemy.test;

import org.junit.jupiter.api.Test;
import org.moqucu.games.nightstalker.model.Direction;
import org.moqucu.games.nightstalker.model.MazeAlgorithm;
import org.moqucu.games.nightstalker.model.enemy.Spider;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SpiderTest {

    private final Spider spider = new Spider();

    @Test
    public void pointsToCorrectImageMap() {

        assertThat(spider.getImageMapFileName(), is("/images/spider.png"));
    }

    @Test
    public void whenGoingUpOrDownThenLowerAnimationIndexIsOne() {

        spider.setDirection(Direction.Up);
        assertThat(spider.getLowerAnimationIndex(), is(0));

        spider.setDirection(Direction.Down);
        assertThat(spider.getLowerAnimationIndex(), is(0));
    }

    @Test
    public void whenGoingUpOrDownThenUpperAnimationIndexIsOne() {

        spider.setDirection(Direction.Up);
        assertThat(spider.getUpperAnimationIndex(), is(1));

        spider.setDirection(Direction.Down);
        assertThat(spider.getUpperAnimationIndex(), is(1));
    }

    @Test
    public void whenGoingLeftOrRightThenLowerAnimationIndexIsTwo() {

        spider.setDirection(Direction.Left);
        assertThat(spider.getLowerAnimationIndex(), is(2));

        spider.setDirection(Direction.Right);
        assertThat(spider.getLowerAnimationIndex(), is(2));
    }

    @Test
    public void whenGoingLeftOrRightThenUpperAnimationIndexIsThree() {

        spider.setDirection(Direction.Left);
        assertThat(spider.getUpperAnimationIndex(), is(3));

        spider.setDirection(Direction.Right);
        assertThat(spider.getUpperAnimationIndex(), is(3));
    }

    @Test
    public void frameRateIsTwoHundred() {

        assertThat(spider.getFrameRate(), is(200));
    }

    @Test
    public void velocityIsFifty() {

        assertThat(spider.getVelocity(), is(50.));
    }

    @Test
    public void pointsToEnemyMazeGraph() {

        assertThat(spider.getMazeGraphFileName(), is("/maze-graph-enemy.json"));
    }

    @Test
    public void randomAlgorithm() {

        assertThat(spider.getMazeAlgorithm(), is(MazeAlgorithm.Random));
    }
}
