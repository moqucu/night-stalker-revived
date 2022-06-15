package org.moqucu.games.nightstalker.test;

import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

@ExtendWith(ApplicationExtension.class)
public class CurveAnimationTest {

    CubicCurve curve = new CubicCurve();
    Anchor start;
    Anchor end;
    Rectangle rectPath;
    PathTransition pathTransition;

    private Point2D bezier(double t, Point2D... points) {
        if (points.length == 2) {
            return points[0].multiply(1-t).add(points[1].multiply(t));
        }
        Point2D[] leftArray = new Point2D[points.length - 1];
        System.arraycopy(points, 0, leftArray, 0, points.length - 1);
        Point2D[] rightArray = new Point2D[points.length - 1];
        System.arraycopy(points, 1, rightArray, 0, points.length - 1);
        return bezier(t, leftArray).multiply(1-t).add(bezier(t, rightArray).multiply(t));
    }

    private Point2D bezierDeriv(double t, Point2D... points) {
        if (points.length == 2) {
            return points[1].subtract(points[0]);
        }
        Point2D[] leftArray = new Point2D[points.length - 1];
        System.arraycopy(points, 0, leftArray, 0, points.length - 1);
        Point2D[] rightArray = new Point2D[points.length - 1];
        System.arraycopy(points, 1, rightArray, 0, points.length - 1);
        return bezier(t, leftArray).multiply(-1).add(bezierDeriv(t, leftArray).multiply(1-t))
                .add(bezier(t, rightArray)).add(bezierDeriv(t, rightArray).multiply(t));
    }

    /**
     * Will be called with {@code @Before} semantics, i. e. before each test method.
     *
     * @param stage - Will be injected by the test runner.
     */
    @Start
    private void start(Stage stage) {

        curve.setStartX(100);
        curve.setStartY(200);
        curve.setControlX1(150);
        curve.setControlY1(0);
        curve.setControlX2(200);
        curve.setControlY2(50);
        curve.setEndX(250);
        curve.setEndY(400);
        curve.setStroke(Color.TRANSPARENT);
        curve.setStrokeWidth(1);
        curve.setFill(Color.TRANSPARENT);

        start    = new Anchor(Color.PALEGREEN, curve.startXProperty(),    curve.startYProperty());
        end      = new Anchor(Color.TOMATO,    curve.endXProperty(),      curve.endYProperty());

        rectPath = new Rectangle (0, 0, 40, 40);
        rectPath.setArcHeight(25);
        rectPath.setArcWidth(25);
        rectPath.setFill(Color.ORANGE);


        Group root = new Group();
        root.getChildren().addAll(curve, start, end, rectPath);

        stage.setScene(new Scene( root, 400, 400, Color.ALICEBLUE));
        stage.show();
    }

    /**
     * @param robot - Will be injected by the test runner.
     */
    @Test
    void testTransition(FxRobot robot) throws InterruptedException {
        pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(2000));
        pathTransition.setPath(curve);
        pathTransition.setNode(rectPath);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.setCycleCount(Timeline.INDEFINITE);
        pathTransition.setAutoReverse(true);
        pathTransition.play();

        Thread.sleep(10000);
    }

}
