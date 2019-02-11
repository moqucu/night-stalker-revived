package org.moqucu.games.nightstalker;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.stage.Stage;
import org.moqucu.games.nightstalker.utility.BackGroundMusicLoop;
import org.moqucu.games.nightstalker.view.FxmlView;
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

    // private Maze maze;

    // private Font buttonFont = Font.loadFont(translate("fonts/intellect.ttf"), 12);

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
        // maze = createMazeAndAddSprites();
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

        Maze maze = new Maze(WIDTH, HEIGHT);

        maze.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image(translate("images/Sprite 001 - Wall.png")))
                        .initialPosition(GameObject.Position.builder().horizontal(0).vertical(0).build())
                        .build()
        ));

        maze.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image(translate("images/Sprite 002 - Wall.png")))
                        .initialPosition(GameObject.Position.builder().horizontal(1).vertical(0).build())
                        .build()
        ));
        maze.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image(translate("images/Sprite 003 - Wall.png")))
                        .initialPosition(GameObject.Position.builder().horizontal(2).vertical(0).build())
                        .build()
        ));
        maze.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image(translate("images/Sprite 004 - Wall.png")))
                        .initialPosition(GameObject.Position.builder().horizontal(3).vertical(0).build())
                        .build()
        ));
        maze.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image(translate("images/Sprite 005 - Wall.png")))
                        .initialPosition(GameObject.Position.builder().horizontal(4).vertical(0).build())
                        .build()
        ));
        maze.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image(translate("images/Sprite 006 - Wall.png")))
                        .initialPosition(GameObject.Position.builder().horizontal(5).vertical(0).build())
                        .initialPosition(GameObject.Position.builder().horizontal(7).vertical(0).build())
                        .initialPosition(GameObject.Position.builder().horizontal(9).vertical(0).build())
                        .initialPosition(GameObject.Position.builder().horizontal(11).vertical(0).build())
                        .initialPosition(GameObject.Position.builder().horizontal(13).vertical(0).build())
                        .initialPosition(GameObject.Position.builder().horizontal(15).vertical(0).build())
                        .initialPosition(GameObject.Position.builder().horizontal(17).vertical(0).build())
                        .build()
        ));
        maze.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image(translate("images/Sprite 007 - Wall.png")))
                        .initialPosition(GameObject.Position.builder().horizontal(6).vertical(0).build())
                        .initialPosition(GameObject.Position.builder().horizontal(8).vertical(0).build())
                        .initialPosition(GameObject.Position.builder().horizontal(10).vertical(0).build())
                        .initialPosition(GameObject.Position.builder().horizontal(12).vertical(0).build())
                        .initialPosition(GameObject.Position.builder().horizontal(14).vertical(0).build())
                        .initialPosition(GameObject.Position.builder().horizontal(16).vertical(0).build())
                        .initialPosition(GameObject.Position.builder().horizontal(18).vertical(0).build())
                        .build()
        ));
        maze.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image(translate("images/Sprite 008 - Wall.png")))
                        .initialPosition(GameObject.Position.builder().horizontal(19).vertical(0).build())
                        .build()
        ));
        maze.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image(translate("images/Sprite 009 - Wall.png")))
                        .initialPosition(GameObject.Position.builder().horizontal(0).vertical(1).build())
                        .build()
        ));

        maze.addGameObject(0, new SpiderWeb(
                new Image(translate("images/Sprite 010 - Web.png")),
                GameObject.Position.builder().horizontal(1).vertical(1).build()
        ));
        maze.addGameObject(0, new SpiderWeb(
                new Image(translate("images/Sprite 011 - Web.png")),
                GameObject.Position.builder().horizontal(2).vertical(1).build()
        ));
        maze.addGameObject(0, new SpiderWeb(
                new Image(translate("images/Sprite 012 - Web.png")),
                GameObject.Position.builder().horizontal(3).vertical(1).build()
        ));

        maze.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image(translate("images/Sprite 013 - Wall.png")))
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
        maze.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image(translate("images/Sprite 014 - Wall.png")))
                        .initialPosition(GameObject.Position.builder().horizontal(19).vertical(1).build())
                        .initialPosition(GameObject.Position.builder().horizontal(19).vertical(5).build())
                        .initialPosition(GameObject.Position.builder().horizontal(19).vertical(8).build())
                        .build()
        ));
        maze.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image(translate("images/Sprite 015 - Wall.png")))
                        .initialPosition(GameObject.Position.builder().horizontal(0).vertical(2).build())
                        .build()
        ));

        maze.addGameObject(0, new SpiderWeb(
                new Image(translate("images/Sprite 016 - Web.png")),
                GameObject.Position.builder().horizontal(1).vertical(2).build()
        ));

        maze.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image(translate("images/Sprite 017 - Wall.png")))
                        .initialPosition(GameObject.Position.builder().horizontal(2).vertical(2).build())
                        .build()
        ));

        maze.addGameObject(0, new SpiderWeb(
                new Image(translate("images/Sprite 018 - Web.png")),
                GameObject.Position.builder().horizontal(3).vertical(2).build()
        ));

        maze.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image(translate("images/Sprite 019 - Wall.png")))
                        .initialPosition(GameObject.Position.builder().horizontal(4).vertical(2).build())
                        .initialPosition(GameObject.Position.builder().horizontal(12).vertical(3).build())
                        .initialPosition(GameObject.Position.builder().horizontal(14).vertical(3).build())
                        .initialPosition(GameObject.Position.builder().horizontal(2).vertical(5).build())
                        .initialPosition(GameObject.Position.builder().horizontal(6).vertical(5).build())
                        .initialPosition(GameObject.Position.builder().horizontal(4).vertical(7).build())
                        .build()
        ));
        maze.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image(translate("images/Sprite 020 - Wall.png")))
                        .initialPosition(GameObject.Position.builder().horizontal(6).vertical(2).build())
                        .initialPosition(GameObject.Position.builder().horizontal(11).vertical(2).build())
                        .initialPosition(GameObject.Position.builder().horizontal(16).vertical(2).build())
                        .initialPosition(GameObject.Position.builder().horizontal(18).vertical(4).build())
                        .initialPosition(GameObject.Position.builder().horizontal(8).vertical(7).build())
                        .initialPosition(GameObject.Position.builder().horizontal(6).vertical(9).build())
                        .initialPosition(GameObject.Position.builder().horizontal(14).vertical(9).build())
                        .build()
        ));
        maze.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image(translate("images/Sprite 021 - Wall.png")))
                        .initialPosition(GameObject.Position.builder().horizontal(7).vertical(2).build())
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
                        .image(new Image(translate("images/Sprite 022 - Wall.png")))
                        .initialPosition(GameObject.Position.builder().horizontal(8).vertical(2).build())
                        .initialPosition(GameObject.Position.builder().horizontal(15).vertical(4).build())
                        .initialPosition(GameObject.Position.builder().horizontal(16).vertical(6).build())
                        .initialPosition(GameObject.Position.builder().horizontal(10).vertical(7).build())
                        .initialPosition(GameObject.Position.builder().horizontal(1).vertical(9).build())
                        .initialPosition(GameObject.Position.builder().horizontal(8).vertical(9).build())
                        .build()
        ));
        maze.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image(translate("images/Sprite 023 - Wall.png")))
                        .initialPosition(GameObject.Position.builder().horizontal(9).vertical(2).build())
                        .initialPosition(GameObject.Position.builder().horizontal(17).vertical(2).build())
                        .initialPosition(GameObject.Position.builder().horizontal(16).vertical(4).build())
                        .initialPosition(GameObject.Position.builder().horizontal(17).vertical(6).build())
                        .initialPosition(GameObject.Position.builder().horizontal(2).vertical(9).build())
                        .initialPosition(GameObject.Position.builder().horizontal(10).vertical(9).build())
                        .build()
        ));
        maze.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image(translate("images/Sprite 024 - Wall.png")))
                        .initialPosition(GameObject.Position.builder().horizontal(12).vertical(2).build())
                        .initialPosition(GameObject.Position.builder().horizontal(12).vertical(7).build())
                        .build()
        ));
        maze.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image(translate("images/Sprite 025 - Wall.png")))
                        .initialPosition(GameObject.Position.builder().horizontal(14).vertical(2).build())
                        .initialPosition(GameObject.Position.builder().horizontal(2).vertical(3).build())
                        .initialPosition(GameObject.Position.builder().horizontal(6).vertical(4).build())
                        .initialPosition(GameObject.Position.builder().horizontal(4).vertical(6).build())
                        .build()
        ));
        maze.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image(translate("images/Sprite 026 - Wall.png")))
                        .initialPosition(GameObject.Position.builder().horizontal(19).vertical(2).build())
                        .initialPosition(GameObject.Position.builder().horizontal(19).vertical(6).build())
                        .initialPosition(GameObject.Position.builder().horizontal(19).vertical(9).build())
                        .build()
        ));
        maze.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image(translate("images/Sprite 027 - Wall.png")))
                        .initialPosition(GameObject.Position.builder().horizontal(0).vertical(3).build())
                        .initialPosition(GameObject.Position.builder().horizontal(0).vertical(6).build())
                        .build()
        ));
        maze.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image(translate("images/Sprite 028 - Wall.png")))
                        .initialPosition(GameObject.Position.builder().horizontal(19).vertical(3).build())
                        .initialPosition(GameObject.Position.builder().horizontal(19).vertical(7).build())
                        .initialPosition(GameObject.Position.builder().horizontal(19).vertical(10).build())
                        .build()
        ));
        maze.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image(translate("images/Sprite 029 - Wall.png")))
                        .initialPosition(GameObject.Position.builder().horizontal(0).vertical(4).build())
                        .initialPosition(GameObject.Position.builder().horizontal(0).vertical(7).build())
                        .initialPosition(GameObject.Position.builder().horizontal(0).vertical(10).build())
                        .build()
        ));
        maze.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image(translate("images/Sprite 030 - Wall.png")))
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
                new Image(translate("images/Sprite 031 - Bunker Solid.png")),
                GameObject.Position.builder().horizontal(8).vertical(4).build()
        ));

        maze.addGameObject(0, new Bunker(
                new Image(translate("images/Sprite 032 - Bunker.png")),
                GameObject.Position.builder().horizontal(9).vertical(4).build()
        ));

        maze.addGameObject(0, new SolidBunker(
                new Image(translate("images/Sprite 033 - Bunker Solid.png")),
                GameObject.Position.builder().horizontal(10).vertical(4).build()
        ));

        maze.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image(translate("images/Sprite 034 - Wall.png")))
                        .initialPosition(GameObject.Position.builder().horizontal(14).vertical(4).build())
                        .build()
        ));
        maze.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image(translate("images/Sprite 035 - Wall.png")))
                        .initialPosition(GameObject.Position.builder().horizontal(19).vertical(4).build())
                        .initialPosition(GameObject.Position.builder().horizontal(16).vertical(9).build())
                        .build()
        ));
        maze.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image(translate("images/Sprite 036 - Wall.png")))
                        .initialPosition(GameObject.Position.builder().horizontal(0).vertical(5).build())
                        .initialPosition(GameObject.Position.builder().horizontal(0).vertical(8).build())
                        .build()
        ));

        maze.addGameObject(0, new SolidBunker(
                new Image(translate("images/Sprite 037 - Bunker Solid.png")),
                GameObject.Position.builder().horizontal(8).vertical(5).build()
        ));


        maze.addGameObject(0, new HalfSolidBunker(
                new Image(translate("images/Sprite 038 - Bunker Half-Solid.png")),
                GameObject.Position.builder().horizontal(9).vertical(5).build()
        ));

        maze.addGameObject(0, new SolidBunker(
                new Image(translate("images/Sprite 039 - Bunker Solid.png")),
                GameObject.Position.builder().horizontal(10).vertical(5).build()
        ));


        maze.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image(translate("images/Sprite 040 - Wall.png")))
                        .initialPosition(GameObject.Position.builder().horizontal(14).vertical(6).build())
                        .build()
        ));
        maze.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image(translate("images/Sprite 041 - Wall.png")))
                        .initialPosition(GameObject.Position.builder().horizontal(16).vertical(8).build())
                        .build()
        ));
        maze.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image(translate("images/Sprite 042 - Wall.png")))
                        .initialPosition(GameObject.Position.builder().horizontal(17).vertical(8).build())
                        .build()
        ));
        maze.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image(translate("images/Sprite 043 - Wall.png")))
                        .initialPosition(GameObject.Position.builder().horizontal(0).vertical(9).build())
                        .build()
        ));
        maze.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image(translate("images/Sprite 044 - Wall.png")))
                        .initialPosition(GameObject.Position.builder().horizontal(17).vertical(9).build())
                        .build()
        ));
        maze.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image(translate("images/Sprite 045 - Wall.png")))
                        .initialPosition(GameObject.Position.builder().horizontal(0).vertical(11).build())
                        .build()
        ));
        maze.addGameObjects(4, WallFactory.createWalls(
                WallFactory.WallFactoryConfiguration.builder()
                        .image(new Image(translate("images/Sprite 046 - Wall.png")))
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
                        .image(new Image(translate("images/Sprite 047 - Wall.png")))
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
                        .image(new Image(translate("images/Sprite 048 - Wall.png")))
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
