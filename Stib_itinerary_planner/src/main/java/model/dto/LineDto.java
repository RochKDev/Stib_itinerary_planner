package model.dto;

public class LineDto extends Dto<Integer>{

    /**
     * Creates a new instance of <code>LineDto</code> with the key of the data.
     *
     * @param key key of the data.
     */
    public LineDto(Integer key) {
        super(key);
    }
    @Override
    public String toString() {
        return "LineDto{" +
                "key=" + key +
                '}';
    }
}
