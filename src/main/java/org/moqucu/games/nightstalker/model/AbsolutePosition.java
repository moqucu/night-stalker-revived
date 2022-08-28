package org.moqucu.games.nightstalker.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AbsolutePosition {

    public static final double MAX_X = 639.0;

    public static final double MAX_Y = 383.0;

    private double x;

    private double y;

    public void addToX(double numberToBeAdded) {

        x = x + numberToBeAdded;
        if (x < 0.0)
            x = 0.0;
        else if (x > MAX_X)
            x = MAX_X;
    }

    public void addToY(double numberToBeAdded) {

        y = y + numberToBeAdded;
        if (y < 0.0)
            y = 0.0;
        else if (y > MAX_Y)
            y = MAX_Y;
    }
}