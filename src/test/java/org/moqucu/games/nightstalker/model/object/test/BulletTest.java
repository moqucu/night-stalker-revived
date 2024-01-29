package org.moqucu.games.nightstalker.model.object.test;

import org.junit.jupiter.api.Test;
import org.moqucu.games.nightstalker.model.*;
import org.moqucu.games.nightstalker.model.enemy.GreyRobot;
import org.moqucu.games.nightstalker.model.hero.NightStalker;
import org.moqucu.games.nightstalker.model.object.Bullet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BulletTest {

    @Test
    public void bulletInheritsFromDisplayableObject() {

        assertThat(new Bullet(), isA(DisplayableObject.class));
    }

    @Test
    public void bulletHasFiredProperty() {

        assertThat(new Bullet(), hasProperty("fired"));
    }

    @Test
    public void bulletHasDirectionProperty() {

        assertThat(new Bullet(), hasProperty("direction"));
    }

    @Test
    public void propertyDirectionOfTypeDirection() {

        final Bullet aBullet = new Bullet();

        assertThat(aBullet.getDirection(), isA(Direction.class));
    }

    @Test
    public void initialDirectionIsUndefined() {

        final Bullet aBullet = new Bullet();

        assertThat(aBullet.getDirection(), is(Direction.Undefined));
    }

    @Test
    public void immediatelyAfterFiringDirectionIsNotUndefined() {

        final Bullet aBullet = new Bullet();
        aBullet.fire(new NightStalker(), Direction.Down, new AbsolutePosition(), new AbsolutePosition());

        assertThat(aBullet.getDirection(), is(not(Direction.Undefined)));
    }

    @Test
    public void initiallyBulletIsNotVisible() {

        final Bullet aBullet = new Bullet();

        assertThat(aBullet.isObjectVisible(), is(false));
    }

    @Test
    public void afterBulletIsFiredItBecomesVisible() {

        final Bullet aBullet = new Bullet();
        aBullet.fire(new NightStalker(), Direction.Left, new AbsolutePosition(), new AbsolutePosition());

        assertThat(aBullet.isObjectVisible(), is(true));
    }

    @Test
    public void firedPropertyIsOfTypeBoolean() {

        final Bullet aBullet = new Bullet();

        assertThat(aBullet.isFired(), isA(Boolean.class));
    }

    @Test
    public void firingIntoUnknownDirectionNotSupported() {

        final Bullet aBullet = new Bullet();

        assertThrows(
                Bullet.FiringDirectionNotSupportedException.class,
                () -> aBullet.fire(
                        new NightStalker(),
                        Direction.Undefined,
                        new AbsolutePosition(),
                        new AbsolutePosition()
                )
        );
    }

    @Test
    public void firingOnTopNotSupported() {

        final Bullet aBullet = new Bullet();

        assertThrows(
                Bullet.FiringDirectionNotSupportedException.class,
                () -> aBullet.fire(
                        new NightStalker(),
                        Direction.OnTop,
                        new AbsolutePosition(),
                        new AbsolutePosition()
                )
        );
    }

    @Test
    public void firingABulletTriggersAnEvent() {

        final Bullet aBullet = new Bullet();
        aBullet.addPropertyChangeListener(evt -> {
            if (evt.getPropertyName().equals("fired") && evt.getNewValue().equals(true))
                throw new RuntimeException("Bullet was fired!");
        });

        Throwable throwable = assertThrows(
                RuntimeException.class,
                () -> aBullet.fire(
                        new NightStalker(),
                        Direction.Right,
                        new AbsolutePosition(),
                        new AbsolutePosition()
                )
        );
        assertThat(throwable.getMessage(), is("Bullet was fired!"));
    }

    @Test
    public void bulletHasSourceProperty() {

        assertThat(new Bullet(), hasProperty("source"));
    }

    @Test
    public void propertySourceOfTypeGameObject() {

        final Bullet aBullet = new Bullet();

        assertThat(aBullet.getSource(), isA(GameObject.class));
    }

    @Test
    public void sourceIsFiringSource() {

        final Bullet aBullet = new Bullet();
        final NightStalker aNightStalker = new NightStalker();
        aBullet.fire(aNightStalker, Direction.Up, new AbsolutePosition(), new AbsolutePosition());

        assertThat(aBullet.getSource(), is(aNightStalker));
    }

    @Test
    public void collisionWithNonSourceStopsBullet() {

        final Bullet aBullet = new Bullet();
        final GreyRobot aGreyRobot = new GreyRobot();
        aBullet.fire(new NightStalker(), Direction.Left, new AbsolutePosition(), new AbsolutePosition());
        aBullet.collisionOccurredWith(aGreyRobot);

        assertThat(aBullet.isFired(), is(false));
        assertThat(aBullet.isObjectVisible(), is(false));
        assertThat(aBullet.getSource(), is(aBullet));
        assertThat(aBullet.getDirection(), is(Direction.Undefined));
    }

    @Test
    public void collisionWithItselfDoesNotStopBullet() {

        final Bullet aBullet = new Bullet();
        final NightStalker aNightStalker = new NightStalker();
        aBullet.fire(aNightStalker, Direction.Left, new AbsolutePosition(), new AbsolutePosition());
        aBullet.collisionOccurredWith(aBullet);

        assertThat(aBullet.isFired(), is(true));
        assertThat(aBullet.isObjectVisible(), is(true));
        assertThat(aBullet.getSource(), is(aNightStalker));
        assertThat(aBullet.getDirection(), is(Direction.Left));
    }

    @Test
    public void collisionWithItsSourceNotStopBullet() {

        final Bullet aBullet = new Bullet();
        final NightStalker aNightStalker = new NightStalker();
        aBullet.fire(aNightStalker, Direction.Left, new AbsolutePosition(), new AbsolutePosition());
        aBullet.collisionOccurredWith(aNightStalker);

        assertThat(aBullet.isFired(), is(true));
        assertThat(aBullet.isObjectVisible(), is(true));
        assertThat(aBullet.getSource(), is(aNightStalker));
        assertThat(aBullet.getDirection(), is(Direction.Left));
    }

    @Test
    public void bulletHasAnAbsoluteTargetPositionProperty() {

        assertThat(new Bullet(), hasProperty("absoluteTargetPosition"));
    }

    @Test
    public void absoluteTargetPositionPropertyOfTypeAbsolutePosition() {

        final Bullet aBullet = new Bullet();

        assertThat(aBullet.getAbsoluteTargetPosition(), isA(AbsolutePosition.class));
    }

    @Test
    public void initialTargetPositionEqualsItsAbsolutePosition() {

        final Bullet aBullet = new Bullet();

        assertThat(aBullet.getAbsoluteTargetPosition(), is(aBullet.getAbsolutePosition()));
    }

    @Test
    public void aBulletIsOfTypeTimeListener() {

        assertThat(new Bullet(), isA(TimeListener.class));
    }

    @Test
    public void firingABulletToTheLeftBetweenZeroPositionsDeterminesRightSourceAndTargetCoordinates() {

        final Bullet aBullet = new Bullet();
        aBullet.fire(new NightStalker(), Direction.Left, new AbsolutePosition(), new AbsolutePosition());

        assertThat(aBullet.getXPosition(), is(0 + DisplayableObject.WIDTH / 2));
        assertThat(aBullet.getYPosition(), is(0 + DisplayableObject.HEIGHT / 2));
        assertThat(aBullet.getAbsoluteTargetPosition().getX(), is(0.));
        assertThat(aBullet.getAbsoluteTargetPosition().getY(), is(0 + DisplayableObject.HEIGHT / 2));
    }

    @Test
    public void firingABulletToTheRightBetweenZeroPositionsDeterminesRightSourceAndTargetCoordinates() {

        final Bullet aBullet = new Bullet();
        aBullet.fire(new NightStalker(), Direction.Right, new AbsolutePosition(), new AbsolutePosition());

        assertThat(aBullet.getXPosition(), is(0 + DisplayableObject.WIDTH / 2));
        assertThat(aBullet.getYPosition(), is(0 + DisplayableObject.HEIGHT / 2));
        assertThat(aBullet.getAbsoluteTargetPosition().getX(), is(0 + DisplayableObject.WIDTH));
        assertThat(aBullet.getAbsoluteTargetPosition().getY(), is(0 + DisplayableObject.HEIGHT / 2));
    }

    @Test
    public void firingABulletToTheTopBetweenZeroPositionsDeterminesRightSourceAndTargetCoordinates() {

        final Bullet aBullet = new Bullet();
        aBullet.fire(new NightStalker(), Direction.Up, new AbsolutePosition(), new AbsolutePosition());

        assertThat(aBullet.getXPosition(), is(0 + DisplayableObject.WIDTH / 2));
        assertThat(aBullet.getYPosition(), is(0 + DisplayableObject.HEIGHT / 2));
        assertThat(aBullet.getAbsoluteTargetPosition().getX(), is(0 + DisplayableObject.WIDTH / 2));
        assertThat(aBullet.getAbsoluteTargetPosition().getY(), is(0.));
    }

    @Test
    public void firingABulletToTheBottomBetweenZeroPositionsDeterminesRightSourceAndTargetCoordinates() {

        final Bullet aBullet = new Bullet();
        aBullet.fire(new NightStalker(), Direction.Down, new AbsolutePosition(), new AbsolutePosition());

        assertThat(aBullet.getXPosition(), is(0 + DisplayableObject.WIDTH / 2));
        assertThat(aBullet.getYPosition(), is(0 + DisplayableObject.HEIGHT / 2));
        assertThat(aBullet.getAbsoluteTargetPosition().getX(), is(0 + DisplayableObject.WIDTH / 2));
        assertThat(aBullet.getAbsoluteTargetPosition().getY(), is(0 + DisplayableObject.HEIGHT));
    }

    @Test
    public void afterFiringBulletMovesBetweenSourceAndTargetPositionWithExpectedVelocity() {

        final Bullet aBullet = new Bullet();
        aBullet.fire(
                new NightStalker(),
                Direction.Right,
                new AbsolutePosition(0, 64),
                new AbsolutePosition(32, 64)
        );
        aBullet.elapseTime(1000);

        assertThat(aBullet.getXPosition(), is(32.));
        assertThat(aBullet.getYPosition(), is(64 + DisplayableObject.HEIGHT / 2));
        assertThat(aBullet.isFired(), is(true));
        assertThat(aBullet.isObjectVisible(), is(true));
    }

    @Test
    public void whenReachingTargetBoundaryBulletStopsBeingFired() {

        final Bullet aBullet = new Bullet();
        aBullet.fire(
                new NightStalker(),
                Direction.Right,
                new AbsolutePosition(0, 64),
                new AbsolutePosition(32, 64)
        );
        aBullet.elapseTime(3001);

        assertThat(aBullet.getXPosition(), is(64.));
        assertThat(aBullet.getYPosition(), is(64 + DisplayableObject.HEIGHT / 2));
        assertThat(aBullet.isFired(), is(false));
        assertThat(aBullet.isObjectVisible(), is(false));
    }

    @Test
    public void bulletOfTypeResettable() {

        final Bullet aBullet = new Bullet();

        assertThat(aBullet, isA(Resettable.class));
    }

    @Test
    public void resettingTheBatSetsEverythingBack() {

        final Bullet aBullet = new Bullet();
        aBullet.fire(
                new GreyRobot(),
                Direction.Right,
                new AbsolutePosition(),
                new AbsolutePosition()
        );
        aBullet.reset();

        assertThat(aBullet.isFired(), is(false));
        assertThat(aBullet.isObjectVisible(), is(false));
        assertThat(aBullet.getDirection(), is(Direction.Undefined));
        assertThat(aBullet.getSource(), is (aBullet));
        assertThat(aBullet.getAbsoluteTargetPosition(), is (nullValue()));
    }

    @Test
    public void pointsToCorrectImageMap() {

        final Bullet aBullet = new Bullet();
        assertThat(aBullet.getImageMapFileName(), is("/images/bullet.png"));
    }

    @Test
    public void initialImageIndexIsZero() {

        final Bullet aBullet = new Bullet();
        assertThat(aBullet.getInitialImageIndex(), is(0));
    }

}
