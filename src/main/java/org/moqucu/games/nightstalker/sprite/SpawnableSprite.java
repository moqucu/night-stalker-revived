package org.moqucu.games.nightstalker.sprite;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleDoubleProperty;
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
public abstract class SpawnableSprite extends ArtificiallyMovableSprite {

    private final LongProperty spawnTineInMillis = new SimpleLongProperty(3000L);

    private final DoubleProperty spawnCoordinateX = new SimpleDoubleProperty(0.);

    private final DoubleProperty spawnCoordinateY = new SimpleDoubleProperty(0.);

    protected SpawnableSprite() {

        super();
    }

    public long getSpawnTimeInMillis() {

        return spawnTineInMillis.get();
    }

    public void setSpawnTimeInMillis(long sleepTimeInMillis) {

        this.spawnTineInMillis.set(sleepTimeInMillis);
    }

    public LongProperty spawnTimeInMillisProperty() {

        return spawnTineInMillis;
    }

    public double getSpawnCoordinateX() {

        return spawnCoordinateX.get();
    }

    public void setSpawnCoordinateX(double spawnCoordinateX) {

        this.spawnCoordinateX.set(spawnCoordinateX);
        this.translateXProperty().set(spawnCoordinateX);
    }

    public DoubleProperty spawnCoordinateXProperty() {

        return spawnCoordinateX;
    }


    public double getSpawnCoordinateY() {

        return spawnCoordinateY.get();
    }

    public void setSpawnCoordinateY(double spawnCoordinateY) {

        this.spawnCoordinateY.set(spawnCoordinateY);
        this.translateYProperty().set(spawnCoordinateY);
    }

    public DoubleProperty spawnCoordinateYProperty() {

        return spawnCoordinateY;
    }
}
