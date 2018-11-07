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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Game {


    private Stage primaryStage;

    private GraphicsContext graphicsContext;

    private final static int WIDTH = 640, HEIGHT = 384;

    private Set<KeyCode> input = new HashSet<>();

    private ArrayList<Updatable> updatables = new ArrayList<>();
    private ArrayList<Renderable> renderables = new ArrayList<>();

    public Game(Stage primaryStage) {

        this.primaryStage = primaryStage;
        initializeStage();
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


        final long startNanoTime = System.nanoTime();

        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                double t = (currentNanoTime - startNanoTime) / 1000000000.0;
                updatables.forEach(u -> u.update(input));
                renderables.forEach(r -> r.render(graphicsContext, t));
            }
        }.start();

        primaryStage.show();

    }
}
