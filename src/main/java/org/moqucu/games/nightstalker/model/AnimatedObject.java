package org.moqucu.games.nightstalker.model;

import lombok.Getter;

public abstract class AnimatedObject extends DisplayableObject implements TimeListener {

    public static class PreconditionNotMetForAnimatingObjectException extends RuntimeException {

        PreconditionNotMetForAnimatingObjectException(String message) {

            super(message);
        }
    }

    @Getter
    private int frameRate = -1;

    @Getter
    private boolean animated = false;

    @Getter
    private int lowerAnimationIndex = -1;

    @Getter
    private int upperAnimationIndex = -1;

    @Getter
    private int imageIndex = -1;

    private double elapsedTime = 0;

    private double frameInterval = -1.0;

    public void setLowerAnimationIndex(int lowerAnimationIndex) {

        final int oldLowerAnimationIndex = this.lowerAnimationIndex;
        this.lowerAnimationIndex = lowerAnimationIndex;

        if (oldLowerAnimationIndex != lowerAnimationIndex) {

            propertyChangeSupport.firePropertyChange(
                    "lowerAnimationIndex",
                    oldLowerAnimationIndex,
                    lowerAnimationIndex
            );

            if (getInitialImageIndex() == -1)
                setImageIndex(lowerAnimationIndex);
        }
    }

    public void setUpperAnimationIndex(int upperAnimationIndex) {

        final int oldUpperAnimationIndex = this.upperAnimationIndex;
        this.upperAnimationIndex = upperAnimationIndex;
        propertyChangeSupport.firePropertyChange(
                "upperAnimationIndex",
                oldUpperAnimationIndex,
                upperAnimationIndex
        );
    }

    public void setAnimated(boolean animated) {

        if (frameRate == -1)
            throw new PreconditionNotMetForAnimatingObjectException("Frame rate not correctly set!");
        else if (lowerAnimationIndex == -1)
            throw new PreconditionNotMetForAnimatingObjectException("Lower animation index not correctly set!");
        else if (upperAnimationIndex == -1)
            throw new PreconditionNotMetForAnimatingObjectException("Upper animation index not correctly set!");
        else {
            final boolean oldAnimated = this.animated;
            this.animated = animated;
            propertyChangeSupport.firePropertyChange(
                    "animated",
                    oldAnimated,
                    animated
            );
        }
    }

    public void setFrameRate(int frameRate) {

        final int oldFrameRate = this.frameRate;
        this.frameRate = frameRate;
        frameInterval = 1000.0 / frameRate;
        propertyChangeSupport.firePropertyChange(
                "frameRate",
                oldFrameRate,
                frameRate
        );
    }

    public void setImageIndex(int imageIndex) {

        final int oldImageIndex = this.imageIndex;
        this.imageIndex = imageIndex;
        propertyChangeSupport.firePropertyChange(
                "imageIndex",
                oldImageIndex,
                imageIndex
        );
    }

    @Override
    public void elapseTime(double milliseconds) {

        if (animated) {

            final long numberOfFramesBefore = Math.round(elapsedTime / frameInterval);
            final long numberOfFramesAfter = Math.round((elapsedTime + milliseconds) / frameInterval);

            int loopInternalCopyOfImageIndex = imageIndex;

            for (int i = 0; i < (numberOfFramesAfter - numberOfFramesBefore); i++) {

                loopInternalCopyOfImageIndex++;
                if (loopInternalCopyOfImageIndex > upperAnimationIndex)
                    loopInternalCopyOfImageIndex = lowerAnimationIndex;
            }
            setImageIndex(loopInternalCopyOfImageIndex);
        }

        elapsedTime += milliseconds;
    }

    @Override
    public void setInitialImageIndex(int initialImageIndex) {

        super.setInitialImageIndex(initialImageIndex);
        setImageIndex(initialImageIndex);
    }
}
