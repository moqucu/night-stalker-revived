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

        switch (mazeAlgorithm) {
            case OuterRing:
                return new OuterRing();
            case Random:
                return new Random();
            default:
                return null;
        }
    }
}
