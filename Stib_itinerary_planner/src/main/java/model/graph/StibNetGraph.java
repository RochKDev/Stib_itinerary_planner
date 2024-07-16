package model.graph;

import model.dataStructure.Pair;
import model.dto.FrequentTravelDto;
import model.dto.FullStopDto;
import model.exceptions.RepositoryException;
import model.repository.FrequentTravelRepository;
import model.repository.StopRepository;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;

public class StibNetGraph {
    private WeightedGraph<StopNode> graph;
    private final PropertyChangeSupport support;
    private final FrequentTravelRepository repository;

    public StibNetGraph() throws RepositoryException {
        this.graph = new WeightedGraph<>();
        populateGraph();
        repository = new FrequentTravelRepository();
        this.support = new PropertyChangeSupport(this);
    }

    public WeightedGraph<StopNode> getGraph() {
        return graph;
    }

    private void populateGraph() throws RepositoryException {
        StopRepository repo = new StopRepository();
        List<FullStopDto> list = repo.getAllFullStop();

        for (var node : list) {
            graph.getAdj().put(new StopNode(node), new LinkedList<>());
        }
        var entriesToModify = new ArrayList<>(graph.getAdj().entrySet());

        for (var node : entriesToModify) {
            if (node.getKey().getStopDto() == null) {
                continue;
            }
            var neighbours = repo.getAllNeighbours(node.getKey().getStopDto().getKey());
            for (var neighbour : neighbours) {
                if (neighbour.getKey().getFirst() != node.getKey().getStopDto().getKey().getFirst()) {// diff line
                    StopNode transit = new StopNode(new FullStopDto(
                            new Pair<>(0, node.getKey().getStopDto().getKey().getSecond()),
                            -1, node.getKey().getStopDto().getName()));
                    for (var find : graph.getAdj().entrySet()) {// to find the exact existing node in the map
                        if (find.getKey().getStopDto() != null && find.getKey().getStopDto().equals(transit.getStopDto())) {
                            transit = find.getKey();
                        }
                    }
                    if (!graph.hasEdge(node.getKey(), transit)) {
                        graph.addEdge(node.getKey(), transit, 1);
                    }

                } else {
                    StopNode findNode;
                    for (var find : graph.getAdj().entrySet()) {// to find the exact existing node in the map
                        if (find.getKey().getStopDto() != null && find.getKey().getStopDto().equals(neighbour)) {
                            findNode = find.getKey();
                            if (!graph.hasEdge(node.getKey(), findNode)) {
                                graph.addEdge(node.getKey(), findNode, 1);
                            }
                        }
                    }
                }
            }
        }
    }

    public void resetGraph() {
        for (var node : graph.getAdj().entrySet()) {
            node.getKey().setDistance(Integer.MAX_VALUE);
            node.getKey().setShortestPath(new LinkedList<>());
        }
    }

    public void calculateShortestPathFromSource(StopNode source) {
        Dijkstra.calculateShortestPathFromSource(this, source);
        support.firePropertyChange("shortestPathCompleted", null, this);
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }
    public void removeFrequent(FrequentTravelDto travel) throws RepositoryException{
            repository.remove(travel.getKey());
            support.firePropertyChange("frequentRemoved", null, travel);
    }
    public void addFrequentTravel(FrequentTravelDto frequentTravelDto) throws RepositoryException {
        repository.add(frequentTravelDto);
        support.firePropertyChange("frequentAdded", null, frequentTravelDto);
    }
    public List<FrequentTravelDto> getAll() throws RepositoryException{
        return repository.getAll();
    }


}
