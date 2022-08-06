package org.moqucu.games.nightstalker.test.model;

import org.junit.jupiter.api.Test;
import org.moqucu.games.nightstalker.event.TimeListener;
import org.moqucu.games.nightstalker.model.Direction;
import org.moqucu.games.nightstalker.model.GameObject;
import org.moqucu.games.nightstalker.model.MovableGameObject;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.*;

public class MovableGameObjectTest {

    private final MovableGameObject genericMovableGameObject = new MovableGameObject() {

        @Override
        public void elapseTime(long milliseconds) {
        }
    };

    @Test
    public void ofTypeGameObject() {

        assertThat(genericMovableGameObject.getClass(), typeCompatibleWith(GameObject.class));
    }

    @Test
    public void hasVelocity() {

        assertThat(genericMovableGameObject, hasProperty("velocity"));
    }

    @Test
    public void hasDirection() {

        assertThat(genericMovableGameObject, hasProperty("direction"));
    }

    @Test
    public void ofTypeTimeListener() {

        assertThat(genericMovableGameObject.getClass(), typeCompatibleWith(TimeListener.class));
    }

    @Test
    public void hasPropertyMoving() {

        assertThat(genericMovableGameObject, hasProperty("inMotion"));
    }

    @Test
    public void setInMotionWorkOnlyWithDefinedDirection() {

        genericMovableGameObject.setDirection(Direction.Undefined);
        genericMovableGameObject.setInMotion();
        assertThat(genericMovableGameObject.isInMotion(), is(false));
        genericMovableGameObject.stop();

        genericMovableGameObject.setDirection(Direction.Left);
        genericMovableGameObject.setInMotion();
        assertThat(genericMovableGameObject.isInMotion(), is(true));
        genericMovableGameObject.stop();
    }

}
