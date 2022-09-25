package org.moqucu.games.nightstalker.model;

import lombok.Getter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public abstract class GameObject {

    public static class PreconditionNotMetForMakingObjectVisibleException extends RuntimeException {

        public PreconditionNotMetForMakingObjectVisibleException(String message) {

            super(message);
        }
    }

    /**
     * Exception is thrown whenever expected the index for accessing a sprite sheet's cell is < 0 or > 239.
     */
    public static class InitialImageIndexOutOfBoundsException extends RuntimeException {

        public InitialImageIndexOutOfBoundsException() {

            super("Index for accessing the initial image has to be between 0 and 239!");
        }
    }

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

    public String getObjectId() {

        return objectId.toString();
    }

    public double getXPosition() {

        return absolutePosition.getX();
    }

    public void setXPosition(double xPosition) {

        final double oldXPosition = absolutePosition.getX();
        if (oldXPosition != xPosition) {

            absolutePosition.addToX(xPosition - oldXPosition);
            propertyChangeSupport.firePropertyChange("XPosition", oldXPosition, absolutePosition.getX());
        }
    }

    public void addToXPosition(double numberToBeAdded) {

        setXPosition(getXPosition() + numberToBeAdded);
    }

    public double getYPosition() {

        return absolutePosition.getY();
    }

    public void setYPosition(double yPosition) {

        final double oldYPosition = absolutePosition.getY();

        if (oldYPosition != yPosition) {

            absolutePosition.addToY(yPosition - oldYPosition);
            propertyChangeSupport.firePropertyChange("YPosition", oldYPosition, absolutePosition.getY());
        }
    }

    public void addToYPosition(double numberToBeAdded) {

        setYPosition(getYPosition() + numberToBeAdded);
    }

    public void setImageMapFileName(String imageMapFileName) {

        final String oldImageMapFileName = this.imageMapFileName;
        this.imageMapFileName = imageMapFileName;
        if (!oldImageMapFileName.equals(imageMapFileName))
            propertyChangeSupport.firePropertyChange(
                    "imageMapFileName",
                    oldImageMapFileName,
                    imageMapFileName
            );
    }

    public void setInitialImageIndex(int initialImageIndex) {

        if (initialImageIndex < 0 || initialImageIndex > 239)
            throw new InitialImageIndexOutOfBoundsException();

        final int oldInitialImageIndex = this.initialImageIndex;
        this.initialImageIndex = initialImageIndex;

        if (oldInitialImageIndex != initialImageIndex)
            propertyChangeSupport.firePropertyChange(
                    "initialImageIndex",
                    oldInitialImageIndex,
                    initialImageIndex
            );
    }

    public void setObjectVisible(boolean objectVisible) {

        if (imageMapFileName == null)
            throw new PreconditionNotMetForMakingObjectVisibleException("Image map file name not correctly set!");

        try (InputStream inputStream = getClass().getResourceAsStream(imageMapFileName)) {

            if (inputStream == null || inputStream.readAllBytes().length == 0)
                throw new PreconditionNotMetForMakingObjectVisibleException("Image map file name not correctly set!");
            else {
                final boolean oldObjectVisible = this.objectVisible;
                this.objectVisible = objectVisible;

                if (oldObjectVisible != objectVisible)
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

    public void addPropertyChangeListener(PropertyChangeListener listener) {

        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {

        propertyChangeSupport.removePropertyChangeListener(listener);
    }
}
