package org.moqucu.games.nightstalker.view;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.TilePane;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class MazeBackgroundTilePane extends TilePane {

    private ObservableList<Node> children;

    private int index = 0;

    private final ListChangeListener<Node> childrenChangeListener = change -> {

        if (change.next() && change.getAddedSize() > 0)
            change.getAddedSubList().stream().filter(node -> node instanceof DisplayableSprite).forEach(
                    sprite -> {
                        final double x = index % 20 * 32.0;
                        //noinspection IntegerDivisionInFloatingPointContext
                        final double y = index / 20 * 32.0;
                        index++;
                        ((DisplayableSprite) sprite).setX(x);
                        ((DisplayableSprite) sprite).setY(y);
                    }
            );
    };

    @Override
    public ObservableList<Node> getChildren() {

        final ObservableList<Node> children = super.getChildren();

        if (this.children == null || this.children != children) {
            this.children = children;
            this.children.addListener(childrenChangeListener);
        }

        return children;
    }
}
