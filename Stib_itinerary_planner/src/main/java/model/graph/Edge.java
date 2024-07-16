package model.graph;

/**
 * Class that represents an edge in a weighted graph.
 * @param <T> the object to create the node with.
 */
public class Edge<T> {
    private final T connectedNode;
    private int weight;

    /**
     * The constructor of the edge with a given weight.
     * @param connectedNode The node to connect on the edge.
     * @param weight the weight of the edge.
     */
    public Edge(T connectedNode, int weight) {
        this.connectedNode = connectedNode;
        this.weight = weight;
    }

    /**
     * Constructor of an edge with a weight of 0;
     * @param connectedNode The node to connect on the edge.
     */
    public Edge(T connectedNode) {
        this.connectedNode = connectedNode;
        this.weight = 0;
    }

    /**
     * Gets the connected node to the edge.
     * @return the connected edge.
     */
    public T getConnectedNode() {
        return connectedNode;
    }

    public int getWeight() {
        return weight;
    }
    public void setWeight(int weight){
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "connectedNode=" + connectedNode +
                ", weight=" + weight +
                '}';
    }
}
