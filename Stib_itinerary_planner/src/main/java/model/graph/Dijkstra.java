package model.graph;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class Dijkstra {

    public static void calculateShortestPathFromSource(StibNetGraph graph, StopNode source) {
        
        source.setDistance(0);

        Set<StopNode> settledNodes = new HashSet<>();
        Set<StopNode> unsettledNodes = new HashSet<>();
        unsettledNodes.add(source);

        while (!unsettledNodes.isEmpty()) {
            StopNode currentNode = getLowestDistanceNode(unsettledNodes);
            unsettledNodes.remove(currentNode);
            var list = graph.getGraph().getAdj().get(currentNode);
            for (var adjacencyPair : list) {
                StopNode adjacentNode = adjacencyPair.getConnectedNode();
                Integer edgeWeight = adjacencyPair.getWeight();

                if (!settledNodes.contains(adjacentNode)) {
                    CalculateMinimumDistance(adjacentNode, edgeWeight, currentNode);
                    unsettledNodes.add(adjacentNode);
                }
            }
            settledNodes.add(currentNode);
        }
    }

    private static void CalculateMinimumDistance(StopNode evaluationNode, Integer edgeWeight, StopNode sourceNode) {
        Integer sourceDistance = sourceNode.getDistance();
        if (sourceDistance + edgeWeight < evaluationNode.getDistance()) {
            evaluationNode.setDistance(sourceDistance + edgeWeight);
            LinkedList<StopNode> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
            shortestPath.add(sourceNode);
            evaluationNode.setShortestPath(shortestPath);
        }
    }

    private static StopNode getLowestDistanceNode(Set<StopNode> unsettledNodes) {
        StopNode lowestDistanceNode = null;
        int lowestDistance = Integer.MAX_VALUE;
        for (StopNode node : unsettledNodes) {
            int nodeDistance = node.getDistance();
            if (nodeDistance < lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }
        return lowestDistanceNode;
    }
}