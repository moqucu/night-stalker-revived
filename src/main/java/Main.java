import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.*;
import utility.WallFactory;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {

        World world = new World();
        Game game = new Game(primaryStage, world);

        world.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image("images/Sprite 001 - Wall.png"))
                        .initialPosition(GameObject.Position.builder().horizontal(0).vertical(0).build())
                        .build()
        ));

        world.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image("images/Sprite 002 - Wall.png"))
                        .initialPosition(GameObject.Position.builder().horizontal(1).vertical(0).build())
                        .build()
        ));
        world.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image("images/Sprite 003 - Wall.png"))
                        .initialPosition(GameObject.Position.builder().horizontal(2).vertical(0).build())
                        .build()
        ));
        world.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image("images/Sprite 004 - Wall.png"))
                        .initialPosition(GameObject.Position.builder().horizontal(3).vertical(0).build())
                        .build()
        ));
        world.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image("images/Sprite 005 - Wall.png"))
                        .initialPosition(GameObject.Position.builder().horizontal(4).vertical(0).build())
                        .build()
        ));
        world.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image("images/Sprite 006 - Wall.png"))
                        .initialPosition(GameObject.Position.builder().horizontal(5).vertical(0).build())
                        .initialPosition(GameObject.Position.builder().horizontal(7).vertical(0).build())
                        .initialPosition(GameObject.Position.builder().horizontal(9).vertical(0).build())
                        .initialPosition(GameObject.Position.builder().horizontal(11).vertical(0).build())
                        .initialPosition(GameObject.Position.builder().horizontal(13).vertical(0).build())
                        .initialPosition(GameObject.Position.builder().horizontal(15).vertical(0).build())
                        .initialPosition(GameObject.Position.builder().horizontal(17).vertical(0).build())
                        .build()
        ));
        world.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image("images/Sprite 007 - Wall.png"))
                        .initialPosition(GameObject.Position.builder().horizontal(6).vertical(0).build())
                        .initialPosition(GameObject.Position.builder().horizontal(8).vertical(0).build())
                        .initialPosition(GameObject.Position.builder().horizontal(10).vertical(0).build())
                        .initialPosition(GameObject.Position.builder().horizontal(12).vertical(0).build())
                        .initialPosition(GameObject.Position.builder().horizontal(14).vertical(0).build())
                        .initialPosition(GameObject.Position.builder().horizontal(16).vertical(0).build())
                        .initialPosition(GameObject.Position.builder().horizontal(18).vertical(0).build())
                        .build()
        ));
        world.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image("images/Sprite 008 - Wall.png"))
                        .initialPosition(GameObject.Position.builder().horizontal(19).vertical(0).build())
                        .build()
        ));
        world.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image("images/Sprite 009 - Wall.png"))
                        .initialPosition(GameObject.Position.builder().horizontal(0).vertical(1).build())
                        .build()
        ));

        world.addGameObject(0, new SpiderWeb(
                new Image("images/Sprite 010 - Web.png"),
                GameObject.Position.builder().horizontal(1).vertical(1).build()
        ));
        world.addGameObject(0, new SpiderWeb(
                new Image("images/Sprite 011 - Web.png"),
                GameObject.Position.builder().horizontal(2).vertical(1).build()
        ));
        world.addGameObject(0, new SpiderWeb(
                new Image("images/Sprite 012 - Web.png"),
                GameObject.Position.builder().horizontal(3).vertical(1).build()
        ));

        world.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image("images/Sprite 013 - Wall.png"))
                        .initialPosition(GameObject.Position.builder().horizontal(4).vertical(1).build())
                        .initialPosition(GameObject.Position.builder().horizontal(4).vertical(3).build())
                        .initialPosition(GameObject.Position.builder().horizontal(2).vertical(4).build())
                        .initialPosition(GameObject.Position.builder().horizontal(12).vertical(4).build())
                        .initialPosition(GameObject.Position.builder().horizontal(2).vertical(6).build())
                        .initialPosition(GameObject.Position.builder().horizontal(6).vertical(6).build())
                        .initialPosition(GameObject.Position.builder().horizontal(4).vertical(8).build())
                        .initialPosition(GameObject.Position.builder().horizontal(12).vertical(8).build())
                        .build()
        ));
        world.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image("images/Sprite 014 - Wall.png"))
                        .initialPosition(GameObject.Position.builder().horizontal(19).vertical(1).build())
                        .initialPosition(GameObject.Position.builder().horizontal(19).vertical(5).build())
                        .initialPosition(GameObject.Position.builder().horizontal(19).vertical(8).build())
                        .build()
        ));
        world.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image("images/Sprite 015 - Wall.png"))
                        .initialPosition(GameObject.Position.builder().horizontal(0).vertical(2).build())
                        .build()
        ));

        world.addGameObject(0, new SpiderWeb(
                new Image("images/Sprite 016 - Web.png"),
                GameObject.Position.builder().horizontal(1).vertical(2).build()
        ));

        world.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image("images/Sprite 017 - Wall.png"))
                        .initialPosition(GameObject.Position.builder().horizontal(2).vertical(2).build())
                        .build()
        ));

        world.addGameObject(0, new SpiderWeb(
                new Image("images/Sprite 018 - Web.png"),
                GameObject.Position.builder().horizontal(3).vertical(2).build()
        ));

        world.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image("images/Sprite 019 - Wall.png"))
                        .initialPosition(GameObject.Position.builder().horizontal(4).vertical(2).build())
                        .initialPosition(GameObject.Position.builder().horizontal(12).vertical(3).build())
                        .initialPosition(GameObject.Position.builder().horizontal(14).vertical(3).build())
                        .initialPosition(GameObject.Position.builder().horizontal(2).vertical(5).build())
                        .initialPosition(GameObject.Position.builder().horizontal(6).vertical(5).build())
                        .initialPosition(GameObject.Position.builder().horizontal(4).vertical(7).build())
                        .build()
        ));
        world.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image("images/Sprite 020 - Wall.png"))
                        .initialPosition(GameObject.Position.builder().horizontal(6).vertical(2).build())
                        .initialPosition(GameObject.Position.builder().horizontal(11).vertical(2).build())
                        .initialPosition(GameObject.Position.builder().horizontal(16).vertical(2).build())
                        .initialPosition(GameObject.Position.builder().horizontal(18).vertical(4).build())
                        .initialPosition(GameObject.Position.builder().horizontal(8).vertical(7).build())
                        .initialPosition(GameObject.Position.builder().horizontal(6).vertical(9).build())
                        .initialPosition(GameObject.Position.builder().horizontal(14).vertical(9).build())
                        .build()
        ));
        world.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image("images/Sprite 021 - Wall.png"))
                        .initialPosition(GameObject.Position.builder().horizontal(7).vertical(2).build())
                        .initialPosition(GameObject.Position.builder().horizontal(15).vertical(6).build())
                        .initialPosition(GameObject.Position.builder().horizontal(9).vertical(7).build())
                        .initialPosition(GameObject.Position.builder().horizontal(11).vertical(7).build())
                        .initialPosition(GameObject.Position.builder().horizontal(7).vertical(9).build())
                        .initialPosition(GameObject.Position.builder().horizontal(9).vertical(9).build())
                        .initialPosition(GameObject.Position.builder().horizontal(15).vertical(9).build())
                        .build()
        ));
        world.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image("images/Sprite 022 - Wall.png"))
                        .initialPosition(GameObject.Position.builder().horizontal(8).vertical(2).build())
                        .initialPosition(GameObject.Position.builder().horizontal(15).vertical(4).build())
                        .initialPosition(GameObject.Position.builder().horizontal(16).vertical(6).build())
                        .initialPosition(GameObject.Position.builder().horizontal(10).vertical(7).build())
                        .initialPosition(GameObject.Position.builder().horizontal(1).vertical(9).build())
                        .initialPosition(GameObject.Position.builder().horizontal(8).vertical(9).build())
                        .build()
        ));
        world.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image("images/Sprite 023 - Wall.png"))
                        .initialPosition(GameObject.Position.builder().horizontal(9).vertical(2).build())
                        .initialPosition(GameObject.Position.builder().horizontal(17).vertical(2).build())
                        .initialPosition(GameObject.Position.builder().horizontal(16).vertical(4).build())
                        .initialPosition(GameObject.Position.builder().horizontal(17).vertical(6).build())
                        .initialPosition(GameObject.Position.builder().horizontal(2).vertical(9).build())
                        .initialPosition(GameObject.Position.builder().horizontal(10).vertical(9).build())
                        .build()
        ));
        world.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image("images/Sprite 024 - Wall.png"))
                        .initialPosition(GameObject.Position.builder().horizontal(12).vertical(2).build())
                        .initialPosition(GameObject.Position.builder().horizontal(12).vertical(7).build())
                        .build()
        ));
        world.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image("images/Sprite 025 - Wall.png"))
                        .initialPosition(GameObject.Position.builder().horizontal(14).vertical(2).build())
                        .initialPosition(GameObject.Position.builder().horizontal(2).vertical(3).build())
                        .initialPosition(GameObject.Position.builder().horizontal(6).vertical(4).build())
                        .initialPosition(GameObject.Position.builder().horizontal(4).vertical(6).build())
                        .build()
        ));
        world.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image("images/Sprite 026 - Wall.png"))
                        .initialPosition(GameObject.Position.builder().horizontal(19).vertical(2).build())
                        .initialPosition(GameObject.Position.builder().horizontal(19).vertical(6).build())
                        .initialPosition(GameObject.Position.builder().horizontal(19).vertical(9).build())
                        .build()
        ));
        world.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image("images/Sprite 027 - Wall.png"))
                        .initialPosition(GameObject.Position.builder().horizontal(0).vertical(3).build())
                        .initialPosition(GameObject.Position.builder().horizontal(0).vertical(6).build())
                        .build()
        ));
        world.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image("images/Sprite 028 - Wall.png"))
                        .initialPosition(GameObject.Position.builder().horizontal(19).vertical(3).build())
                        .initialPosition(GameObject.Position.builder().horizontal(19).vertical(7).build())
                        .initialPosition(GameObject.Position.builder().horizontal(19).vertical(10).build())
                        .build()
        ));
        world.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image("images/Sprite 029 - Wall.png"))
                        .initialPosition(GameObject.Position.builder().horizontal(0).vertical(4).build())
                        .initialPosition(GameObject.Position.builder().horizontal(0).vertical(7).build())
                        .initialPosition(GameObject.Position.builder().horizontal(0).vertical(10).build())
                        .build()
        ));
        world.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image("images/Sprite 030 - Wall.png"))
                        .initialPosition(GameObject.Position.builder().horizontal(4).vertical(4).build())
                        .initialPosition(GameObject.Position.builder().horizontal(12).vertical(5).build())
                        .initialPosition(GameObject.Position.builder().horizontal(2).vertical(7).build())
                        .initialPosition(GameObject.Position.builder().horizontal(6).vertical(7).build())
                        .initialPosition(GameObject.Position.builder().horizontal(14).vertical(7).build())
                        .initialPosition(GameObject.Position.builder().horizontal(4).vertical(9).build())
                        .initialPosition(GameObject.Position.builder().horizontal(12).vertical(9).build())
                        .build()
        ));

        world.addGameObject(0, new SolidBunker(
                new Image("images/Sprite 031 - Bunker Solid.png"),
                GameObject.Position.builder().horizontal(8).vertical(4).build()
        ));

        world.addGameObject(0, new Bunker(
                new Image("images/Sprite 032 - Bunker.png"),
                GameObject.Position.builder().horizontal(9).vertical(4).build()
        ));

        world.addGameObject(0, new SolidBunker(
                new Image("images/Sprite 033 - Bunker Solid.png"),
                GameObject.Position.builder().horizontal(10).vertical(4).build()
        ));

        world.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image("images/Sprite 034 - Wall.png"))
                        .initialPosition(GameObject.Position.builder().horizontal(14).vertical(4).build())
                        .build()
        ));
        world.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image("images/Sprite 035 - Wall.png"))
                        .initialPosition(GameObject.Position.builder().horizontal(19).vertical(4).build())
                        .initialPosition(GameObject.Position.builder().horizontal(16).vertical(9).build())
                        .build()
        ));
        world.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image("images/Sprite 036 - Wall.png"))
                        .initialPosition(GameObject.Position.builder().horizontal(0).vertical(5).build())
                        .initialPosition(GameObject.Position.builder().horizontal(0).vertical(8).build())
                        .build()
        ));

        world.addGameObject(0, new SolidBunker(
                new Image("images/Sprite 037 - Bunker Solid.png"),
                GameObject.Position.builder().horizontal(8).vertical(5).build()
        ));


        world.addGameObject(0, new HalfSolidBunker(
                new Image("images/Sprite 038 - Bunker Half-Solid.png"),
                GameObject.Position.builder().horizontal(9).vertical(5).build()
        ));

        world.addGameObject(0, new SolidBunker(
                new Image("images/Sprite 039 - Bunker Solid.png"),
                GameObject.Position.builder().horizontal(10).vertical(5).build()
        ));


        world.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image("images/Sprite 040 - Wall.png"))
                        .initialPosition(GameObject.Position.builder().horizontal(14).vertical(6).build())
                        .build()
        ));
        world.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image("images/Sprite 041 - Wall.png"))
                        .initialPosition(GameObject.Position.builder().horizontal(16).vertical(8).build())
                        .build()
        ));
        world.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image("images/Sprite 042 - Wall.png"))
                        .initialPosition(GameObject.Position.builder().horizontal(17).vertical(8).build())
                        .build()
        ));
        world.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image("images/Sprite 043 - Wall.png"))
                        .initialPosition(GameObject.Position.builder().horizontal(0).vertical(9).build())
                        .build()
        ));
        world.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image("images/Sprite 044 - Wall.png"))
                        .initialPosition(GameObject.Position.builder().horizontal(17).vertical(9).build())
                        .build()
        ));
        world.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image("images/Sprite 045 - Wall.png"))
                        .initialPosition(GameObject.Position.builder().horizontal(0).vertical(11).build())
                        .build()
        ));
        world.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image("images/Sprite 046 - Wall.png"))
                        .initialPosition(GameObject.Position.builder().horizontal(1).vertical(11).build())
                        .initialPosition(GameObject.Position.builder().horizontal(3).vertical(11).build())
                        .initialPosition(GameObject.Position.builder().horizontal(5).vertical(11).build())
                        .initialPosition(GameObject.Position.builder().horizontal(7).vertical(11).build())
                        .initialPosition(GameObject.Position.builder().horizontal(9).vertical(11).build())
                        .initialPosition(GameObject.Position.builder().horizontal(11).vertical(11).build())
                        .initialPosition(GameObject.Position.builder().horizontal(13).vertical(11).build())
                        .initialPosition(GameObject.Position.builder().horizontal(15).vertical(11).build())
                        .initialPosition(GameObject.Position.builder().horizontal(17).vertical(11).build())
                        .build()
        ));
        world.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image("images/Sprite 047 - Wall.png"))
                        .initialPosition(GameObject.Position.builder().horizontal(2).vertical(11).build())
                        .initialPosition(GameObject.Position.builder().horizontal(4).vertical(11).build())
                        .initialPosition(GameObject.Position.builder().horizontal(6).vertical(11).build())
                        .initialPosition(GameObject.Position.builder().horizontal(8).vertical(11).build())
                        .initialPosition(GameObject.Position.builder().horizontal(10).vertical(11).build())
                        .initialPosition(GameObject.Position.builder().horizontal(12).vertical(11).build())
                        .initialPosition(GameObject.Position.builder().horizontal(14).vertical(11).build())
                        .initialPosition(GameObject.Position.builder().horizontal(16).vertical(11).build())
                        .initialPosition(GameObject.Position.builder().horizontal(18).vertical(11).build())
                        .build()
        ));
        world.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image("images/Sprite 048 - Wall.png"))
                        .initialPosition(GameObject.Position.builder().horizontal(19).vertical(11).build())
                        .build()
        ));

        game.addRenderable(world);
        game.addUpdatable(world);

        Bat bat = new Bat();
        world.addGameObject(2, bat);

        game.start();
    }

    public static void main(String[] args) {

        launch(args);
    }
}
