package org.moqucu.games.nightstalker.view.movable;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

@Data
@Log4j2
@SuppressWarnings("unused")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public abstract class SleepingSprite extends ArtificiallyMovedSprite {

    private final LongProperty sleepTineInMillis = new SimpleLongProperty(1000L);

    SleepingSprite() {

        super();
    }

    @SuppressWarnings("WeakerAccess")
    public long getSleepTimeInMillis() {

        return sleepTineInMillis.get();
    }

    public void setSleepTimeInMillis(long sleepTimeInMillis) {

        this.sleepTineInMillis.set(sleepTimeInMillis);
    }

    public LongProperty sleepTimeInMillisProperty() {

        return sleepTineInMillis;
    }
}
