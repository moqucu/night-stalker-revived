package org.moqucu.games.nightstalker.model;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import lombok.Getter;
import lombok.Setter;
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
    @Setter
    private double velocity = 0.;

    @Getter
    private boolean inMotion = false;

    @Getter
    @Setter
    private Direction direction = Direction.Undefined;

    @Getter
    private MazeAlgorithm mazeAlgorithm = MazeAlgorithm.None;

    private MazeAlgorithmImpl mazeAlgorithmImpl;

    @Getter
    private String mazeGraphFileName = "";

    @Getter
    private AbsMazeGraph absMazeGraph;

    @Override
    public void elapseTime(long milliseconds) {

        super.elapseTime(milliseconds);

        if (isInMotion()) {

            //todo: this needs to be flushed out

            switch (getDirection()) {

                case Up:
                    getAbsolutePosition().addToY(-1.0 * milliseconds / 1000 * getVelocity());
                    break;
                case Down:
                    getAbsolutePosition().addToY(1.0 * milliseconds / 1000 * getVelocity());
                    break;
                case Left:
                    getAbsolutePosition().addToX(-1.0 * milliseconds / 1000 * getVelocity());
                    break;
                case Right:
                    getAbsolutePosition().addToX(1.0 * milliseconds / 1000 * getVelocity());
                    break;
            }
        }
    }

    public void setInMotion(boolean inMotion) {

        if (direction != Direction.Undefined)
            throw new PreconditionNotMetForSettingObjectInMotionException("Direction is undefined!");
        else if (absMazeGraph == null)
            throw new PreconditionNotMetForSettingObjectInMotionException("No maze graph!");
        else if (!absMazeGraph.isWithinBounds(getAbsolutePosition()))
            throw new PreconditionNotMetForSettingObjectInMotionException("Absolute position is out of bounds!");
        else if (velocity <= 0.)
            throw new PreconditionNotMetForSettingObjectInMotionException("No velocity!");
        else if (mazeAlgorithm == MazeAlgorithm.None)
            throw new PreconditionNotMetForSettingObjectInMotionException("No maze algorithm!");
        else
            this.inMotion = inMotion;
    }

    public void setMazeGraphFileName(String mazeGraphFileName) {

        try (InputStream inputStream = getClass().getResourceAsStream(mazeGraphFileName)) {

            final MazeGraphV2 mazeGraph = new MazeGraphV2();
            mazeGraph.loadFromJson(inputStream);
            absMazeGraph = new AbsMazeGraph(mazeGraph);
            this.mazeGraphFileName = mazeGraphFileName;

        } catch (NullPointerException exception) {
            throw new PreconditionNotMetForSettingObjectInMotionException(MAZE_JSON_FILE_NAME_CANNOT_BE_NULL);
        } catch (JsonParseException | JsonMappingException exception) {
            throw new PreconditionNotMetForSettingObjectInMotionException(JSON_FILE_WITH_MAZE_GRAPH_IS_CORRUPT);
        } catch (IOException exception) {
            throw new PreconditionNotMetForSettingObjectInMotionException(MAZE_JSON_FILE_NAME_NOT_CORRECTLY_SET);
        }
    }

    public void setMazeAlgorithm(MazeAlgorithm mazeAlgorithm) {

        mazeAlgorithmImpl = MazeAlgorithmFactory.getInstance().createMazeAlgorithm(mazeAlgorithm);
        this.mazeAlgorithm = mazeAlgorithm;
    }
}
