package org.moqucu.games.nightstalker.model;

import lombok.Getter;
import lombok.Setter;
import org.moqucu.games.nightstalker.event.TimeListener;

public abstract class AnimatedObject extends GameObject implements TimeListener {

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
    @Setter
    private int upperAnimationIndex = -1;

    @Getter
    @Setter
    private int imageIndex = -1;

    private long elapsedTime = 0;

    private double frameInterval = -1.0;

    public void setLowerAnimationIndex(int lowerAnimationIndex) {

        this.lowerAnimationIndex = lowerAnimationIndex;
        this.imageIndex = lowerAnimationIndex;
    }

    public void setAnimated(boolean animated) {

        if (frameRate == -1)
            throw new PreconditionNotMetForAnimatingObjectException("Frame rate not correctly set!");
        else if (lowerAnimationIndex == -1)
            throw new PreconditionNotMetForAnimatingObjectException("Lower animation index not correctly set!");
        else if (upperAnimationIndex == -1)
            throw new PreconditionNotMetForAnimatingObjectException("Upper animation index not correctly set!");
        else
            this.animated = animated;
    }

    public void setFrameRate(int frameRate) {

        this.frameRate = frameRate;
        frameInterval = 1000.0 / frameRate;
    }

    @Override
    public void elapseTime(long milliseconds) {

        if (animated) {

            elapsedTime += milliseconds;
            final long elapsedTimeSinceLastFullSecond = elapsedTime - (elapsedTime / 1000) * 1000;
            double remainingTimeForLoop = elapsedTimeSinceLastFullSecond - frameInterval;

            while (remainingTimeForLoop > 0) {

                imageIndex++;
                if (imageIndex > upperAnimationIndex)
                    imageIndex = lowerAnimationIndex;
                remainingTimeForLoop -= frameInterval;
            }
        }
    }
}
