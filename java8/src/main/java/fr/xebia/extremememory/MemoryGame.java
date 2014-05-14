package fr.xebia.extremememory;

import fr.xebia.extremememory.model.GameResponse;

class MemoryGame {
    long[][] getCardCoordsToFlip() {
        long[][] coords = {new long[]{0, 0}, new long[]{0, 1}};
        System.out.format("Playing with [[%d, %d], [%d, %d]]%n", coords[0][0], coords[0][1], coords[1][0], coords[1][1]);
        return coords;
    }

    void update(GameResponse response) {
        System.out.format("%s%n", response);
    }

    boolean isFinished() {
        return true;
    }
}
