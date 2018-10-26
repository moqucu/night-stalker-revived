package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.World;

public class Main extends Application {

    private World world = new World();

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Night Stalker Revived");

        Pane root = new Pane();

        StackPane holder = new StackPane();
        Canvas canvas = new Canvas(640, 384);

        holder.getChildren().add(canvas);
        root.getChildren().add(holder);

        holder.setStyle("-fx-background-color: #002DFF");
        Scene scene = new Scene(root, 640, 384);
        primaryStage.setScene(scene);
        primaryStage.show();

        GraphicsContext gc = canvas.getGraphicsContext2D();

        world.getBuildingBlocks().forEach(buildingBlock -> buildingBlock.getPositions().forEach(
                position -> gc.drawImage(
                        buildingBlock.getImage(),
                        32 * position.getHorizontal(),
                        32 * position.getVertical()
                )));

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
