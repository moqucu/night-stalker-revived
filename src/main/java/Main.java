import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.*;
import utility.WallFactory;

public class Main extends Application {



    @Override
    public void start(Stage primaryStage) {

        World world = new World(WIDTH, HEIGHT);
        Game game = new Game(primaryStage, world);

        

        primaryStage.setHeight(HEIGHT + TITLE_BAR_HEIGHT);
        primaryStage.setWidth(WIDTH);

        game.start();
    }

}
