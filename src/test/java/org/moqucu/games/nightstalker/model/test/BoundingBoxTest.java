package org.moqucu.games.nightstalker.model.test;

import org.junit.jupiter.api.Test;
import org.moqucu.games.nightstalker.model.BoundingBox;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.isA;
import static org.hamcrest.Matchers.is;

public class BoundingBoxTest {

    private final BoundingBox boundingBox = new BoundingBox();

    @Test
    public void hasMinXProperty() {

        assertThat(boundingBox, hasProperty("minX"));
    }

    @Test
    public void minXPropertyOfTypeDouble() {

        assertThat(boundingBox.getMinX(), isA(Double.class));
    }

    @Test
    public void hasMaxXProperty() {

        assertThat(boundingBox, hasProperty("maxX"));
    }

    @Test
    public void maxXPropertyOfTypeDouble() {

        assertThat(boundingBox.getMaxX(), isA(Double.class));
    }

    @Test
    public void hasMinYProperty() {

        assertThat(boundingBox, hasProperty("minY"));
    }

    @Test
    public void minYPropertyOfTypeDouble() {

        assertThat(boundingBox.getMinY(), isA(Double.class));
    }

    @Test
    public void hasMaxYProperty() {

        assertThat(boundingBox, hasProperty("maxY"));
    }

    @Test
    public void maxYPropertyOfTypeDouble() {

        assertThat(boundingBox.getMaxY(), isA(Double.class));
    }

    @Test
    public void overlappingWorks() {

        final BoundingBox boundingBox1 = new BoundingBox(2.0, 8.0, 3.0, 9.0);
        final BoundingBox boundingBox2 = new BoundingBox(4.0, 10.0, 4.0, 10.0);

        assertThat(boundingBox1.isOverlapping(boundingBox2), is(true));
    }

    @Test
    public void thereIsNoOverlappingInThisCase() {

        final BoundingBox boundingBox1 = new BoundingBox(2.0, 8.0, 3.0, 9.0);
        final BoundingBox boundingBox2 = new BoundingBox(4.0, 10.0, 10.0, 14.0);

        assertThat(boundingBox1.isOverlapping(boundingBox2), is(false));
    }
}
