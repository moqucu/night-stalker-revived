package org.moqucu.games.nightstalker.model.hero;

import lombok.Getter;
import org.moqucu.games.nightstalker.model.Direction;
import org.moqucu.games.nightstalker.model.MazeAlgorithm;
import org.moqucu.games.nightstalker.model.MovableObject;

public class NightStalker extends MovableObject {

    @Getter
    private boolean running;

    public NightStalker() {

        setXPosition(288);
        setYPosition(144);
        setMazeAlgorithm(MazeAlgorithm.FollowDirection);
        setMazeGraphFileName("/json/maze-graph-night-stalker.json");
        setImageMapFileName("/images/night-stalker.png");
        setFrameRate(10);
        setVelocity(30);
        setInitialImageIndex(0);
        setLowerAnimationIndex(0);
        setUpperAnimationIndex(0);
        setDirection(Direction.Up);
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
                        } else {
                            setLowerAnimationIndex(0);
                            setUpperAnimationIndex(0);
                            setAnimated(false);
                            setInMotion(false);
                        }
                    }
                }
        );
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
}
