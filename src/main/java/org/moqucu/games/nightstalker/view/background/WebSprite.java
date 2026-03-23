package org.moqucu.games.nightstalker.view.background;

import lombok.Getter;
import org.moqucu.games.nightstalker.model.GameObject;
import org.moqucu.games.nightstalker.model.background.Web;
import org.moqucu.games.nightstalker.view.DisplayableSprite;

@Getter
public class WebSprite extends DisplayableSprite {

    private Web model;

    public WebSprite() {

        super(new Web());
        model = (Web) super.getModel();
    }

    private void setWebModel(Web model) {

        super.setModel(model);
        this.model = model;
    }

    public void setModel(GameObject gameObject) {

        if (!(gameObject instanceof Web))
            throw new RuntimeException("Game object needs to be of class Web!");

        setWebModel((Web) gameObject);
    }
}
