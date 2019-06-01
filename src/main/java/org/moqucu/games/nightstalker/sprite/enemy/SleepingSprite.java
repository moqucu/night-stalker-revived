package org.moqucu.games.nightstalker.sprite.enemy;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.moqucu.games.nightstalker.sprite.SpawnableSprite;

@Data
@Log4j2
@SuppressWarnings("unused")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public abstract class SleepingSprite extends SpawnableSprite {

    private final LongProperty sleepTineInMillis = new SimpleLongProperty(1000L);

    SleepingSprite() {

        super();
    }

    public long getSleepTimeInMillis() {

        return sleepTineInMillis.get();
    }

    public void setSleepTimeInMillis(long sleepTimeInMillis) {

        this.sleepTineInMillis.set(sleepTimeInMillis);
    }

    LongProperty sleepTimeInMillisProperty() {

        return sleepTineInMillis;
    }
}
