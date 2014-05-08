package fr.xebia;

import java.util.*;
import java.util.stream.Collectors;

public class Grid {
    private final Set<Coords> cardsToFlip;
    private final Set<Coords> unkownCards;
    private final Map<Card, Coords> cards;

    public Grid(int gridSize) {
        cardsToFlip = new HashSet<>();
        unkownCards = new HashSet<>(gridSize * gridSize);
        cards = new HashMap<>(gridSize * gridSize);

        for (int x = 0; x < gridSize; x++) {
            for (int y = 0; y < gridSize; y++) {
                unkownCards.add(new Coords(x, y));
            }
        }
    }

    int[][] getCardsToFlip() {
        Set<Coords> cardsToFlip = this.cardsToFlip;
        Random random = new Random();
        if (cardsToFlip.isEmpty()) {
            cardsToFlip = unkownCards.stream().sorted((coords1, coords2) -> random.nextBoolean() ? -1 : 1).limit(2).collect(Collectors.toSet());
        }

        Iterator<Coords> iterator = cardsToFlip.iterator();
        int[][] result = new int[2][];
        for (int i = 0; i < 2; i++) {
            Coords next = iterator.next();
            result[i] = new int[]{next.x, next.y};
        }

        this.cardsToFlip.clear();

        return result;
    }

    public void update(int[][] coords, GameResponse response) {
        cards.put(response.turn.cards.get(0), new Coords(coords[0][0], coords[0][1]));
        cards.put(response.turn.cards.get(1), new Coords(coords[1][0], coords[1][1]));
    }

    private static final class Coords {
        private final int x;
        private final int y;

        private Coords(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int[] toArray() {
            return new int[0];
        }
    }
}
