package org.moqucu.games.nightstalker.model;

import java.beans.PropertyChangeListener;

public interface GameObject {

    class PreconditionNotMetForMakingObjectVisibleException extends RuntimeException {

        PreconditionNotMetForMakingObjectVisibleException(String message) {

            super(message);
        }
    }

    /**
     * Exception is thrown whenever expected the index for accessing a sprite sheet's cell is < 0 or > 239.
     */
    class InitialImageIndexOutOfBoundsException extends RuntimeException {

        InitialImageIndexOutOfBoundsException() {

            super("Index for accessing the initial image has to be between 0 and 239!");
        }
    }

    double getXPosition();

    void setXPosition(double xPosition);

    double getYPosition();

    void setYPosition(double yPosition);

    void setImageMapFileName(String imageMapFileName);

    void setInitialImageIndex(int initialImageIndex);

    void setObjectVisible(boolean objectVisible);

    void addPropertyChangeListener(PropertyChangeListener listener);

    void removePropertyChangeListener(PropertyChangeListener listener);

    String getObjectId();

    double getWidth();

    double getHeight();

    String getImageMapFileName();

    int getInitialImageIndex();

    boolean isObjectVisible();
}
