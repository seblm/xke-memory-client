package fr.xebia.extremememory.engine;

import java.util.*;

import static java.lang.String.format;
import static java.util.stream.Collectors.*;
import static java.util.stream.LongStream.range;

public class Grid {
    private final Random random;
    private final List<Card> grid;

    public Grid(long size) {
        this.random = new Random();
        this.grid = new LinkedList<>();
        range(0, size).forEach(y ->
                range(0, size).forEach(x ->
                        grid.add(new Card(new Coordinate(x, y)))));
    }

    public void flipped(long x, long y, String card, boolean found) {
        findCard(x, y).setValue(card).setFound(found);
    }

    private Card findCard(long x, long y) {
        return grid.stream()
                .filter(currentCard -> currentCard.coordinate.x == x && currentCard.coordinate.y == y)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(format("card at (%d, %d) doesn't exists", x, y)));
    }

    public long[][] getCardsToFlip() {
        return getCardsToFlip(random);
    }

    long[][] getCardsToFlip(Random random) {
        return grid.stream()
                .filter(card -> card.getValue() != null && !card.isFound())
                .collect(groupingBy(Card::getValue))
                .entrySet().stream()
                .filter(entry -> entry.getValue().size() == 2)
                .map(entry -> new long[][]{
                        entry.getValue().get(0).coordinate.toArray(),
                        entry.getValue().get(1).coordinate.toArray()
                })
                .findFirst()
                .orElse(findRandomCardsToFlip(random));
    }

    private long[][] findRandomCardsToFlip(Random random) {
        List<long[]> cardsToFlip = grid.stream()
                .filter(card -> !card.isFound() && card.getValue() == null)
                .map(card -> card.coordinate.toArray())
                .collect(toList());
        if (cardsToFlip.size() == 0) {
            return new long[0][0];
        }
        if (cardsToFlip.size() == 1) {
            return new long[][]{cardsToFlip.remove(random.nextInt(cardsToFlip.size()))};
        }
        return new long[][]{
                cardsToFlip.remove(random.nextInt(cardsToFlip.size())),
                cardsToFlip.remove(random.nextInt(cardsToFlip.size()))};
    }

    @Override
    public String toString() {
        Map<Long, Optional<Card>> maxLengthByColumn = grid.stream().collect(groupingBy(
                card -> card.coordinate.x,
                maxBy((a, b) -> Integer.compare(a.toString().length(), b.toString().length()))));

        return grid.stream().reduce(new Formatter(),
                (formatter, card) -> {
                    if (card.coordinate.x > 0) {
                        formatter.format(" ");
                    } else if (card.coordinate.y > 0) {
                        formatter.format("%n");
                    }
                    int length = maxLengthByColumn.get(card.coordinate.x).get().toString().length();
                    return formatter.format("%c[%" + (length == 0 ? "" : Integer.toString(length)) + "s]",
                            card.getValue() == null ? '?' : card.isFound() ? '✓' : '⨯',
                            card.toString());
                },
                (a, b) -> a
        ).toString();
    }
}
