package org.moqucu.games.nightstalker.model;

public record AbsPosAndDirection(AbsolutePosition absolutePosition, Direction direction) {

    public AbsolutePosition getAbsolutePosition() {
        return absolutePosition();
    }

    public Direction getDirection() {
        return direction();
    }
}
