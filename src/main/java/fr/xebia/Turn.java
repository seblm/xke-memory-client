package fr.xebia;

import java.util.List;

public class Turn {
    public int turnScore;
    public List<Card> cards;
    public String message;

    public Turn() {
    }

    @Override
    public String toString() {
        return "Turn{" +
                "turnScore=" + turnScore +
                ", cards=" + cards +
                '}';
    }
}
