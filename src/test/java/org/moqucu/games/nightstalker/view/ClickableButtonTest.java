package org.moqucu.games.nightstalker.view;

import javafx.animation.RotateTransition;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.LabeledMatchers;

@SuppressWarnings("ALL")
@ExtendWith(ApplicationExtension.class)
class ClickableButtonTest {

    private Button button;
    private Image image;
    private ImageView imageView;

    /**
     * Will be called with {@code @Before} semantics, i. e. before each test method.
     *
     * @param stage - Will be injected by the test runner.
     */
    @Start
    private void start(Stage stage) {
        button = new Button("click me!");
        button.setId("myButton");
        button.setOnAction(
                actionEvent -> {
                    button.setText("clicked!");
                    RotateTransition rotateTransition = new RotateTransition();
                    rotateTransition.setDuration(Duration.millis(1000));
                    rotateTransition.setNode(imageView);
                    rotateTransition.setByAngle(360);
                    rotateTransition.setCycleCount(50);
                    rotateTransition.setAutoReverse(false);
                    rotateTransition.play();

                }
        );
        Pane pane = new Pane();
        pane.setStyle("-fx-background-color: black;");
        image = new Image("part_1_a.gif");
        pane.getChildren().add(button);
        button.relocate(20, 20);
        imageView = new ImageView(image);
        pane.getChildren().add(imageView);
        imageView.relocate(100, 100);

        stage.setScene(new Scene(pane, 800, 600));
        stage.show();
    }

    /**
     * @param robot - Will be injected by the test runner.
     */
    @Test
    void should_contain_button_with_text(FxRobot robot) {
        FxAssert.verifyThat(button, LabeledMatchers.hasText("click me!"));
        // or (lookup by css id):
        FxAssert.verifyThat("#myButton", LabeledMatchers.hasText("click me!"));
        // or (lookup by css class):
        FxAssert.verifyThat(".button", LabeledMatchers.hasText("click me!"));
    }

    /**
     * @param robot - Will be injected by the test runner.
     */
    @Test
    @SneakyThrows
    void when_button_is_clicked_text_changes(FxRobot robot) {
        // when:
        robot.clickOn(".button");

        // then:
        FxAssert.verifyThat(button, LabeledMatchers.hasText("clicked!"));
        // or (lookup by css id):
        FxAssert.verifyThat("#myButton", LabeledMatchers.hasText("clicked!"));
        // or (lookup by css class):
        FxAssert.verifyThat(".button", LabeledMatchers.hasText("clicked!"));

        Thread.sleep(2000);
    }
}
