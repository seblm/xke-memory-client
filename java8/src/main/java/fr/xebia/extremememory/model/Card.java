package fr.xebia.extremememory.model;

import org.codehaus.jackson.annotate.JsonProperty;

import static java.lang.String.format;

public class Card {
    private final String symbol;
    private final String color;
    private final boolean found;

    public Card(@JsonProperty("symbol") String symbol,
                @JsonProperty("color") String color,
                @JsonProperty("found") boolean found) {
        this.symbol = symbol;
        this.color = color;
        this.found = found;
    }

    @Override
    public String toString() {
        return format("%c %s %s", found ? '✓' : '✗', color, symbol);
    }
}
