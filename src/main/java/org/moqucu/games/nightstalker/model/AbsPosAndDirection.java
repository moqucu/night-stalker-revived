package org.moqucu.games.nightstalker.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode
@RequiredArgsConstructor
public class AbsPosAndDirection {

    @Getter
    private final AbsolutePosition absolutePosition;

    @Getter
    private final Direction direction;
}
