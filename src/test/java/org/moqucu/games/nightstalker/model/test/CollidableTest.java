package org.moqucu.games.nightstalker.model.test;

import org.junit.jupiter.api.Test;
import org.moqucu.games.nightstalker.model.BoundingBox;
import org.moqucu.games.nightstalker.model.Collidable;
import org.moqucu.games.nightstalker.model.CollidableImpl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CollidableTest {

    private final Collidable collidableOne = new CollidableImpl() {

        @Override
        public BoundingBox getAbsoluteBounds() {

            final BoundingBox boundingBox = new BoundingBox();
            int absX = 2;
            boundingBox.setMinX(absX + getBoundingBox().getMinX());
            boundingBox.setMaxX(absX + getBoundingBox().getMaxX());
            int absY = 8;
            boundingBox.setMinY(absY + getBoundingBox().getMinY());
            boundingBox.setMaxY(absY + getBoundingBox().getMaxY());

            return boundingBox;
        }

        @Override
        public boolean canChangePosition() {

            return false;
        }
    };

    private final Collidable collidableTwo = new CollidableImpl() {

        @Override
        public BoundingBox getAbsoluteBounds() {

            final BoundingBox boundingBox = new BoundingBox();
            int absX = 3;
            boundingBox.setMinX(absX + getBoundingBox().getMinX());
            boundingBox.setMaxX(absX + getBoundingBox().getMaxX());
            int absY = 9;
            boundingBox.setMinY(absY + getBoundingBox().getMinY());
            boundingBox.setMaxY(absY + getBoundingBox().getMaxY());

            return boundingBox;
        }

        @Override
        public boolean canChangePosition() {

            return true;
        }
    };

    @Test
    public void collidableHasBoundingBox() {

        assertThat(collidableOne, hasProperty("boundingBox"));
    }

    @Test
    public void boundingBoxOfTypeBoundingBox() {

        collidableTwo.setBoundingBox(new BoundingBox());

        assertThat(collidableTwo.getBoundingBox(), isA(BoundingBox.class));
    }

    @Test
    public void absoluteBoundsAlignWithPositionOfObject() {

        final BoundingBox boundingBox = new BoundingBox(1, 10, 1, 10);
        collidableTwo.setBoundingBox(boundingBox);

        assertThat(collidableTwo.getAbsoluteBounds().getMinX(), is(4.0));
        assertThat(collidableTwo.getAbsoluteBounds().getMaxX(), is(13.0));
        assertThat(collidableTwo.getAbsoluteBounds().getMinY(), is(10.0));
        assertThat(collidableTwo.getAbsoluteBounds().getMaxY(), is(19.0));
    }

    @Test
    public void absoluteBoundsAlignWithPositionOfAnotherObject() {

        final BoundingBox boundingBox = new BoundingBox(5, 20, 4, 8);
        collidableOne.setBoundingBox(boundingBox);

        assertThat(collidableOne.getAbsoluteBounds().getMinX(), is(7.0));
        assertThat(collidableOne.getAbsoluteBounds().getMaxX(), is(22.0));
        assertThat(collidableOne.getAbsoluteBounds().getMinY(), is(12.0));
        assertThat(collidableOne.getAbsoluteBounds().getMaxY(), is(16.0));
    }

    @Test
    public void withCorrectlySetBoundingBoxesTwoCollidablesCollide() {

        final BoundingBox boundingBox1 = new BoundingBox(1, 10, 1, 10);
        collidableTwo.setBoundingBox(boundingBox1);
        final BoundingBox boundingBox2 = new BoundingBox(5, 20, 4, 8);
        collidableOne.setBoundingBox(boundingBox2);

        assertThat(collidableOne.isCollidingWith(collidableTwo), is(true));
    }

    @Test
    public void withIncorrectlySetBoundingBoxesTwoCollidablesDoNotCollide() {

        final BoundingBox boundingBox1 = new BoundingBox(1, 10, 1, 10);
        collidableTwo.setBoundingBox(boundingBox1);
        final BoundingBox boundingBox2 = new BoundingBox(50, 200, 40, 80);
        collidableOne.setBoundingBox(boundingBox2);

        assertThat(collidableOne.isCollidingWith(collidableTwo), is(false));
    }

    @Test
    public void collidableOneCannotChangePosition() {

        assertThat(collidableOne.canChangePosition(), is(false));
    }


    @Test
    public void collidableTwoCanChangePosition() {

        assertThat(collidableTwo.canChangePosition(), is(true));
    }
}
