package org.moqucu.games.nightstalker.test;

import javafx.animation.Transition;
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

import java.util.concurrent.TimeUnit;

@ExtendWith(ApplicationExtension.class)
public class CurveAnimationTest {

    CubicCurve curve = new CubicCurve();
    Anchor start;
    Anchor end;
    Rectangle rectPath;

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
    public void start(Stage stage) {

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
    void testTransition(FxRobot robot) {

        Transition transition = new Transition() {

            {
                setCycleDuration(Duration.millis(2000));
            }

            @Override
            protected void interpolate(double frac) {
                Point2D start = new Point2D(curve.getStartX(), curve.getStartY());
                Point2D control1 = new Point2D(curve.getControlX1(), curve.getControlY1());
                Point2D control2 = new Point2D(curve.getControlX2(), curve.getControlY2());
                Point2D end = new Point2D(curve.getEndX(), curve.getEndY());

                Point2D center = bezier(frac, start, control1, control2, end);

                double width = rectPath.getBoundsInLocal().getWidth() ;
                double height = rectPath.getBoundsInLocal().getHeight() ;

                rectPath.setTranslateX(center.getX() - width /2);
                rectPath.setTranslateY(center.getY() - height / 2);

                Point2D tangent = bezierDeriv(frac, start, control1, control2, end);
                double angle = Math.toDegrees(Math.atan2(tangent.getY(), tangent.getX()));
                rectPath.setRotate(angle);
            }

        };

        transition.setCycleCount(1);
        transition.setAutoReverse(false);
        transition.play();

        robot.sleep(2, TimeUnit.SECONDS);
    }
}
