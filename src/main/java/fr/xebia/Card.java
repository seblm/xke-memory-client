package fr.xebia;

public class Card {
    public String symbol;
    public String color;
    public boolean found;

    public Card() {
    }

    @Override
    public String toString() {
        return "Card{" +
                "symbol='" + symbol + '\'' +
                ", color='" + color + '\'' +
                ", found=" + found +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;

        if (color != null ? !color.equals(card.color) : card.color != null) return false;
        if (symbol != null ? !symbol.equals(card.symbol) : card.symbol != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = symbol != null ? symbol.hashCode() : 0;
        result = 31 * result + (color != null ? color.hashCode() : 0);
        return result;
    }
}
