package org.moqucu.games.nightstalker.view.enemy.test;

import org.junit.jupiter.api.Test;
import org.moqucu.games.nightstalker.model.enemy.GreyRobot;
import org.moqucu.games.nightstalker.view.MovableSprite;
import org.moqucu.games.nightstalker.view.enemy.GreyRobotSprite;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GreyRobotSpriteTest {

    private final GreyRobotSprite greyRobotSprite = new GreyRobotSprite();

    @Test
    public void wallIsOfTypeSprite() {

        assertThat(greyRobotSprite, isA(MovableSprite.class));
    }

    @Test
    public void hasModelProperty() {

        assertThat(greyRobotSprite, hasProperty("model"));
    }

    @Test
    public void modelIsOfTypeBatModel() {

        assertThat(greyRobotSprite.getModel(), isA(GreyRobot.class));
    }

    @Test
    public void settingAModelWorks() {

        final GreyRobot greyRobot = new GreyRobot();
        greyRobotSprite.setModel(greyRobot);

        assertThat(greyRobotSprite.getModel(), is(greyRobot));
    }
}
