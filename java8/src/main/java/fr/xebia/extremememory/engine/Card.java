package fr.xebia.extremememory.engine;

import java.util.Optional;

import static java.util.Optional.empty;

public class Card {
    public final Coordinate coordinate;

    private boolean found;
    private Optional<String> value;

    public Card(Coordinate coordinate) {
        this.coordinate = coordinate;
        this.value = empty();
        this.found = false;
    }

    boolean isUnknow() {
        return !value().isPresent();
    }

    boolean isKnownButNotFound() {
        return value().isPresent() && !isFound();
    }

    @Override
    public String toString() {
        return value.orElse("");
    }

    public boolean isFound() {
        return found;
    }

    public void setFound(boolean found) {
        this.found = found;
    }

    public Optional<String> value() {
        return value;
    }

    public Card setValue(String value) {
        this.value = Optional.of(value);
        return this;
    }
}
