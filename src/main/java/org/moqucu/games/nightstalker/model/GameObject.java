package org.moqucu.games.nightstalker.model;

import lombok.Getter;
import lombok.Setter;

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
    private final AbsolutePosition absolutePosition = new AbsolutePosition();

    @Getter
    private final UUID id = UUID.randomUUID();

    @Getter
    private final double width = 32.0;

    @Getter
    private final double height = 32.0;

    @Getter
    @Setter
    private String imageMapFileName = "";

    @Getter
    @Setter
    private int initialImageIndex = -1;

    @Getter
    private boolean visible = false;

    public void setVisible(boolean visible) {

        try (InputStream inputStream = getClass().getResourceAsStream(imageMapFileName)) {

            if (inputStream == null)
                throw new PreconditionNotMetForMakingObjectVisibleException("Image map file name not correctly set!");
            else if (initialImageIndex == -1)
                throw new PreconditionNotMetForMakingObjectVisibleException("Initial image index not correctly set!");
            else
                this.visible = visible;

        } catch (IOException ioException) {
            throw new PreconditionNotMetForMakingObjectVisibleException("Image map file name not correctly set!");
        }
    }
}
