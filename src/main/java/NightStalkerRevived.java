
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Bat;
import model.Bunker;
import model.GameObject;
import model.GreyRobot;
import model.Gun;
import model.HalfSolidBunker;
import model.NightStalker;
import model.SolidBunker;
import model.Spider;
import model.SpiderWeb;
import model.World;
import utility.WallFactory;

public class NightStalkerRevived extends Application {

    private Group root;

    private Scene scene;

    private StackPane root;

    private Image splashScreen;

    private int gameScore = 0;
    
    private World world;

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("InvinciBagel");
        root = new Group();
        scene = new Scene(
                root,
                GameConstants.WIDTH,
                GameConstants.HEIGHT,
                Color.web(Integer.toHexString(GameConstants.COLOR_WHITE))
        );
        primaryStage.setScene(scene);
        primaryStage.show();

        createGameActors();
        addGameActorNodes();
        createCastingDirection();
        createSplashScreenNodes();
        addNodesToStackPane();
        createStartGameLoop();
    }

    private void createGameActors() {
        
        world = new World(GameConstants.WIDTH, GameConstants.HEIGHT);

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

        Bat bat1 = new Bat(17, 3, 5.0);
        world.addGameObject(2, bat1);

        Bat bat2 = new Bat(16, 7, 2.5);
        world.addGameObject(2, bat2);

        Spider spider = new Spider(1, 1);
        world.addGameObject(2, spider);

        GreyRobot robot = new GreyRobot(1, 10);
        world.addGameObject(2, robot);

        NightStalker nightStalker = new NightStalker();
        world.addGameObject(1, nightStalker);

        Gun gun = new Gun(3.0);
        world.addGameObject(3, gun);
    }

    private void createSplashScreenNodes() {
        scoreText = new Text();
        scoreText.setText(String.valueOf(gameScore));
        scoreText.setLayoutY(385);
        scoreText.setLayoutX(525);
        scoreFont = new Font("Verdana", 20);
        scoreText.setFont(scoreFont);
        scoreText.setFill(Color.RED);
        scoreLabel = new Text();
        scoreLabel.setText("SCORE:");
        scoreLabel.setLayoutY(385);
        scoreLabel.setLayoutX(445);
        scoreLabel.setFont(scoreFont);
        scoreLabel.setFill(Color.BLACK);
        buttonContainer = new HBox(12);
        buttonContainer.setLayoutY(365);
        buttonContainerPadding = new Insets(0, 0, 10, 16);
        buttonContainer.setPadding(buttonContainerPadding);
        gameButton = new Button();
        gameButton.setText("PLAY GAME");
        gameButton.setOnAction((ActionEvent) -> {
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
        splashScreenTextArea.setImage(instructionLayer);
    }

    private void addNodesToStackPane() {
        root.getChildren().add(splashScreenBackplate);
        root.getChildren().add(splashScreenTextArea);
        root.getChildren().add(buttonContainer);
        root.getChildren().add(scoreText);
        root.getChildren().add(scoreLabel);
    }

    public static void main(String[] args) {

        launch(args);
    }
}
