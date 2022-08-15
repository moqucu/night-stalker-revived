package org.moqucu.games.nightstalker.view;

import javafx.scene.Group;
import javafx.scene.shape.CubicCurve;
import lombok.extern.log4j.Log4j2;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import org.moqucu.games.nightstalker.label.LivesLabel;
import org.moqucu.games.nightstalker.label.ScoreLabel;
import org.moqucu.games.nightstalker.sprite.enemy.GreyRobot;
import org.moqucu.games.nightstalker.sprite.enemy.Spider;
import org.moqucu.games.nightstalker.sprite.hero.NightStalker;
import org.moqucu.games.nightstalker.sprite.object.GreyRobotPartOne;
import org.moqucu.games.nightstalker.sprite.object.GreyRobotPartTwo;
import org.moqucu.games.nightstalker.utility.SpriteCreationListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Log4j2
public class StageManager implements SpriteCreationListener {

    private final Stage primaryStage;
    private final SpringFXMLLoader springFXMLLoader;
    private final Map<Parent, Scene> scenes = new HashMap<>();

    public StageManager(SpringFXMLLoader springFXMLLoader, Stage primaryStage) {

        this.springFXMLLoader = springFXMLLoader;
        this.primaryStage = primaryStage;
        this.registerSpriteClass(Group.class);
        this.registerSpriteClass(NightStalker.class);
        this.registerSpriteClass(Spider.class);
        this.registerSpriteClass(GreyRobot.class);
        this.registerSpriteClass(GreyRobotPartOne.class);
        this.registerSpriteClass(GreyRobotPartTwo.class);
        this.registerSpriteClass(CubicCurve.class);

        this.registerSpriteClass(LivesLabel.class);
        this.registerSpriteClass(ScoreLabel.class);

    }

    public Parent switchScene(final FxmlView view) {

        Parent viewRootNodeHierarchy = loadViewNodeHierarchy(view.getFxmlFile());
        log.debug(viewRootNodeHierarchy.getId());

        if (view.equals(FxmlView.GAME_SCREEN)) {

            // ToDo: unclear why I am accessing these sprites? Maybe, trying to make connections?

            NightStalker nightStalker = (NightStalker) createdSprites.get(NightStalker.class);
            LivesLabel livesLabel = (LivesLabel) createdSprites.get(LivesLabel.class);
            livesLabel.bindLivesProperty(nightStalker.livesProperty());
            nightStalker.livesProperty().addListener(
                    (observable, oldValue, newValue)
                            -> log.info("Observed lives change: {} -> {}", oldValue, newValue));

            Spider spider = (Spider) createdSprites.get(Spider.class);
            ScoreLabel scoreLabel = (ScoreLabel) createdSprites.get(ScoreLabel.class);
            spider.addHitListener(scoreLabel);
            GreyRobot greyRobot = (GreyRobot) createdSprites.get(GreyRobot.class);
            Group animationGroup = (Group) createdSprites.get(Group.class);
            CubicCurve cubicCurve = (CubicCurve) createdSprites.get(CubicCurve.class);
//            greyRobot.setPartsAnimationGroup(animationGroup);
//            greyRobot.setCurve(cubicCurve);
            GreyRobotPartOne greyRobotPartOne = (GreyRobotPartOne) createdSprites.get(GreyRobotPartOne.class);
            GreyRobotPartTwo greyRobotPartTwo = (GreyRobotPartTwo) createdSprites.get(GreyRobotPartTwo.class);
//            greyRobot.setPartOne(greyRobotPartOne);
//            greyRobot.setPartTwo(greyRobotPartTwo);
            greyRobot.translateXProperty().addListener(
                    (observable, oldValue, newValue) -> {
                        greyRobotPartOne.setTranslateX((double) newValue);
                        greyRobotPartTwo.setTranslateX((double) newValue);
                    }
            );
            greyRobot.translateYProperty().addListener(
                    (observable, oldValue, newValue) -> {
                        greyRobotPartOne.setTranslateY((double) newValue);
                        greyRobotPartTwo.setTranslateY((double) newValue);
                    }
            );
        }

        show(viewRootNodeHierarchy, view.getTitle());

        return viewRootNodeHierarchy;
    }

    private void show(Parent rootNode, String title) {

        primaryStage.setTitle(title);
        primaryStage.setScene(prepareScene(rootNode));
        primaryStage.sizeToScene();
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    private Scene prepareScene(Parent rootNode) {

        if (!scenes.containsKey(rootNode)) {

            Scene scene = new Scene(rootNode);
            scenes.put(rootNode, scene);
        }

        return scenes.get(rootNode);
    }

    /**
     * Loads the object hierarchy from a FXML document and returns to root node
     * of that hierarchy.
     *
     * @return Parent root node of the FXML document hierarchy
     */
    @SneakyThrows
    private Parent loadViewNodeHierarchy(String fxmlFilePath) {

        return Optional.of(springFXMLLoader.load(this, fxmlFilePath)).get();
    }
}
