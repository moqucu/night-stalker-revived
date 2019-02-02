package model;

import javafx.geometry.Rectangle2D;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * https://gamedevelopment.tutsplus.com/tutorials/quick-tip-use-quadtrees-to-detect-likely-collisions-in-2d-space--gamedev-374
 */
class QuadTree {

    private static byte TOP_RIGHT_NODE = 0b00000001;

    private static byte TOP_LEFT_NODE = 0b00000010;

    private static byte BOTTOM_LEFT_NODE = 0b00000100;

    private static byte BOTTOM_RIGHT_NODE = 0b00001000;

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
    private final ConcurrentMap<Byte, QuadTree> nodes = new ConcurrentHashMap<>(4);

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
    void clear() {

        sprites.clear();
        nodes.clear();
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

        nodes.put(
                TOP_RIGHT_NODE,
                new QuadTree(
                        nextLevel,
                        new Rectangle2D(leftBoundary + halfWidth, topBoundary, halfWidth, halfHeight)
                )
        );

        nodes.put(
                TOP_LEFT_NODE,
                new QuadTree(
                        nextLevel,
                        new Rectangle2D(leftBoundary, topBoundary, halfWidth, halfHeight)
                )
        );

        nodes.put(
                BOTTOM_LEFT_NODE,
                new QuadTree(
                        nextLevel,
                        new Rectangle2D(leftBoundary, topBoundary + halfHeight, halfWidth, halfHeight)
                )
        );

        nodes.put(
                BOTTOM_RIGHT_NODE,
                new QuadTree(
                        nextLevel,
                        new Rectangle2D(leftBoundary + halfWidth, topBoundary + halfHeight, halfWidth, halfHeight)
                )
        );

        hasSubNodes.set(true);
    }

    /**
     * Determine which node the object belongs to.
     *
     * @param sprite Sprite whose node is to be determined.
     * @return byte with lower 4 bits representing which index to pull.
     */
    private byte getIndex(Sprite sprite) {

        /* if none of this node's boundary intersects with the sprite's boundary, return 0 for all bits */
        byte intersectionFlags = 0b00000000;

        intersectionFlags = setIntersectionFlags(sprite, intersectionFlags, TOP_RIGHT_NODE);
        intersectionFlags = setIntersectionFlags(sprite, intersectionFlags, TOP_LEFT_NODE);
        intersectionFlags = setIntersectionFlags(sprite, intersectionFlags, BOTTOM_LEFT_NODE);
        intersectionFlags = setIntersectionFlags(sprite, intersectionFlags, BOTTOM_RIGHT_NODE);

        return intersectionFlags;
    }

    /**
     * Checks if sprite intersects with node @ bitMask and sets the respective flag.
     *
     * @param sprite            Sprite to be analyzed for intersecting with the specified node.
     * @param intersectionFlags Parameter to be modified with OR operator in case sprite intersects with boundary.
     * @param bitMask           The index / flag that needs to be determined.
     * @return Potentially modified parameter based on intersection analysis.
     */
    private byte setIntersectionFlags(Sprite sprite, byte intersectionFlags, byte bitMask) {

        if (nodes.get(bitMask) != null
                && nodes.get(bitMask).getBoundary().intersects(sprite.getBoundary()))
            intersectionFlags |= bitMask;

        return intersectionFlags;
    }

    private boolean wasInsertedIntoSubNode(Sprite sprite) {

        byte index = getIndex(sprite);

        if (index > 0) {
            nodes.get(index).insert(sprite);
            return true;
        }

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
    void insert(Sprite sprite) {

        /* Can only insert new objects into right boundary */
        if (!boundary.contains(sprite.getBoundary()))
            throw new RuntimeException("Sprite is (partially) out of bounds with regards to this node!");

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

                if (wasInsertedIntoSubNode(listedObject))
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
    @SuppressWarnings("Duplicates")
    List<Sprite> findNearbyGameObjects(Sprite sprite) {

        List<Sprite> nearbySprites = new ArrayList<>(sprites);

        byte index = getIndex(sprite);

        if ((index & TOP_RIGHT_NODE) == TOP_RIGHT_NODE)
            nearbySprites.addAll(nodes.get(TOP_RIGHT_NODE).findNearbyGameObjects(sprite));

        if ((index & TOP_LEFT_NODE) == TOP_LEFT_NODE)
            nearbySprites.addAll(nodes.get(TOP_LEFT_NODE).findNearbyGameObjects(sprite));

        if ((index & BOTTOM_LEFT_NODE) == BOTTOM_LEFT_NODE)
            nearbySprites.addAll(nodes.get(BOTTOM_LEFT_NODE).findNearbyGameObjects(sprite));

        if ((index & BOTTOM_RIGHT_NODE) == BOTTOM_RIGHT_NODE)
            nearbySprites.addAll(nodes.get(BOTTOM_RIGHT_NODE).findNearbyGameObjects(sprite));

        return nearbySprites;
    }

    List<Sprite> findNearbyGameObjects(long x, long y) {

        Wall wall = new Wall(null, GameObject.Position.builder().horizontal(0).vertical(0).build());
        wall.setCurrentCoordinates(Sprite.Coordinates.builder().x(x).y(y).build());

        return findNearbyGameObjects(wall);
    }

}
