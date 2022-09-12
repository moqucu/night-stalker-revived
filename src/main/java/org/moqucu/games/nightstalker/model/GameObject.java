package org.moqucu.games.nightstalker.model;

import java.beans.PropertyChangeListener;

public interface GameObject {

    class PreconditionNotMetForMakingObjectVisibleException extends RuntimeException {

        PreconditionNotMetForMakingObjectVisibleException(String message) {

            super(message);
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
