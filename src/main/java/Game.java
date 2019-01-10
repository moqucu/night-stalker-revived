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
import model.QuadTree;
import java.awt.Rectangle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

public class Game {

    private final long startNanoTime = System.nanoTime();

    private final AtomicLong lastNanoTime = new AtomicLong(System.nanoTime());

    private Stage primaryStage;

    private GraphicsContext graphicsContext;

    private final static int WIDTH = 640, HEIGHT = 384;

    private Set<KeyCode> input = new HashSet<>();

    private World world;

    private ArrayList<Updatable> updatables = new ArrayList<>();
    private ArrayList<Renderable> renderables = new ArrayList<>();

    //todo private QuadTree quad = new QuadTree(0, new Rectangle(0,0,WIDTH,HEIGHT));

    public Game(Stage primaryStage, World world) {

        this.primaryStage = primaryStage;
        initializeStage();
        this.world = world;
    }

    private void initializeStage() {

        Canvas canvas = new Canvas(WIDTH, HEIGHT);
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

                // Clear QuadTree
                //todo quad.clear();
                // Update QuadTree
                //todo renderables.forEach(q -> quad.insert(q.getBoundary()));  // Todo bbox() attr needed

                updatables.forEach(u -> u.update(deltaTimeSinceStart, deltaTime, input, world.getSprites()));
                renderables.forEach(r -> r.render(graphicsContext, deltaTimeSinceStart));
            }
        }.start();

        primaryStage.show();
    }
}
