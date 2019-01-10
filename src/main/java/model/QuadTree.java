package model;

import javafx.geometry.Rectangle2D;

import java.awt.Rectangle;
import java.util.List;
import java.util.ArrayList;

public class QuadTree {

    // https://gamedevelopment.tutsplus.com/tutorials/quick-tip-use-quadtrees-to-detect-likely-collisions-in-2d-space--gamedev-374

    private int level;
    private List<Rectangle> objects;
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
        int subWidth = (int)(bounds.getWidth() / 2);
        int subHeight = (int)(bounds.getHeight() / 2);
// Todo        int x = (int)bounds.getX();
// Todo       int y = (int)bounds.getY();

//        Todo nodes[0] = new QuadTree(level+1, new Rectangle(x + subWidth, y, subWidth, subHeight));
//        Todo nodes[1] = new QuadTree(level+1, new Rectangle( x, y, subWidth, subHeight));
//        Todo nodes[2] = new QuadTree(level+1, new Rectangle( x, y + subHeight, subWidth, subHeight));
//        todo nodes[3] = new QuadTree(level+1, new Rectangle(x + subWidth, y + subHeight, subWidth, subHeight));
    }

    // Determine which node the object belongs to
    // Returns -1 if object doesn't fit in node
    private int getIndex(Rectangle pRect) {
        int index = -1;
//        todo double verticalMidpoint = bounds.getX() + (bounds.getWidth() / 2);
//        todo double horizontalMidpoint = bounds.getY() + (bounds.getHeight() / 2);

        // Object can completely fit within the top quadrants
//        todo        boolean topQuadrant = (pRect.getY() < horizontalMidpoint && pRect.getY() + pRect.getHeight() < horizontalMidpoint);
        // Object can completely fit within the bottom quadrants
        //        todo  boolean bottomQuadrant = (pRect.getY() > horizontalMidpoint);

        // Object can completely fit within the left quadrants
       /* todo if (pRect.getX() < verticalMidpoint && pRect.getX() + pRect.getWidth() < verticalMidpoint) {
            if (topQuadrant) {
                index = 1;
            }
            else if (bottomQuadrant) {
                index = 2;
            }
        }
        // Object can completely fit within the right quadrants
        else if (pRect.getX() > verticalMidpoint) {
            if (topQuadrant) {
                index = 0;
            }
            else if (bottomQuadrant) {
                index = 3;
            }
        }*/

        return index;
    }

    // Add object to QuadTree todo
    /*public void insert(Rectangle2D pRect) {

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
    }*/

    // Return all objects that could collide with the given object
    //todo
    /*public List retrieve(List returnObjects, Rectangle2D pRect) {
        int index = getIndex(pRect);
        if (index != -1 && nodes[0] != null) {
            nodes[index].retrieve(returnObjects, pRect);
        }

        returnObjects.addAll(objects);

        return returnObjects;
    }*/
}
