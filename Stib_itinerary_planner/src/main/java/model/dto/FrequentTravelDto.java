package model.dto;

import model.dataStructure.Pair;

public class FrequentTravelDto extends Dto<String>{
    private Pair<Integer, Integer> keySource;
    private Pair<Integer, Integer> keyDestination;

    public FrequentTravelDto(String key, Pair<Integer, Integer> keySource, Pair<Integer, Integer> keyDestination) {
        super(key);
        this.keySource = keySource;
        this.keyDestination = keyDestination;
    }

    public Pair<Integer, Integer> getKeySource() {
        return keySource;
    }

    public Pair<Integer, Integer> getKeyDestination() {
        return keyDestination;
    }

    @Override
    public String toString() {
        return key;
    }
}
