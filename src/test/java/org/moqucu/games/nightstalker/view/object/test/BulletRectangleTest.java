package org.moqucu.games.nightstalker.view.object.test;

import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.moqucu.games.nightstalker.model.AbsolutePosition;
import org.moqucu.games.nightstalker.model.Direction;
import org.moqucu.games.nightstalker.model.object.Bullet;
import org.moqucu.games.nightstalker.model.object.Weapon;
import org.moqucu.games.nightstalker.view.Sprite;
import org.moqucu.games.nightstalker.view.object.BulletRectangle;
import org.testfx.framework.junit5.ApplicationExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(ApplicationExtension.class)
public class BulletRectangleTest {

    private final BulletRectangle bulletRectangle = new BulletRectangle();

    @Test
    public void bulletRectangleIsASprite() {

        assertThat(bulletRectangle, isA(Sprite.class));
    }

    @Test
    public void bulletRectangleIsARectangle() {

        assertThat(bulletRectangle, isA(Rectangle.class));
    }
    @Test
    public void modelRepresentsBullet() {

        assertThat(bulletRectangle.getModel(), isA(Bullet.class));
    }

    @Test
    public void ensureThatWrongModelClassTriggersException() {

        final Weapon weapon = new Weapon();
        Throwable throwable = assertThrows(
                RuntimeException.class,
                () -> bulletRectangle.setModel(weapon)
        );
        assertThat(throwable.getMessage(), is("Model needs to be of type " +
                "class org.moqucu.games.nightstalker.model.object.Bullet but was of type " +
                "class org.moqucu.games.nightstalker.model.object.Weapon!"));
    }

    @Test
    public void correctModelClassWorksFine() {

        final Bullet bullet = new Bullet();
        bullet.setXPosition(23.5);
        bulletRectangle.setModel(bullet);
        assertThat(bulletRectangle.getX(), is(23.5));
    }

    @Test
    public void testPropertyChangePropagation() {

        final Bullet bullet = new Bullet();
        bullet.fire(bullet, Direction.Left, new AbsolutePosition(), new AbsolutePosition());
        bulletRectangle.setModel(bullet);
        assertThat(bulletRectangle.getX(), is(16.));
    }

    @Test
    public void testRectangleSize() {

    }

    @Test
    public void testColorSize() {

    }
}
