package fr.xebia.extremememory.model;

import java.util.List;

import static java.lang.String.format;

public class Turn {
    public int turnScore;
    public List<Card> cards;
    public String message;

    @Override
    public String toString() {
        return format("Current turn scores %d with %s%s", turnScore, cards, message == null || message.isEmpty() ? "" : ("\n" + message));
    }
}
