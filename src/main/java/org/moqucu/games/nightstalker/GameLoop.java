package org.moqucu.games.nightstalker;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import org.moqucu.games.nightstalker.view.Maze;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import static org.moqucu.games.nightstalker.utility.GameConstants.HEIGHT;
import static org.moqucu.games.nightstalker.utility.GameConstants.WIDTH;

public class GameLoop {

    private final long startNanoTime = System.nanoTime();

    private final AtomicLong lastNanoTime = new AtomicLong(System.nanoTime());

    private Stage primaryStage;

    private GraphicsContext graphicsContext;

    private Set<KeyCode> input = new HashSet<>();

    private Maze maze;

    private StackPane root = new StackPane();

    public GameLoop(Stage primaryStage, Maze maze) {

        this.primaryStage = primaryStage;
        this.maze = maze;
        initializeStage();
    }
    @SneakyThrows
    private void initializeStage() {

        Canvas canvas = new Canvas(maze.getWidth(), maze.getHeight());
        graphicsContext = canvas.getGraphicsContext2D();

        StackPane holder = new StackPane();
        holder.setStyle("-fx-background-color: #002DFF");
        holder.getChildren().add(root);
        primaryStage.show();

        TimeUnit.SECONDS.sleep(10);
        holder.getChildren().add(canvas);

        Pane root = new Pane();
        root.getChildren().add(holder);

        /* Initialise input */

        Scene scene = new Scene(root, WIDTH, HEIGHT);
        scene.setOnKeyPressed(event -> input.add(event.getCode()));
        scene.setOnKeyReleased(event -> input.remove(event.getCode()));

        primaryStage.setTitle("Night Stalker Revived");
        primaryStage.setScene(scene);
    }

    void start() {

        new AnimationTimer() {

            public void handle(long currentNanoTime) {

                /* calculate time since last update */
                double deltaTimeSinceStart = (currentNanoTime - startNanoTime) / 1000000000.0;
                double deltaTime = (currentNanoTime - lastNanoTime.getAndSet(currentNanoTime)) / 1000000000.0;

                maze.update(deltaTimeSinceStart, deltaTime, input, null);
            }
        }.start();

        primaryStage.show();
    }
}
