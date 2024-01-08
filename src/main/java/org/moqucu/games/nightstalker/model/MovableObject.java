package org.moqucu.games.nightstalker.model;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.io.InputStream;

@Log4j2
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

            final MazeGraph mazeGraph = new MazeGraph();
            mazeGraph.loadFromJson(inputStream);
            absMazeGraph = new AbsMazeGraph(mazeGraph);
            final String oldMazeGraphFileName = this.mazeGraphFileName;
            this.mazeGraphFileName = mazeGraphFileName;

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

    private void moveForRangeIntoDirection(double range, Direction direction) {

        switch (direction) {
            case Up -> addToYPosition(-1.0 * range);
            case Down -> addToYPosition(range);
            case Left -> addToXPosition(-1.0 * range);
            case Right -> addToXPosition(range);
        }
    }

    @Override
    public void elapseTime(double milliseconds) {

        // Figure out the required animation changes...
        super.elapseTime(milliseconds);

        // Compute the range that the object shall have moved since the last update
        double range = milliseconds / 1000 * getVelocity();

        // Repeat while object is in motion and there is still way to go...
        while (isInMotion() && range > 0) {

            // Find the next targeted position given current position and maze algorithm
            final AbsPosAndDirection nextAbsPos = mazeAlgorithmImpl.getNextAbsPos(
                    absMazeGraph,
                    new AbsPosAndDirection(
                            new AbsolutePosition(getXPosition(), getYPosition()),
                            getDirection()
                    )
            );

            // If we are already standing on the targeted position, get out of the loop
            if (nextAbsPos.getAbsolutePosition().equals(getAbsolutePosition()))
                break;

            // Memorize originally intended direction and change direction to computed one
            final Direction originalDirection = getDirection();
            setDirection(nextAbsPos.direction());

            // Compute distance to targeted position
            final double distance = getAbsolutePosition().distanceTo(nextAbsPos.absolutePosition());

            // Determine whether range or distance to targeted position is smaller and move that much
            double moveDistance = Math.min(range, distance);
            moveForRangeIntoDirection(moveDistance, getDirection());

            // Reduce range by moved distance
            range -= moveDistance;

            // If range is left without having reached target point, set direction back to original one
            // This way we can go 'around corners' without getting stuck somewhere
            if (range > 0 && getMazeAlgorithm().equals(MazeAlgorithm.FollowDirection))
                setDirection(originalDirection);
        }
    }
}
