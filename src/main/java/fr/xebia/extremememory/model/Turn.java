package fr.xebia.extremememory.model;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

import static java.lang.String.format;

public class Turn {
    private final int turnScore;
    private final List<Card> cards;
    private final String message;

    public Turn(@JsonProperty("turnScore") int turnScore,
                @JsonProperty("cards") List<Card> cards,
                @JsonProperty("message") String message) {
        this.turnScore = turnScore;
        this.cards = cards;
        this.message = message;
    }

    @Override
    public String toString() {
        return format("Current turn scores %d with %s%s", turnScore, cards, message == null || message.isEmpty() ? "" : ("\n" + message));
    }
}
