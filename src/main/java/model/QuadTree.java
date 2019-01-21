package model;

import javafx.geometry.Rectangle2D;
import lombok.Getter;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * https://gamedevelopment.tutsplus.com/tutorials/quick-tip-use-quadtrees-to-detect-likely-collisions-in-2d-space--gamedev-374
 */
public class QuadTree {

    private static int TOP_RIGHT_NODE = 0;

    private static int TOP_LEFT_NODE = 1;

    private static int BOTTOM_LEFT_NODE = 2;

    private static int BOTTOM_RIGHT_NODE = 3;

    /**
     * Defines how many objects a node can hold before it splits.
     */
    private static final int MAX_OBJECTS = 20;

    /**
     * Defines the deepest level sub node.
     */
    private static final int MAX_LEVELS = 7;

    /**
     * Represents the current node level (0 being the topmost node).
     */
    private final int level;

    /**
     * Does this node have sub notes?
     */
    private AtomicBoolean hasSubNodes = new AtomicBoolean(false);

    /**
     * Represents the 2D space that the node occupies.
     */
    @Getter
    private final Rectangle2D boundary;

    /**
     * Represents the four sub nodes.
     */
    private final QuadTree[] nodes = new QuadTree[4];

    /**
     * Objects in node
     */
    private CopyOnWriteArrayList<Sprite> sprites = new CopyOnWriteArrayList<>();

    /**
     * Create root node.
     **/
    QuadTree(Rectangle2D quadTreeBoundaries) {

        level = 0;
        this.boundary = quadTreeBoundaries;
    }

    private QuadTree(int level, Rectangle2D quadTreeBoundary) {

        this.level = level;
        this.boundary = quadTreeBoundary;
    }

    /**
     * The clear method clears the quad tree by recursively clearing all objects from all nodes.
     */
    public void clear() {

        sprites.clear();

        for (int i = 0; i < nodes.length; i++) {

            if (nodes[i] != null) {

                nodes[i].clear();
                nodes[i] = null;
            }
        }

        hasSubNodes.set(false);
    }

    /**
     * Split the node into four sub nodes by
     * dividing the node into four equal parts
     * and initializing the four sub nodes with the new boundary.
     */
    private void split() {

        int halfWidth = (int) (boundary.getWidth() / 2);
        int halfHeight = (int) (boundary.getHeight() / 2);

        int leftBoundary = (int) boundary.getMinX();
        int topBoundary = (int) boundary.getMinY();

        int nextLevel = level + 1;

        nodes[TOP_RIGHT_NODE] = new QuadTree(
                nextLevel,
                new Rectangle2D(leftBoundary + halfWidth, topBoundary, halfWidth, halfHeight)
        );
        nodes[TOP_LEFT_NODE] = new QuadTree(
                nextLevel,
                new Rectangle2D(leftBoundary, topBoundary, halfWidth, halfHeight)
        );
        nodes[BOTTOM_LEFT_NODE] = new QuadTree(
                nextLevel,
                new Rectangle2D(leftBoundary, topBoundary + halfHeight, halfWidth, halfHeight)
        );
        nodes[BOTTOM_RIGHT_NODE] = new QuadTree(
                nextLevel,
                new Rectangle2D(leftBoundary + halfWidth, topBoundary + halfHeight, halfWidth, halfHeight)
        );

        hasSubNodes.set(true);
    }

    /**
     * Determine which node the object belongs to. -1 means
     * object cannot completely fit within a child node and is part
     * of the parent node
     *
     * @param sprite Sprite whose node is to be determined.
     * @return 0 = top-right node, 1 = top-left node, 2 = bottom-left node, 3 = bottom-right node,
     * -1 = parent node.
     */
    private int getIndex(Sprite sprite) {

        if (!hasSubNodes.get())
            return -1;

        if (nodes[TOP_RIGHT_NODE].getBoundary().contains(sprite.getBoundary()))
            return TOP_RIGHT_NODE;
        else if (nodes[TOP_LEFT_NODE].getBoundary().contains(sprite.getBoundary()))
            return TOP_LEFT_NODE;
        else if (nodes[BOTTOM_LEFT_NODE].getBoundary().contains(sprite.getBoundary()))
            return BOTTOM_LEFT_NODE;
        else if (nodes[BOTTOM_RIGHT_NODE].getBoundary().contains(sprite.getBoundary()))
            return BOTTOM_RIGHT_NODE;
        else
            return -1;
    }

    private boolean wasInsertedIntoSubNode(Sprite sprite) {

        int index = getIndex(sprite);

        if (index != -1) {
            nodes[index].insert(sprite);
            return true;
        }
        else
            return false;
    }

    /**
     * The insert method is where everything comes together.
     * The method first determines whether the node has any child nodes and tries to add the object there.
     * If there are no child nodes or the object doesn’t fit in a child node, it adds the object to the parent node.
     * Once the object is added, it determines whether the node needs to split by checking
     * if the current number of objects exceeds the max allowed objects.
     * Splitting will cause the node to insert any object that can fit in a child node
     * to be added to the child node; otherwise the object will stay in the parent node.
     *
     * @param sprite The game object that shall be inserted into the quad tree.
     */
    public void insert(Sprite sprite) {

        /* Can only insert new objects into right boundary */
        if (!boundary.contains(sprite.getBoundary()))
            return;

        /* If this node has sub nodes and object can be inserted there, this function is done */
        if (hasSubNodes.get() && wasInsertedIntoSubNode(sprite))
            return;

        /* Insert the game object in the list */
        sprites.add(sprite);

        /* if capacity of list is reached, split and move all objects into sub nodes */
        if (sprites.size() > MAX_OBJECTS) {

            if (level == MAX_LEVELS)
                throw new RuntimeException("Quad tree has exceeded max_objects and max_levels limit");

            split();

            sprites.forEach(listedObject -> {

                if (wasInsertedIntoSubNode(sprite))
                    sprites.remove(listedObject);


            });
        }
    }

    /**
     * Return all sprites that are in relative close proximity to the given object.
     *
     * @param sprite Sprite that shall be used for the proximity search.
     * @return All nearby game sprites.
     */
    List<Sprite> findNearbyGameObjects(Sprite sprite) {

        int index = getIndex(sprite);

        if (index != -1 && hasSubNodes.get())
            return nodes[index].findNearbyGameObjects(sprite);
        else
            return sprites;
    }
}
