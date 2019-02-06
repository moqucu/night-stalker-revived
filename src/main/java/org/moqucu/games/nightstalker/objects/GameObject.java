package org.moqucu.games.nightstalker.objects;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public abstract class GameObject {

    public static final int WIDTH = 32;

    public static final int HEIGHT = 32;

    @Data
    @Builder
    public static class Position {

        private int horizontal;
        private int vertical;
    }

    GameObject(Position initialPosition) {

        this.initialPosition = initialPosition;
    }

    private Position initialPosition;
}
