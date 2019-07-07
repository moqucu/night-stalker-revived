package org.moqucu.games.nightstalker.sprite.object;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import lombok.*;
import lombok.extern.log4j.Log4j2;

import static org.moqucu.games.nightstalker.NightStalkerRevived.translate;

@Data
@Log4j2
@EqualsAndHashCode(callSuper = true)
public class LivesIndex extends Text {

    /**
     * Represents the number of night stalker lived to be displayed by this text field. Default value is 4.
     */
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private IntegerProperty livesIndex = new SimpleIntegerProperty(4);

    public LivesIndex() {

        super();
        setText(livesIndex.getValue().toString());
        setFont(Font.loadFont(translate("fonts/intellect.ttf"), 32.0));
    }

    /**
     * Returns current value of livesIndex.
     *
     * @return current value of livesIndex.
     */
    public int getLivesIndex() {

        return livesIndex.get();
    }

    /**
     * Sets the value of livesIndex.
     *
     * @param livesIndex future value of livesIndex.
     */
    public void setLivesIndex(int livesIndex) {

        this.livesIndex.set(livesIndex);
        setText(this.livesIndex.getValue().toString());
    }

    /**
     * Returns the property object for the livesIndex.
     *
     * @return property object for the livesIndex.
     */
    public IntegerProperty stillLivesIndexProperty() {

        return livesIndex;
    }


}
