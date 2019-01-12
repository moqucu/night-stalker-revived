package model;

import javafx.geometry.Rectangle2D;

import java.util.List;
import java.util.ArrayList;

public class QuadTree {

    // https://gamedevelopment.tutsplus.com/tutorials/quick-tip-use-quadtrees-to-detect-likely-collisions-in-2d-space--gamedev-374

    private int level;
    private List<Rectangle2D> objects;
    private Rectangle2D bounds;
    private QuadTree[] nodes;

    public QuadTree(int pLevel, Rectangle2D pBounds) {
        level = pLevel;
        objects = new ArrayList();
        bounds = pBounds;
        nodes = new QuadTree[4];
    }

    // Clears all objects from tree
    public void clear() {
        objects.clear();

        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i] != null) {
                nodes[i].clear();
                nodes[i] = null;
            }
        }
    }


    // Splits the node into 4 subnodes
    private void split() {
        int subWidth = (int) (bounds.getWidth() / 2);
        int subHeight = (int) (bounds.getHeight() / 2);
        int x = (int) bounds.getMinX();
        int y = (int) bounds.getMinY();

        nodes[0] = new QuadTree(level + 1, new Rectangle2D(x + subWidth, y, subWidth, subHeight));
        nodes[1] = new QuadTree(level + 1, new Rectangle2D(x, y, subWidth, subHeight));
        nodes[2] = new QuadTree(level + 1, new Rectangle2D(x, y + subHeight, subWidth, subHeight));
        nodes[3] = new QuadTree(level + 1, new Rectangle2D(x + subWidth, y + subHeight, subWidth, subHeight));
    }

    // Determine which node the object belongs to
    // Returns -1 if object doesn't fit in node
    private int getIndex(Rectangle2D pRect) {
        int index = -1;
        double verticalMidpoint = bounds.getMinX() + (bounds.getWidth() / 2);
        double horizontalMidpoint = bounds.getMinY() + (bounds.getHeight() / 2);

        // Object can completely fit within the top quadrants
        boolean topQuadrant = (pRect.getMinY() < horizontalMidpoint && pRect.getMinY() + pRect.getHeight() < horizontalMidpoint);
        // Object can completely fit within the bottom quadrants
        boolean bottomQuadrant = (pRect.getMinY() > horizontalMidpoint);

        // Object can completely fit within the left quadrants
        if (pRect.getMinX() < verticalMidpoint && pRect.getMinX() + pRect.getWidth() < verticalMidpoint) {
            if (topQuadrant) {
                index = 1;
            }
            else if (bottomQuadrant) {
                index = 2;
            }
        }
        // Object can completely fit within the right quadrants
        else if (pRect.getMinX() > verticalMidpoint) {
            if (topQuadrant) {
                index = 0;
            }
            else if (bottomQuadrant) {
                index = 3;
            }
        }

        return index;
    }

    // Add object to QuadTree
    public void insert(Rectangle2D pRect) {

        int MAX_OBJECTS = 10;
        int MAX_LEVELS = 5;

        if (nodes[0] != null) {
            int index = getIndex(pRect);

            if (index != -1) {
                nodes[index].insert(pRect);

                return;
            }
        }

        objects.add(pRect);

        if (objects.size() > MAX_OBJECTS && level < MAX_LEVELS) {
            if (nodes[0] == null) {
                split();
            }

            int i = 0;
            while (i < objects.size()) {
                int index = getIndex(objects.get(i));
                if (index != -1) {
                    nodes[index].insert(objects.remove(i));
                }
                else {
                    i++;
                }
            }
        }
    }

    // Return all objects that could collide with the given object
    public List retrieve(List returnObjects, Rectangle2D pRect) {
        int index = getIndex(pRect);
        if (index != -1 && nodes[0] != null) {
            nodes[index].retrieve(returnObjects, pRect);
        }

        returnObjects.addAll(objects);

        return returnObjects;
    }
}
