package model.graph;

import model.dto.FullStopDto;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class StopNode {
    private FullStopDto fullStopDto;
    private Integer distance;
    private LinkedList<StopNode> shortestPath;

    public StopNode(FullStopDto fullStopDto) {
        this.fullStopDto = fullStopDto;
        this.distance = Integer.MAX_VALUE;
        this.shortestPath = new LinkedList<>();
    }

    public FullStopDto getStopDto() {
        return fullStopDto;
    }

    public Integer getDistance() {
        return distance;
    }

    void setDistance(Integer distance) {
        this.distance = distance;
    }

    public List<StopNode> getShortestPath() {
        return Collections.unmodifiableList(shortestPath);
    }
    void setShortestPath(LinkedList<StopNode> shortestPath) {
        this.shortestPath = shortestPath;
    }

    @Override
    public String toString() {
        return this.fullStopDto.getName() + ", " + this.fullStopDto.getKey().getFirst();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StopNode stopNode = (StopNode) o;
        if (stopNode.getStopDto() == null) return false;
        return Objects.equals(fullStopDto.getKey().getSecond(), stopNode.fullStopDto.getKey().getSecond())
                && Objects.equals(fullStopDto.getKey().getFirst(), stopNode.fullStopDto.getKey().getFirst());
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullStopDto);
    }
}
