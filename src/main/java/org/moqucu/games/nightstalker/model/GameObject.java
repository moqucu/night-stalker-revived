package org.moqucu.games.nightstalker.model;

import lombok.Getter;

import java.util.UUID;

public abstract class GameObject {

    @Getter
    private final AbsolutePosition absolutePosition = new AbsolutePosition();

    @Getter
    private final UUID id = UUID.randomUUID();

    @Getter
    private final double width = 32.0;

    @Getter
    private final double height = 32.0;
}
