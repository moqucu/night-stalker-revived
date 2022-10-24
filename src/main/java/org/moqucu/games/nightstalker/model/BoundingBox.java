package org.moqucu.games.nightstalker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoundingBox {

    private double minX;
    private double maxX;
    private double minY;
    private double maxY;

    public boolean isOverlapping(BoundingBox withAnotherBoundingBox) {

        return maxX >= withAnotherBoundingBox.minX
                & withAnotherBoundingBox.maxX >= minX
                & maxY >= withAnotherBoundingBox.minY
                & withAnotherBoundingBox.maxY >= minY;
     }
}
