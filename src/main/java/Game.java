import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.Renderable;
import model.Updatable;
import model.World;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import static model.GameObject.HEIGHT;
import static model.GameObject.WIDTH;

public class Game {

    private final long startNanoTime = System.nanoTime();

    private final AtomicLong lastNanoTime = new AtomicLong(System.nanoTime());

    private Stage primaryStage;

    private GraphicsContext graphicsContext;

    private Set<KeyCode> input = new HashSet<>();

    private World world;

    private ArrayList<Updatable> updatables = new ArrayList<>();
    private ArrayList<Renderable> renderables = new ArrayList<>();

    public Game(Stage primaryStage, World world) {

        this.primaryStage = primaryStage;
        this.world = world;
        initializeStage();
    }

    private void initializeStage() {

        Canvas canvas = new Canvas(world.getWidth(), world.getHeight());
        graphicsContext = canvas.getGraphicsContext2D();

        StackPane holder = new StackPane();
        holder.setStyle("-fx-background-color: #002DFF");
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
