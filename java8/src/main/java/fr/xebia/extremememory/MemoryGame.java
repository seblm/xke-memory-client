package fr.xebia.extremememory;

import fr.xebia.extremememory.engine.Grid;
import fr.xebia.extremememory.model.Card;
import fr.xebia.extremememory.model.GameResponse;

class MemoryGame {
    private final Grid grid;

    private long[][] coords;

    MemoryGame(long size) {
        this.grid = new Grid(size);
    }

    long[][] getCardCoordsToFlip() {
        coords = grid.getCardsToFlip();
        System.out.format("Playing with [[%d, %d], [%d, %d]]%n", coords[0][0], coords[0][1], coords[1][0], coords[1][1]);
        return coords;
    }

    void update(GameResponse response) {
        System.out.format("%s%n", response);
        int i = 0;
        for (Card card : response.turn.cards) {
            if (coords[i] == null) {
                throw new IllegalArgumentException("Please call getCardCoordsToFlip() before update(GameResponse)");
            }
            grid.flipped(coords[i][0], coords[i][1], card.color + card.symbol, card.found);
            i++;
        }
    }

    boolean isFinished() {
        return grid.getCardsToFlip().length == 0;
    }
}
