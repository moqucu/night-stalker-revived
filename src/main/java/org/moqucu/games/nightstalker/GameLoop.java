package org.moqucu.games.nightstalker;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import static org.moqucu.games.nightstalker.objects.GameObject.HEIGHT;
import static org.moqucu.games.nightstalker.objects.GameObject.WIDTH;

public class GameLoop {

    private final long startNanoTime = System.nanoTime();

    private final AtomicLong lastNanoTime = new AtomicLong(System.nanoTime());

    private Stage primaryStage;

    private GraphicsContext graphicsContext;

    private Set<KeyCode> input = new HashSet<>();

    private Maze maze;

    private StackPane root = new StackPane();

    private Scene scene = new Scene(root, 640,  384);

    private final Image splashScreen = new Image("images/Night Stalker Revived Slash Screen.png");

    private final ImageView splashScreenBackPlate = new ImageView();

    private ArrayList<Updatable> updatables = new ArrayList<>();
    private ArrayList<Renderable> renderables = new ArrayList<>();

    public GameLoop(Stage primaryStage, Maze maze) {

        this.primaryStage = primaryStage;
        this.maze = maze;
        createSplashScreenNodes();
        initializeStage();
    }

    private void createSplashScreenNodes() {
        splashScreenBackPlate.setImage(splashScreen);
        root.getChildren().add(splashScreenBackPlate);

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

    void addUpdatable(Updatable u) {

        updatables.add(u);
    }

    void addRenderable(Renderable r) {

        renderables.add(r);
    }

    void start() {

        new AnimationTimer() {

            public void handle(long currentNanoTime) {

                /* calculate time since last update */
                double deltaTimeSinceStart = (currentNanoTime - startNanoTime) / 1000000000.0;
                double deltaTime = (currentNanoTime - lastNanoTime.getAndSet(currentNanoTime)) / 1000000000.0;

                updatables.forEach(u -> u.update(deltaTimeSinceStart, deltaTime, input, null));
                renderables.forEach(r -> r.render(graphicsContext, deltaTimeSinceStart));
            }
        }.start();

        primaryStage.show();
    }
}
