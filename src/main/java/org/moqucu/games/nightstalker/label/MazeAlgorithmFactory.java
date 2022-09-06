package org.moqucu.games.nightstalker.label;

import org.moqucu.games.nightstalker.model.MazeAlgorithm;
import org.moqucu.games.nightstalker.model.MazeAlgorithmImpl;
import org.moqucu.games.nightstalker.model.OuterRing;

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

        if (mazeAlgorithm == MazeAlgorithm.OuterRing)
            return new OuterRing();
        else
            return null;
    }
}
