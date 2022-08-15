package org.moqucu.games.nightstalker.view;

import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.moqucu.games.nightstalker.model.Bullet;
import org.moqucu.games.nightstalker.model.Direction;
import org.moqucu.games.nightstalker.sprite.Approachable;
import org.moqucu.games.nightstalker.sprite.Collidable;
import org.moqucu.games.nightstalker.sprite.Hittable;
import org.moqucu.games.nightstalker.sprite.Sprite;

import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class MazeTest {

    private static class CollidableText extends Text implements Collidable {

        @Override
        public boolean isCollidable() {

            return true;
        }
    }

    private static class HittableText extends Text implements Hittable {

        @Override
        public void hitBy(Collidable collidableObject) {
        }

        @Override
        public boolean isHittable() {

            return true;
        }
    }

    private static class ApproachableText extends Text implements Approachable {

        @Override
        public void approachedBy(Set<Sprite> sprite) {
        }

        @Override
        public Line getLineOfSight() {

            return null;
        }
    }

    private static class TextBullet extends Text implements Bullet {

        @Override
        public boolean isCollidable() {

            return true;
        }

        @Override
        public void shot(Direction direction, Point2D startPoint) {

        }
    }

    private final Maze maze = new Maze();

    @Test
    public void heightShouldBe384Pixels() {

        Assertions.assertEquals(Maze.PREF_HEIGHT, maze.getHeight());
    }

    @Test
    public void widthShouldBe640Pixels() {

        Assertions.assertEquals(Maze.PREF_WIDTH, maze.getWidth());
    }

    @Test
    public void idShouldBeSetAccordingly() {

        Assertions.assertEquals(Maze.ID, maze.getId());
    }

    @Test
    public void testThatCollidableObjectsCanBeDirectlyAccessed() {

        CollidableText collidableText = new CollidableText();
        Pane paneWithCollidableText = new Pane(collidableText);
        maze.getChildren().add(paneWithCollidableText);

        Assertions.assertEquals(
                collidableText,
                maze.getAllCurrentlyCollidableSprites().stream().findFirst().orElseThrow()
        );
    }

    @Test
    public void testThatHittableObjectsCanBeDirectlyAccessed() {

        HittableText hittableText = new HittableText();
        Pane paneWithHittableText = new Pane(hittableText);
        maze.getChildren().add(paneWithHittableText);

        Assertions.assertEquals(
                hittableText,
                maze.getAllHittableSprites().stream().findFirst().orElseThrow()
        );
    }

    @Test
    public void testThatApproachableObjectsCanBeDirectlyAccessed() {

        ApproachableText approachableText = new ApproachableText();
        Pane paneWithApproachableText = new Pane(approachableText);
        maze.getChildren().add(paneWithApproachableText);

        Assertions.assertEquals(
                approachableText,
                maze.getAllApproachableSprites().stream().findFirst().orElseThrow()
        );
    }

    @Test
    public void testThatBulletsCanBeDirectlyAccessed() {

        final TextBullet bullet = new TextBullet();

        Pane paneWithBullet = new Pane(bullet);
        maze.getChildren().add(paneWithBullet);

        Assertions.assertEquals(
                bullet,
                maze.getAllBullets().stream().findFirst().orElseThrow()
        );
    }
}
