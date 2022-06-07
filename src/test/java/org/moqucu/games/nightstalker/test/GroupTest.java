package org.moqucu.games.nightstalker.test;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;

@SuppressWarnings("ALL")
@ExtendWith(ApplicationExtension.class)
public class GroupTest {

    @Test
    void members_of_group_refer_to_the_same_parent(FxRobot robot) {

        final Group group = new Group();
        final ImageView imageView1 = new ImageView(new Image("part_1_a.gif"));
        final ImageView imageView2 = new ImageView(new Image("part_1_b.gif"));
        final ImageView imageView3 = new ImageView(new Image("part_2_a.gif"));
        final ImageView imageView4 = new ImageView(new Image("part_2_b.gif"));
        group.getChildren().add(imageView1);
        group.getChildren().add(imageView2);
        group.getChildren().add(imageView3);
        group.getChildren().add(imageView4);
        Assertions.assertEquals(group, imageView1.getParent());
        Assertions.assertEquals(group, imageView2.getParent());
        Assertions.assertEquals(group, imageView3.getParent());
        Assertions.assertEquals(group, imageView4.getParent());
    }
}
