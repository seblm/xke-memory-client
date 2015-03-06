package fr.xebia.extremememory.model;

import static java.lang.String.format;

public class Card {
    public String symbol;
    public String color;
    public boolean found;

    @Override
    public String toString() {
        return format("%c %s %s", found ? '✓' : '✗', color, symbol);
    }
}
