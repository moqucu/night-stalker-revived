package org.moqucu.games.nightstalker.model.enemy.test;

import org.junit.jupiter.api.Test;
import org.moqucu.games.nightstalker.model.Direction;
import org.moqucu.games.nightstalker.model.GameWorld;
import org.moqucu.games.nightstalker.model.MazeAlgorithm;
import org.moqucu.games.nightstalker.model.Resettable;
import org.moqucu.games.nightstalker.model.enemy.Spider;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SpiderTest {

    private final Spider spider = new Spider();

    @Test
    public void spiderOfTypeResettable() {

        assertThat(spider, isA(Resettable.class));
    }

    @Test
    public void resettingTheSpiderSetsEverythingBack() {

        final Spider localSpider = new Spider();
        localSpider.setDirection(Direction.Left);
        localSpider.setYPosition(160);
        assertThat(localSpider.isSlow(), is(false));
        assertThat(localSpider.getVelocity(), is(50.0));
        localSpider.reset();

        assertThat(localSpider.getDirection(), is(Direction.Down));
        assertThat(localSpider.isSlow(), is(true));
        assertThat(localSpider.getVelocity(), is(25.0));
    }

    @Test
    public void initialDirectionIsDown() {

        assertThat(spider.getDirection(), is(Direction.Down));
    }

    @Test
    public void initialMazeAlgorithmIsFollowDirection() {

        assertThat(spider.getMazeAlgorithm(), is(MazeAlgorithm.FollowDirection));
    }

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
    public void frameRateIsFive() {

        assertThat(spider.getFrameRate(), is(5));
    }

    @Test
    public void initialVelocityIsTwentyFive() {

        final Spider anotherSpider = new Spider();

        assertThat(anotherSpider.getVelocity(), is(25.));
    }

    @Test
    public void whenChangingDirectionForTheFirstTimeVelocityDoubles() {

        final GameWorld gameWorld = new GameWorld();
        final Spider anotherSpider = new Spider();
        gameWorld.add(anotherSpider);
        gameWorld.pulse(7000);

        assertThat(anotherSpider.getVelocity(), is(50.));
    }

    @Test
    public void velocityIsTwentyFive() {

        assertThat(spider.getVelocity(), is(25.0));
    }

    @Test
    public void pointsToEnemyMazeGraph() {

        assertThat(spider.getMazeGraphFileName(), is("/json/maze-graph-enemy.json"));
    }

    @Test
    public void spiderChangesToRandomAlgorithmWhenReachingFirstNode() {

        final Spider anotherSpider = new Spider();
        anotherSpider.setYPosition(160);

        assertThat(anotherSpider.getMazeAlgorithm(), is(MazeAlgorithm.Random));
    }

    @Test
    public void canChangePosition() {

        assertThat(spider.canChangePosition(), is(true));
    }

    @Test
    public void initialXPositionIsNinetySix() {

        assertThat(spider.getXPosition(), is(96.));
    }

    @Test
    public void initialYPositionIsThirtyTwo() {

        assertThat(spider.getYPosition(), is(32.));
    }

    @Test
    public void initiallyInMotion() {

        assertThat(spider.isInMotion(), is(true));
    }

    @Test
    public void initiallyAnimated() {

        assertThat(spider.isAnimated(), is(true));
    }

    @Test
    public void hasSlowProperty() {

        assertThat(spider, hasProperty("slow"));
    }

    @Test
    public void slowPropertyOfTypeBoolean() {

        assertThat(spider.isSlow(), isA(Boolean.class));
    }
}
