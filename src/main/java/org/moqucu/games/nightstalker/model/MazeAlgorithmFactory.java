package org.moqucu.games.nightstalker.model;

public class MazeAlgorithmFactory {

    private MazeAlgorithmFactory() {
    }

    private static class BillPughSingleton {
        private static final MazeAlgorithmFactory INSTANCE = new MazeAlgorithmFactory();
    }

    public static MazeAlgorithmFactory getInstance() {

        return BillPughSingleton.INSTANCE;
    }

    public MazeAlgorithmImpl createMazeAlgorithm(MazeAlgorithm mazeAlgorithm) {

        return switch (mazeAlgorithm) {
            case OuterRing -> new OuterRing();
            case Random -> new Random();
            case FollowDirection -> new FollowDirection();
            default -> null;
        };
    }
}
