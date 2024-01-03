package org.moqucu.games.nightstalker.view.enemy;

import lombok.Getter;
import org.moqucu.games.nightstalker.model.GameObject;
import org.moqucu.games.nightstalker.model.enemy.GreyRobot;
import org.moqucu.games.nightstalker.view.MovableSprite;

@Getter
public class GreyRobotSprite extends MovableSprite {

    private GreyRobot model;

    public GreyRobotSprite() {

        super(new GreyRobot());
        model = (GreyRobot) super.getModel();
    }

    private void setBatModel(GreyRobot model) {

        super.setModel(model);
        this.model = model;
    }

    public void setModel(GameObject gameObject) {

        if (!(gameObject instanceof GreyRobot))
            throw new RuntimeException("Game object needs to be of class GreyRobot!");

        setBatModel((GreyRobot)gameObject);
    }
}
