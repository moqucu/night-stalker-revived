package org.moqucu.games.nightstalker.model.hero;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.moqucu.games.nightstalker.model.Direction;
import org.moqucu.games.nightstalker.model.MazeAlgorithm;
import org.moqucu.games.nightstalker.model.MovableObject;
import org.moqucu.games.nightstalker.model.Resettable;

@Getter
@Log4j2
public class NightStalker extends MovableObject implements Resettable {

    private boolean running;

    @Setter
    private boolean leftPressed;

    @Setter
    private boolean rightPressed;

    @Setter
    private boolean upPressed;

    @Setter
    private boolean downPressed;

    public NightStalker() {

        this.addPropertyChangeListener(
                evt -> {

                    if (evt.getPropertyName().equals("running")) {
                        if (evt.getNewValue().equals(true)) {
                            switch (getDirection()) {
                                case Up, Down -> {
                                    setLowerAnimationIndex(1);
                                    setUpperAnimationIndex(2);
                                }
                                case Left -> {
                                    setLowerAnimationIndex(3);
                                    setUpperAnimationIndex(10);
                                }
                                case Right -> {
                                    setLowerAnimationIndex(11);
                                    setUpperAnimationIndex(18);
                                }
                                case OnTop, Undefined -> {
                                    setLowerAnimationIndex(0);
                                    setUpperAnimationIndex(0);
                                }
                            }
                            setAnimated(true);
                            setInMotion(true);
                        } else
                            resetAnimationIndicesAndOtherProperties();
                    }
                }
        );
        setMazeAlgorithm(MazeAlgorithm.FollowDirection);
        setMazeGraphFileName("/json/maze-graph-night-stalker.json");
        setImageMapFileName("/images/night-stalker.png");
        setFrameRate(10);
        setVelocity(30);
        reset();
    }

    private void resetAnimationIndicesAndOtherProperties() {

        setInitialImageIndex(0);
        setLowerAnimationIndex(0);
        setUpperAnimationIndex(0);
        setAnimated(false);
        setInMotion(false);
    }

    public void setRunning(boolean running) {

        final boolean oldRunning = this.running;
        this.running = running;
        propertyChangeSupport.firePropertyChange(
                "running",
                oldRunning,
                running
        );
    }

    public void stop() {

        setRunning(false);
    }

    public void run(Direction direction) {

        setDirection(direction);
        setRunning(true);
    }

    @Override
    public void reset() {

        setXPosition(288);
        setYPosition(144);
        setDirection(Direction.Up);
        if (!running)
            resetAnimationIndicesAndOtherProperties();
        stop();
     }

    @Override
    public void elapseTime(double milliseconds) {

        if (isLeftPressed())
            run(Direction.Left);
        else if (isRightPressed())
            run(Direction.Right);
        else if (isUpPressed())
            run(Direction.Up);
        else if (isDownPressed())
            run(Direction.Down);
        else
            stop();
        super.elapseTime(milliseconds);
    }
}
