package model.dto;

import model.dataStructure.Pair;

import java.util.Objects;

public class StopDto extends Dto<Pair<Integer, Integer>> {
        private int idOrder;

    /**
     * Creates a new instance of <code>StopDto</code>
     * @param key id of the stop.
     * @param idOrder id of the order of the stop.
     */
    public StopDto(Pair<Integer, Integer> key, int idOrder) {
        super(key);
        this.idOrder = idOrder;
    }

    /**
     * Returns the order id of the stop.
     * @return the order id of the stop.
     */
    public int getIdOrder() {
        return idOrder;
    }

    @Override
    public String toString() {
        return "StopDto{" +
                ", idOrder=" + idOrder +
                ", key=" + key +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        StopDto stopDto = (StopDto) o;
        return idOrder == stopDto.idOrder && key.equals(stopDto.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idOrder);
    }
}
