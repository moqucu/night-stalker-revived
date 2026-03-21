package org.moqucu.games.nightstalker.model;

/**
 * String constants for property names used in firePropertyChange and PropertyChangeListener callbacks.
 * Centralizing these prevents typo-induced silent failures in the observer wiring.
 */
public final class PropertyNames {

    private PropertyNames() {}

    // DisplayableObject
    public static final String X_POSITION = "XPosition";
    public static final String Y_POSITION = "YPosition";
    public static final String IMAGE_MAP_FILE_NAME = "imageMapFileName";
    public static final String INITIAL_IMAGE_INDEX = "initialImageIndex";
    public static final String OBJECT_VISIBLE = "objectVisible";
    public static final String OBJECT_ID = "objectId";

    // AnimatedObject
    public static final String LOWER_ANIMATION_INDEX = "lowerAnimationIndex";
    public static final String UPPER_ANIMATION_INDEX = "upperAnimationIndex";
    public static final String ANIMATED = "animated";
    public static final String FRAME_RATE = "frameRate";
    public static final String IMAGE_INDEX = "imageIndex";

    // MovableObject
    public static final String VELOCITY = "velocity";
    public static final String IN_MOTION = "inMotion";
    public static final String DIRECTION = "direction";
    public static final String MAZE_ALGORITHM = "mazeAlgorithm";
    public static final String MAZE_GRAPH_FILE_NAME = "mazeGraphFileName";

    // NightStalker
    public static final String RUNNING = "running";
    public static final String WEAPON = "weapon";

    // Bat
    public static final String SLEEP_TIME = "sleepTime";

    // Bullet
    public static final String FIRED = "fired";
    public static final String SOURCE = "source";

    // Weapon
    public static final String ROUNDS = "rounds";

    // Lives / Scores
    public static final String LIVES = "lives";
    public static final String SCORES = "scores";
}
