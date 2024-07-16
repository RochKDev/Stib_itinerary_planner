package model.dto;

import model.dataStructure.Pair;

import java.util.Objects;

public class FullStopDto {
    private Pair<Integer, Integer> key;
    private int idOrder;
    private String name;

    public FullStopDto(Pair<Integer, Integer> key, int idOrder, String name) {
        this.key = key;
        this.idOrder = idOrder;
        this.name = name;
    }

    public int getIdOrder() {
        return idOrder;
    }

    public String getName() {
        return name;
    }

    public Pair<Integer, Integer> getKey() {
        return key;
    }

    @Override
    public String toString() {
        return "FullStopDto{" +
                "id_line =" + key.getFirst() +
                " id_station = " + key.getSecond() +
                ", idOrder=" + idOrder +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FullStopDto that = (FullStopDto) o;
        return Objects.equals(key.getSecond(), that.getKey().getSecond())
                && Objects.equals(key.getFirst(), that.getKey().getFirst());
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, idOrder, name);
    }
}
