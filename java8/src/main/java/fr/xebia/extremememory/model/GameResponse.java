package fr.xebia.extremememory.model;

import static java.lang.String.format;

public class GameResponse {
    public int gameId;
    public double progress;
    public Turn turn;
    public int gameScore;

    @Override
    public String toString() {
        return format("Game #%s finished at %.1f%% with score %d; %s", gameId, progress, gameScore, turn);
    }
}
