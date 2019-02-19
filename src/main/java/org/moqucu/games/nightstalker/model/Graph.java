package org.moqucu.games.nightstalker.model;

import javafx.geometry.Point2D;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MazeGraph {

    private Map<Point2D, List<Point2D>> adjacencyList = new HashMap<>();

    private void addDirectedEdge(Point2D source, Point2D destination) {

        if (!adjacencyList.containsKey(source))
            adjacencyList.put(source, new LinkedList<>());

        if (!adjacencyList.get(source).contains(destination))
            adjacencyList.get(source).add(destination);
    }

    public void addEge(Point2D source, Point2D destination) {

        addDirectedEdge(source, destination);
        addDirectedEdge(destination, source);
    }
}
