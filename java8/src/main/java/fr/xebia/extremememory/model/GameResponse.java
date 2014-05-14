package fr.xebia.extremememory.model;

import org.codehaus.jackson.annotate.JsonProperty;

import static java.lang.String.format;

public class GameResponse {
    public final int gameId;
    public final double progress;
    public final Turn turn;
    public final int gameScore;

    public GameResponse(@JsonProperty("gameId") int gameId,
                        @JsonProperty("progress") double progress,
                        @JsonProperty("turn") Turn turn,
                        @JsonProperty("gameScore") int gameScore) {
        this.gameId = gameId;
        this.progress = progress;
        this.turn = turn;
        this.gameScore = gameScore;
    }

    @Override
    public String toString() {
        return format("Game #%s finished at %.1f%% with score %d; %s", gameId, progress, gameScore, turn);
    }
}
