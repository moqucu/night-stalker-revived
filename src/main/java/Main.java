import javafx.application.Application;
import javafx.stage.Stage;
import model.Bat;
import model.World;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {

        Game game = new Game(primaryStage);

        World world = new World();
        game.addRenderable(world);

        Bat bat = new Bat();
        game.addRenderable(bat);
        game.addUpdatable(bat);

        game.start();
    }



    public static void main(String[] args) {

        launch(args);
    }
}
