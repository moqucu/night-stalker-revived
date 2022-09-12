package org.moqucu.games.nightstalker.model;

import lombok.Getter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public abstract class GameObjectImpl implements GameObject {

    private final UUID objectId = UUID.randomUUID();

    @Getter
    private final double width = 32.0;

    @Getter
    private final double height = 32.0;

    private final AbsolutePosition absolutePosition = new AbsolutePosition();

    @Getter
    private String imageMapFileName = "";

    @Getter
    private int initialImageIndex = -1;

    @Getter
    private boolean objectVisible = false;

    protected final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    @Override
    public String getObjectId() {

        return objectId.toString();
    }

    @Override
    public double getXPosition() {

        return absolutePosition.getX();
    }

    @Override
    public void setXPosition(double xPosition) {

        final double oldXPosition = absolutePosition.getX();
        absolutePosition.addToX(xPosition - oldXPosition);
        propertyChangeSupport.firePropertyChange("xPosition", oldXPosition, absolutePosition.getX());
    }

    public void addToXPosition(double numberToBeAdded) {

        setXPosition(getXPosition() + numberToBeAdded);
    }

    @Override
    public double getYPosition() {

        return absolutePosition.getY();
    }

    @Override
    public void setYPosition(double yPosition) {

        final double oldYPosition = absolutePosition.getY();
        absolutePosition.addToY(yPosition - oldYPosition);
        propertyChangeSupport.firePropertyChange("yPosition", oldYPosition, absolutePosition.getY());
    }

    public void addToYPosition(double numberToBeAdded) {

        setYPosition(getYPosition() + numberToBeAdded);
    }

    @Override
    public void setImageMapFileName(String imageMapFileName) {

        final String oldImageMapFileName = this.imageMapFileName;
        this.imageMapFileName = imageMapFileName;
        propertyChangeSupport.firePropertyChange(
                "imageMapFileName",
                oldImageMapFileName,
                imageMapFileName
        );
    }

    @Override
    public void setInitialImageIndex(int initialImageIndex) {

        final int oldInitialImageIndex = this.initialImageIndex;
        this.initialImageIndex = initialImageIndex;
        propertyChangeSupport.firePropertyChange(
                "initialImageIndex",
                oldInitialImageIndex,
                initialImageIndex
        );
    }

    @Override
    public void setObjectVisible(boolean objectVisible) {

        try (InputStream inputStream = getClass().getResourceAsStream(imageMapFileName)) {

            if (inputStream == null)
                throw new PreconditionNotMetForMakingObjectVisibleException("Image map file name not correctly set!");
            else if (initialImageIndex == -1)
                throw new PreconditionNotMetForMakingObjectVisibleException("Initial image index not correctly set!");
            else {
                final boolean oldObjectVisible = this.objectVisible;
                this.objectVisible = objectVisible;
                propertyChangeSupport.firePropertyChange(
                        "objectVisible",
                        oldObjectVisible,
                        objectVisible
                );
            }

        } catch (IOException ioException) {
            throw new PreconditionNotMetForMakingObjectVisibleException("Image map file name not correctly set!");
        }
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {

        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {

        propertyChangeSupport.removePropertyChangeListener(listener);
    }
}
