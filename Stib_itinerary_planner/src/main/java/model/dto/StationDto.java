package model.dto;

public class StationDto extends Dto<Integer>{
    private String name;

    /**
     * Creates a new instance of <code>StationDto</code>
     * @param key id of the station.
     * @param name the name of the station.
     */
    public StationDto(Integer key, String name) {
        super(key);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "StationDto{" +
                "name='" + name + '\'' +
                ", key=" + key +
                '}';
    }
}
