package fr.xebia.extremememory.engine;

public class Card {
    public final Coordinate coordinate;

    private boolean found;
    private String value;

    public Card(Coordinate coordinate) {
        this.coordinate = coordinate;
        this.found = false;
    }

    @Override
    public String toString() {
        return value == null ? "" : value;
    }

    public boolean isFound() {
        return found;
    }

    public void setFound(boolean found) {
        this.found = found;
    }

    public String getValue() {
        return value;
    }

    public Card setValue(String value) {
        this.value = value;
        return this;
    }
}
