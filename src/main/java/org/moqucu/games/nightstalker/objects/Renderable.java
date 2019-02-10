package org.moqucu.games.nightstalker.objects;

import javafx.scene.canvas.GraphicsContext;

public interface Renderable {

    void render(GraphicsContext gc, double deltaTime);
}