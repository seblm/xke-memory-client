package fr.xebia;

public class GameResponse {
    public int gameId;
    public double progress;
    public Turn turn;
    public int gameScore;

    public GameResponse() {
    }

    @Override
    public String toString() {
        return "GameResponse{" +
                "gameId=" + gameId +
                ", progress=" + progress +
                ", turn=" + turn +
                ", gameScore=" + gameScore +
                '}';
    }
}
