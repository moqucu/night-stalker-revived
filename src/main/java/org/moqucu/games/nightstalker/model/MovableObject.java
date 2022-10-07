package org.moqucu.games.nightstalker.model;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import lombok.Getter;
import org.moqucu.games.nightstalker.label.MazeAlgorithmFactory;

import java.io.IOException;
import java.io.InputStream;

public abstract class MovableObject extends AnimatedObject {

    public static final String MAZE_JSON_FILE_NAME_CANNOT_BE_NULL = "Maze json file name cannot be null!";
    public static final String JSON_FILE_WITH_MAZE_GRAPH_IS_CORRUPT = "Json file with maze graph is corrupt!";
    public static final String MAZE_JSON_FILE_NAME_NOT_CORRECTLY_SET = "Maze json file name not correctly set!";

    public static class PreconditionNotMetForSettingObjectInMotionException extends RuntimeException {

        PreconditionNotMetForSettingObjectInMotionException(String message) {

            super(message);
        }
    }

    @Getter
    private double velocity = 0.;

    @Getter
    private boolean inMotion = false;

    @Getter
    private Direction direction = Direction.Undefined;

    @Getter
    private MazeAlgorithm mazeAlgorithm = MazeAlgorithm.None;

    private MazeAlgorithmImpl mazeAlgorithmImpl;

    @Getter
    private String mazeGraphFileName = "";

    @Getter
    private AbsMazeGraph absMazeGraph;

    public void setVelocity(double velocity) {

        final double oldVelocity = this.velocity;
        this.velocity = velocity;
        propertyChangeSupport.firePropertyChange(
                "velocity",
                oldVelocity,
                velocity
        );
    }

    public void setInMotion(boolean inMotion) {

        if (direction == Direction.Undefined)
            throw new PreconditionNotMetForSettingObjectInMotionException("Direction is undefined!");
        else if (absMazeGraph == null)
            throw new PreconditionNotMetForSettingObjectInMotionException("No maze graph!");
        else if (!absMazeGraph.isWithinBounds(new AbsolutePosition(getXPosition(), getYPosition())))
            throw new PreconditionNotMetForSettingObjectInMotionException("Absolute position is out of bounds!");
        else if (velocity <= 0.)
            throw new PreconditionNotMetForSettingObjectInMotionException("No velocity!");
        else if (mazeAlgorithm == MazeAlgorithm.None)
            throw new PreconditionNotMetForSettingObjectInMotionException("No maze algorithm!");
        else {

            final boolean oldInMotion = this.inMotion;
            this.inMotion = inMotion;

            if (oldInMotion != inMotion)
                propertyChangeSupport.firePropertyChange(
                        "inMotion",
                        oldInMotion,
                        inMotion
                );
        }
    }

    public void setDirection(Direction direction) {

        final Direction oldDirection = this.direction;
        this.direction = direction;

        if (!oldDirection.equals(direction))
            propertyChangeSupport.firePropertyChange(
                    "direction",
                    oldDirection,
                    direction
            );
    }

    public void setMazeAlgorithm(MazeAlgorithm mazeAlgorithm) {

        final MazeAlgorithm oldMazeAlgorithm = this.mazeAlgorithm;
        this.mazeAlgorithm = mazeAlgorithm;

        if (!oldMazeAlgorithm.equals(mazeAlgorithm)) {

            mazeAlgorithmImpl = MazeAlgorithmFactory.getInstance().createMazeAlgorithm(mazeAlgorithm);
            propertyChangeSupport.firePropertyChange(
                    "mazeAlgorithm",
                    oldMazeAlgorithm,
                    mazeAlgorithm
            );
        }
    }

    public void setMazeGraphFileName(String mazeGraphFileName) {

        try (InputStream inputStream = getClass().getResourceAsStream(mazeGraphFileName)) {

            final MazeGraphV2 mazeGraph = new MazeGraphV2();
            mazeGraph.loadFromJson(inputStream);
            absMazeGraph = new AbsMazeGraph(mazeGraph);
            final String oldMazeGraphFileName = this.mazeGraphFileName;
            this.mazeGraphFileName = mazeGraphFileName;

            if (!oldMazeGraphFileName.equals(mazeGraphFileName))
                propertyChangeSupport.firePropertyChange(
                        "mazeGraphFileName",
                        oldMazeGraphFileName,
                        mazeGraphFileName
                );
        } catch (NullPointerException exception) {
            throw new PreconditionNotMetForSettingObjectInMotionException(MAZE_JSON_FILE_NAME_CANNOT_BE_NULL);
        } catch (JsonParseException | JsonMappingException exception) {
            throw new PreconditionNotMetForSettingObjectInMotionException(JSON_FILE_WITH_MAZE_GRAPH_IS_CORRUPT);
        } catch (IOException exception) {
            throw new PreconditionNotMetForSettingObjectInMotionException(MAZE_JSON_FILE_NAME_NOT_CORRECTLY_SET);
        }
    }

    private void updateAbsolutePosAndDirection(AbsPosAndDirection nextAbsPosAndDirection) {

        setXPosition(nextAbsPosAndDirection.getAbsolutePosition().getX());
        setYPosition(nextAbsPosAndDirection.getAbsolutePosition().getY());
        setDirection(nextAbsPosAndDirection.getDirection());
    }

    @Override
    public void elapseTime(double milliseconds) {

        super.elapseTime(milliseconds);

        double range = milliseconds * getVelocity();
        double absDiff;

        if (isInMotion()) {

            while (range > 0) {

                final AbsPosAndDirection nextAbsPos = mazeAlgorithmImpl.getNextAbsPos(
                        absMazeGraph,
                        new AbsPosAndDirection(
                                new AbsolutePosition(getXPosition(), getYPosition()),
                                direction
                        )
                );

                switch (nextAbsPos.getDirection()) {

                    case Up:
                        absDiff = getYPosition() - nextAbsPos.getAbsolutePosition().getY();
                        if (range < absDiff) {
                            addToYPosition(-1.0 * range);
                            setDirection(nextAbsPos.getDirection());
                            range = 0;
                        } else {
                            updateAbsolutePosAndDirection(nextAbsPos);
                            range -= absDiff;
                        }
                        break;
                    case Down:
                        absDiff = nextAbsPos.getAbsolutePosition().getY() - getYPosition();
                        if (range < absDiff) {
                            addToYPosition(range);
                            setDirection(nextAbsPos.getDirection());
                            range = 0;
                        } else {
                            updateAbsolutePosAndDirection(nextAbsPos);
                            range -= absDiff;
                        }
                        break;
                    case Left:
                        absDiff = getXPosition() - nextAbsPos.getAbsolutePosition().getX();
                        if (range < absDiff) {
                            addToXPosition(-1.0 * range);
                            setDirection(nextAbsPos.getDirection());
                            range = 0;
                        } else {
                            updateAbsolutePosAndDirection(nextAbsPos);
                            range -= absDiff;
                        }
                        break;
                    case Right:
                        absDiff = nextAbsPos.getAbsolutePosition().getX() - getXPosition();
                        if (range < absDiff) {
                            addToXPosition(range);
                            setDirection(nextAbsPos.getDirection());
                            range = 0;
                        } else {
                            updateAbsolutePosAndDirection(nextAbsPos);
                            range -= absDiff;
                        }
                        break;
                }
            }
        }
    }
}
