package model;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

public interface Renderable {

    void render(GraphicsContext gc, double deltaTime);

    Rectangle2D getBoundary();
}