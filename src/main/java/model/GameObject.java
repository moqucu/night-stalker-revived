package model;

import lombok.Builder;
import lombok.Data;

@Data
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
