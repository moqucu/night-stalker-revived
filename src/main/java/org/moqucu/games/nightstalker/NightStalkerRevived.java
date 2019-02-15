package org.moqucu.games.nightstalker;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.stage.Stage;
import org.moqucu.games.nightstalker.utility.BackGroundMusicLoop;
import org.moqucu.games.nightstalker.controller.FxmlView;
import org.moqucu.games.nightstalker.view.StageManager;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
public class NightStalkerRevived extends Application {

    private ConfigurableApplicationContext springContext;

    private StageManager stageManager;

    public static String translate(String relativePath) {

        return NightStalkerRevived.class.getResource(relativePath).toExternalForm();
    }

    private ConfigurableApplicationContext bootstrapSpringApplicationContext() {

        SpringApplicationBuilder builder = new SpringApplicationBuilder(NightStalkerRevived.class);
        String[] args = getParameters().getRaw().toArray(new String[0]);
        builder.headless(false);

        return builder.run(args);
    }

    @Override
    public void init() {

        springContext = bootstrapSpringApplicationContext();
    }

    private void displayInitialScene() {

        stageManager.switchScene(FxmlView.SPLASH_SCREEN);
    }

    @Override
    public void start(Stage primaryStage) {

        stageManager = springContext.getBean(StageManager.class, primaryStage);
        displayInitialScene();

        Task backGroundMusicLoop = new BackGroundMusicLoop();
        ExecutorService service = Executors.newFixedThreadPool(4);
        service.execute(backGroundMusicLoop);
    }

    @Override
    public void stop() {

        springContext.close();
    }

    public static void main(String[] args) {

        Application.launch(args);
    }

    /*private Maze createMazeAndAddSprites() {

        maze.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image(translate("images/Sprite_013_Wall.png")))
                        .initialPosition(GameObject.Position.builder().horizontal(2).vertical(4).build())
                        .initialPosition(GameObject.Position.builder().horizontal(12).vertical(4).build())
                        .initialPosition(GameObject.Position.builder().horizontal(2).vertical(6).build())
                        .initialPosition(GameObject.Position.builder().horizontal(6).vertical(6).build())
                        .initialPosition(GameObject.Position.builder().horizontal(4).vertical(8).build())
                        .initialPosition(GameObject.Position.builder().horizontal(12).vertical(8).build())
                        .build()
        ));
        maze.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image(translate("images/Sprite_014_Wall.png")))
                        .initialPosition(GameObject.Position.builder().horizontal(19).vertical(5).build())
                        .initialPosition(GameObject.Position.builder().horizontal(19).vertical(8).build())
                        .build()
        ));



        maze.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image(translate("images/Sprite_019_Wall.png")))
                        .initialPosition(GameObject.Position.builder().horizontal(2).vertical(5).build())
                        .initialPosition(GameObject.Position.builder().horizontal(6).vertical(5).build())
                        .initialPosition(GameObject.Position.builder().horizontal(4).vertical(7).build())
                        .build()
        ));
        maze.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image(translate("images/Sprite_020_Wall.png")))
                        .initialPosition(GameObject.Position.builder().horizontal(18).vertical(4).build())
                        .initialPosition(GameObject.Position.builder().horizontal(8).vertical(7).build())
                        .initialPosition(GameObject.Position.builder().horizontal(6).vertical(9).build())
                        .initialPosition(GameObject.Position.builder().horizontal(14).vertical(9).build())
                        .build()
        ));
        maze.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image(translate("images/Sprite_021_Wall.png")))
                        .initialPosition(GameObject.Position.builder().horizontal(15).vertical(6).build())
                        .initialPosition(GameObject.Position.builder().horizontal(9).vertical(7).build())
                        .initialPosition(GameObject.Position.builder().horizontal(11).vertical(7).build())
                        .initialPosition(GameObject.Position.builder().horizontal(7).vertical(9).build())
                        .initialPosition(GameObject.Position.builder().horizontal(9).vertical(9).build())
                        .initialPosition(GameObject.Position.builder().horizontal(15).vertical(9).build())
                        .build()
        ));
        maze.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image(translate("images/Sprite_022_Wall.png")))
                        .initialPosition(GameObject.Position.builder().horizontal(15).vertical(4).build())
                        .initialPosition(GameObject.Position.builder().horizontal(16).vertical(6).build())
                        .initialPosition(GameObject.Position.builder().horizontal(10).vertical(7).build())
                        .initialPosition(GameObject.Position.builder().horizontal(1).vertical(9).build())
                        .initialPosition(GameObject.Position.builder().horizontal(8).vertical(9).build())
                        .build()
        ));
        maze.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image(translate("images/Sprite_023_Wall.png")))
                        .initialPosition(GameObject.Position.builder().horizontal(16).vertical(4).build())
                        .initialPosition(GameObject.Position.builder().horizontal(17).vertical(6).build())
                        .initialPosition(GameObject.Position.builder().horizontal(2).vertical(9).build())
                        .initialPosition(GameObject.Position.builder().horizontal(10).vertical(9).build())
                        .build()
        ));
        maze.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image(translate("images/Sprite_024_Wall.png")))
                        .initialPosition(GameObject.Position.builder().horizontal(12).vertical(7).build())
                        .build()
        ));
        maze.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image(translate("images/Sprite_025_Wall.png")))
                        .initialPosition(GameObject.Position.builder().horizontal(6).vertical(4).build())
                        .initialPosition(GameObject.Position.builder().horizontal(4).vertical(6).build())
                        .build()
        ));
        maze.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image(translate("images/Sprite_026_Wall.png")))
                        .initialPosition(GameObject.Position.builder().horizontal(19).vertical(6).build())
                        .initialPosition(GameObject.Position.builder().horizontal(19).vertical(9).build())
                        .build()
        ));
        maze.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image(translate("images/Sprite_027_Wall.png")))
                        .initialPosition(GameObject.Position.builder().horizontal(0).vertical(6).build())
                        .build()
        ));
        maze.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image(translate("images/Sprite_028_Wall.png")))
                        .initialPosition(GameObject.Position.builder().horizontal(19).vertical(7).build())
                        .initialPosition(GameObject.Position.builder().horizontal(19).vertical(10).build())
                        .build()
        ));
        maze.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image(translate("images/Sprite_029_Wall.png")))
                        .initialPosition(GameObject.Position.builder().horizontal(0).vertical(4).build())
                        .initialPosition(GameObject.Position.builder().horizontal(0).vertical(7).build())
                        .initialPosition(GameObject.Position.builder().horizontal(0).vertical(10).build())
                        .build()
        ));
        maze.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image(translate("images/Sprite_030_Wall.png")))
                        .initialPosition(GameObject.Position.builder().horizontal(4).vertical(4).build())
                        .initialPosition(GameObject.Position.builder().horizontal(12).vertical(5).build())
                        .initialPosition(GameObject.Position.builder().horizontal(2).vertical(7).build())
                        .initialPosition(GameObject.Position.builder().horizontal(6).vertical(7).build())
                        .initialPosition(GameObject.Position.builder().horizontal(14).vertical(7).build())
                        .initialPosition(GameObject.Position.builder().horizontal(4).vertical(9).build())
                        .initialPosition(GameObject.Position.builder().horizontal(12).vertical(9).build())
                        .build()
        ));

        maze.addGameObject(0, new SolidBunker(
                new Image(translate("images/Sprite_031_Bunker_Solid.png")),
                GameObject.Position.builder().horizontal(8).vertical(4).build()
        ));

        maze.addGameObject(0, new Bunker(
                new Image(translate("images/Sprite_032_Bunker.png")),
                GameObject.Position.builder().horizontal(9).vertical(4).build()
        ));

        maze.addGameObject(0, new SolidBunker(
                new Image(translate("images/Sprite_033_Bunker_Solid.png")),
                GameObject.Position.builder().horizontal(10).vertical(4).build()
        ));

        maze.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image(translate("images/Sprite_034_Wall.png")))
                        .initialPosition(GameObject.Position.builder().horizontal(14).vertical(4).build())
                        .build()
        ));
        maze.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image(translate("images/Sprite_035_Wall.png")))
                        .initialPosition(GameObject.Position.builder().horizontal(19).vertical(4).build())
                        .initialPosition(GameObject.Position.builder().horizontal(16).vertical(9).build())
                        .build()
        ));
        maze.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image(translate("images/Sprite_036_Wall.png")))
                        .initialPosition(GameObject.Position.builder().horizontal(0).vertical(5).build())
                        .initialPosition(GameObject.Position.builder().horizontal(0).vertical(8).build())
                        .build()
        ));

        maze.addGameObject(0, new SolidBunker(
                new Image(translate("images/Sprite_037_Bunker_Solid.png")),
                GameObject.Position.builder().horizontal(8).vertical(5).build()
        ));


        maze.addGameObject(0, new HalfSolidBunker(
                new Image(translate("images/Sprite_038_Bunker_Half_Solid.png")),
                GameObject.Position.builder().horizontal(9).vertical(5).build()
        ));

        maze.addGameObject(0, new SolidBunker(
                new Image(translate("images/Sprite_039_Bunker_Solid.png")),
                GameObject.Position.builder().horizontal(10).vertical(5).build()
        ));


        maze.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image(translate("images/Sprite_040_Wall.png")))
                        .initialPosition(GameObject.Position.builder().horizontal(14).vertical(6).build())
                        .build()
        ));
        maze.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image(translate("images/Sprite_041_Wall.png")))
                        .initialPosition(GameObject.Position.builder().horizontal(16).vertical(8).build())
                        .build()
        ));
        maze.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image(translate("images/Sprite_042_Wall.png")))
                        .initialPosition(GameObject.Position.builder().horizontal(17).vertical(8).build())
                        .build()
        ));
        maze.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image(translate("images/Sprite_043_Wall.png")))
                        .initialPosition(GameObject.Position.builder().horizontal(0).vertical(9).build())
                        .build()
        ));
        maze.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image(translate("images/Sprite_044_Wall.png")))
                        .initialPosition(GameObject.Position.builder().horizontal(17).vertical(9).build())
                        .build()
        ));
        maze.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image(translate("images/Sprite_045_Wall.png")))
                        .initialPosition(GameObject.Position.builder().horizontal(0).vertical(11).build())
                        .build()
        ));
        maze.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image(translate("images/Sprite_046_Wall.png")))
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
        maze.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image(translate("images/Sprite_047_Wall.png")))
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
        maze.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image(translate("images/Sprite_048_Wall.png")))
                        .initialPosition(GameObject.Position.builder().horizontal(19).vertical(11).build())
                        .build()
        ));

        Bat bat1 = new Bat(17, 3, 5.0);
        maze.addGameObject(2, bat1);

        Bat bat2 = new Bat(16, 7, 2.5);
        maze.addGameObject(2, bat2);

        Spider spider = new Spider(1, 1);
        maze.addGameObject(2, spider);

        GreyRobot robot = new GreyRobot(1, 10);
        maze.addGameObject(2, robot);

        NightStalker nightStalker = new NightStalker();
        maze.addGameObject(1, nightStalker);

        Gun gun = new Gun(3.0);
        maze.addGameObject(3, gun);

        return maze;
    }*/
}
