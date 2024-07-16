package model.graph;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Class that represents a weighted graph.
 * @param <T> the type of object to be used as a node.
 */
public class WeightedGraph<T> {

    private Map<T, LinkedList<Edge<T>>> adj = new HashMap<>();// map of the node adjacency.
                                                              // the key represents the node and the list represents
                                                              // the adjacent edges with the opposite node.
    private final boolean directed;

    /**
     * Constructor of WeightedGraph that creates a graph directed or not.
     * @param directed boolean to know if the graph is directed.
     */
    public WeightedGraph(boolean directed) {
        this.directed = directed;
    }

    /**
     * Constructor by default that creates an undirected graph.
     */
    public WeightedGraph() {
        this.directed = false;
    }

    /**
     * Adds the edge between the 2 specified nodes, if the nodes are not in the graph,
     * it adds them in it.
     * @param node1 the source node if the graph is directed.
     * @param node2 the destination node if the graph is directed.
     * @param weight the weight of the edge.
     */
    public void addEdge(T node1, T node2, int weight){
        this.adj.putIfAbsent(node1, new LinkedList<>()); //add node
        this.adj.putIfAbsent(node2, new LinkedList<>()); //add node
        Edge<T> edge1 = new Edge<>(node2, weight);
        adj.get(node1).add(edge1); // add edge
        if (!directed){ // undirected
            Edge<T> edge2 = new Edge<>(node1, weight);
            adj.get(node2).add(edge2);
        }
    }

    /**
     * Adds the edge between the 2 specified nodes, if the nodes are not in the graph,
     * it adds them in it.
     * @param node1 the source node if the graph is directed.
     * @param node2 the destination node if the graph is directed.
     */
    public void addEdge(T node1, T node2){
        this.adj.putIfAbsent(node1, new LinkedList<>()); //add node
        this.adj.putIfAbsent(node2, new LinkedList<>()); //add node
        Edge<T> edge1 = new Edge<>(node2);
        adj.get(node1).add(edge1); // add edge
        if (!directed){ // undirected
            Edge<T> edge2 = new Edge<>(node1);
            adj.get(node2).add(edge2);
        }
    }

    /**
     * Searches for the edge between the two given nodes.
     * @param node1 the first node.
     * @param node2 the second node.
     * @return the edge between the two nodes.
     */
    private Edge<T> findEdgeByNode(T node1, T node2) {
        LinkedList<Edge<T>> neighbours_node1 = adj.get(node1);
        if (neighbours_node1 == null){
            return null;
        }
        for (Edge<T> edge: neighbours_node1) {
            if (edge.getConnectedNode().equals(node2)) {
                return edge;
            }
        }
        return null;
    }

    /**
     * Removes the edge between the two given edges.
     * @param node1 the first node.
     * @param node2 the second node.
     */
    public void removeEdge(T node1, T node2) {
        LinkedList<Edge<T>> neighbours_node1 = adj.get(node1);
        LinkedList<Edge<T>> neighbours_node2 = adj.get(node2);
        if (neighbours_node1 == null || neighbours_node2 == null)
            return;
        Edge<T> edge1 = findEdgeByNode(node1, node2);
        neighbours_node1.remove(edge1);
        if (!directed)  {//undirected
            Edge<T> edge2 = findEdgeByNode(node2, node1);
            neighbours_node2.remove(edge2);
        }
    }
    /**
     * Checks whether there is node by its key.
     * @param key the node to check.
     * @return true if the node is in the graph, false otherwise.
     */
    public boolean hasNode(T key) {
        return adj.containsKey(key);
    }

    /**
     * Checks if an edge between the two given nodes exists.
     * @param node1 the first node.
     * @param node2 the second node.
     * @return true if the two nodes are linked together by an edge, false otherwise.
     */
    public boolean hasEdge(T node1, T node2) {
        Edge<T> edge1 = findEdgeByNode(node1, node2);
        if (directed) {//directed
            return edge1 != null;
        }
        else { //undirected or bi-directed
            Edge<T> edge2 = findEdgeByNode(node2, node1);
            return edge1 != null && edge2!= null;
        }
    }

    public Map<T, LinkedList<Edge<T>>> getAdj() {
        return adj;
    }
}
