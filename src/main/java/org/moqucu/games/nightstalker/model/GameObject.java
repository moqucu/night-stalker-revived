package org.moqucu.games.nightstalker.model;

import lombok.Getter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public abstract class GameObject {

    public static class PreconditionNotMetForMakingObjectVisibleException extends RuntimeException {

        PreconditionNotMetForMakingObjectVisibleException(String message) {

            super(message);
        }
    }

    @Getter
    private final UUID id = UUID.randomUUID();

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
    private boolean visible = false;

    protected final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public double getX() {

        return absolutePosition.getX();
    }

    public void setX(double x) {

        final double oldX = absolutePosition.getX();
        absolutePosition.addToX(x - oldX);
        propertyChangeSupport.firePropertyChange("x", oldX, absolutePosition.getX());
    }

    public void addToX(double numberToBeAdded) {

        setX(getX() + numberToBeAdded);
    }

    public double getY() {

        return absolutePosition.getY();
    }

    public void setY(double y) {

        final double oldY = absolutePosition.getY();
        absolutePosition.addToY(y - oldY);
        propertyChangeSupport.firePropertyChange("y", oldY, absolutePosition.getY());
    }

    public void addToY(double numberToBeAdded) {

        setY(getY() + numberToBeAdded);
    }

    public void setImageMapFileName(String imageMapFileName) {

        final String oldImageMapFileName = this.imageMapFileName;
        this.imageMapFileName = imageMapFileName;
        propertyChangeSupport.firePropertyChange(
                "imageMapFileName",
                oldImageMapFileName,
                imageMapFileName
        );
    }

    public void setInitialImageIndex(int initialImageIndex) {

        final int oldInitialImageIndex = this.initialImageIndex;
        this.initialImageIndex = initialImageIndex;
        propertyChangeSupport.firePropertyChange(
                "initialImageIndex",
                oldInitialImageIndex,
                initialImageIndex
        );
    }

    public void setVisible(boolean visible) {

        try (InputStream inputStream = getClass().getResourceAsStream(imageMapFileName)) {

            if (inputStream == null)
                throw new PreconditionNotMetForMakingObjectVisibleException("Image map file name not correctly set!");
            else if (initialImageIndex == -1)
                throw new PreconditionNotMetForMakingObjectVisibleException("Initial image index not correctly set!");
            else {
                final boolean oldVisible = this.visible;
                this.visible = visible;
                propertyChangeSupport.firePropertyChange(
                        "visible",
                        oldVisible,
                        visible
                );
            }

        } catch (IOException ioException) {
            throw new PreconditionNotMetForMakingObjectVisibleException("Image map file name not correctly set!");
        }
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {

        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {

        propertyChangeSupport.removePropertyChangeListener(listener);
    }
}
