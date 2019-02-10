package org.moqucu.games.nightstalker;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.moqucu.games.nightstalker.objects.movable.Bat;
import org.moqucu.games.nightstalker.objects.immovable.Bunker;
import org.moqucu.games.nightstalker.objects.GameObject;
import org.moqucu.games.nightstalker.objects.movable.GreyRobot;
import org.moqucu.games.nightstalker.objects.immovable.Gun;
import org.moqucu.games.nightstalker.objects.immovable.HalfSolidBunker;
import org.moqucu.games.nightstalker.objects.movable.NightStalker;
import org.moqucu.games.nightstalker.objects.immovable.SolidBunker;
import org.moqucu.games.nightstalker.objects.movable.Spider;
import org.moqucu.games.nightstalker.objects.immovable.SpiderWeb;
import org.moqucu.games.nightstalker.utility.BackGroundMusicLoop;
import org.moqucu.games.nightstalker.utility.WallFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.moqucu.games.nightstalker.utility.GameConstants.*;

public class NightStalkerRevived extends Application {

    private Group splashScreenRootNode;

    private Stage primaryStage;

    private Maze maze;

    private Font buttonFont = Font.loadFont(translate("fonts/intellect.ttf"), 12);

    private Task backGroundMusicLoop;

    public static String translate(String relativePath) {

        return NightStalkerRevived.class.getResource(relativePath).toExternalForm();
    }

    @Override
    public void init() {

        splashScreenRootNode = createSplashScreenRootNode();
        maze = createMazeAndAddSprites();
    }

    private String fxBorderColor(int color) {

        return "-fx-border-color: #" + String.format("%06x", color) + "; ";
    }

    private String fxBackgroundColor(int color) {

        return "-fx-background-color: #" + String.format("%06x", color) + "; ";
    }

    private String fxBorderWidth(int width) {

        return "-fx-border-width: " + width + "px; ";
    }

    private String fxTextFill(int color) {

        return "-fx-text-fill: #" + String.format("%06x", color) + "; ";
    }

    @Override
    public void start(Stage primaryStage) {

        backGroundMusicLoop = new BackGroundMusicLoop();
        ExecutorService service = Executors.newFixedThreadPool(4);
        service.execute(backGroundMusicLoop);

        /*;
        service.execute(() -> {

            audio.setVolume(0.5f);
            audio.setCycleCount(INDEFINITE);
            audio.play();
        });*/

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Night Stalker Revived");
        Scene scene = new Scene(
                splashScreenRootNode,
                WIDTH,
                HEIGHT + 32,
                Color.web(String.format("%06x", COLOR_BROWN))
        );
        this.primaryStage.setScene(scene);
        this.primaryStage.show();
    }

    private Maze createMazeAndAddSprites() {

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
    }

    private void createStartGameLoop() {

        GameLoop gameLoop = new GameLoop(primaryStage, maze);

        primaryStage.setHeight(HEIGHT + TITLE_BAR_HEIGHT);
        primaryStage.setWidth(WIDTH);

        gameLoop.start();
    }

    private Button createQuitButton() {

        Button quitButton = new Button();
        quitButton.setFont(buttonFont);
        quitButton.setStyle(
                fxBorderColor(COLOR_WHITE)
                        + fxBorderWidth(2)
                        + fxBackgroundColor(COLOR_BROWN)
                        + fxTextFill(COLOR_WHITE)
        );
        quitButton.setText("Quit");
        quitButton.setOnAction((ActionEvent) -> System.exit(0));

        return quitButton;
    }

    private Button createPlayButton() {

        Button playButton = new Button();
        playButton.setFont(buttonFont);
        playButton.setStyle(
                fxBorderColor(COLOR_WHITE)
                        + fxBorderWidth(2)
                        + fxBackgroundColor(COLOR_BROWN)
                        + fxTextFill(COLOR_WHITE)
        );
        playButton.setText("Play");
        playButton.setOnAction((ActionEvent) -> backGroundMusicLoop.cancel());

        return playButton;
    }

    private Node[] createNodeArrayOfAllButtons() {

        List<Node> nodes = new ArrayList<>();
        nodes.add(createQuitButton());
        nodes.add(new Rectangle(20, 28, Paint.valueOf(String.format("%06x", COLOR_BROWN))));
        nodes.add(createPlayButton());

        return nodes.toArray(new Node[]{});
    }

    private Group createSplashScreenRootNode() {

        ImageView splashScreenImage
                = new ImageView(translate("images/Night Stalker Revived Slash Screen.png"));
        HBox buttonContainer = new HBox(createNodeArrayOfAllButtons());
        Insets buttonContainerPadding = new Insets(2, 2, 2, 2);
        buttonContainer.setPadding(buttonContainerPadding);

        VBox splashScreenContainer = new VBox();
        splashScreenContainer.getChildren().add(buttonContainer);
        splashScreenContainer.getChildren().add(splashScreenImage);

       return new Group(splashScreenContainer);

        /*splashScreenRootNode.getChildren().add(splashScreenTextArea);

        splashScreenRootNode.getChildren().add(scoreText);
        splashScreenRootNode.getChildren().add(scoreLabel);*/


        /*Text scoreText = new Text();
        int gameScore = 0;
        scoreText.setText(String.valueOf(gameScore));
        scoreText.setLayoutY(385);
        scoreText.setLayoutX(525);

        scoreText.setFont(buttonFont);
        scoreText.setFill(Color.RED);
        Text scoreLabel = new Text();
        scoreLabel.setText("SCORE:");
        scoreLabel.setLayoutY(385);
        scoreLabel.setLayoutX(445);
        scoreLabel.setFont(buttonFont);
        scoreLabel.setFill(Color.BLACK);

        Button gameButton = new Button();
        gameButton.setFont(buttonFont);
        gameButton.setText("PLAY GAME");*/
        /*gameButton.setOnAction((ActionEvent) -> {
            splashScreenBackplate.setImage(skyCloud);
            splashScreenBackplate.setVisible(true);
            splashScreenBackplate.toBack();
            splashScreenTextArea.setVisible(false);
        });
        helpButton = new Button();
        helpButton.setText("INSTRUCTIONS");
        helpButton.setOnAction((ActionEvent) -> {
            splashScreenBackplate.setImage(splashScreen);
            splashScreenBackplate.toFront();
            splashScreenBackplate.setVisible(true);
            splashScreenTextArea.setVisible(true);
            splashScreenTextArea.setImage(instructionLayer);
            splashScreenTextArea.toFront();
            buttonContainer.toFront();
        });
        scoreButton = new Button();
        scoreButton.setText("HIGH SCORES");
        scoreButton.setOnAction((ActionEvent) -> {
            splashScreenBackplate.setImage(splashScreen);
            splashScreenBackplate.toFront();
            splashScreenBackplate.setVisible(true);
            splashScreenTextArea.setVisible(true);
            splashScreenTextArea.setImage(scoresLayer);
            splashScreenTextArea.toFront();
            buttonContainer.toFront();
        });
        legalButton = new Button();
        legalButton.setText("LEGAL & CREDITS");
        legalButton.setOnAction((ActionEvent) -> {
            splashScreenBackplate.setImage(splashScreen);
            splashScreenBackplate.toFront();
            splashScreenBackplate.setVisible(true);
            splashScreenTextArea.setVisible(true);
            splashScreenTextArea.setImage(legalLayer);
            splashScreenTextArea.toFront();
            buttonContainer.toFront();
        });
        buttonContainer.getChildren().addAll(gameButton, helpButton, scoreButton, legalButton);
        splashScreenBackplate = new ImageView();
        splashScreenBackplate.setImage(splashScreen);
        splashScreenTextArea = new ImageView();
        splashScreenTextArea.setImage(instructionLayer);*/

    }

    public static void main(String[] args) {

        launch(args);
    }
}
