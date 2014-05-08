package fr.xebia;

import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.client.Client;

import static javax.ws.rs.client.ClientBuilder.newBuilder;
import static javax.ws.rs.client.Entity.entity;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;

public class App {
    private final Client client;

    public App() {
        this.client = newBuilder().register(JacksonFeature.class).build();
    }

    public static void main(String[] args) {
        new App().startGame(6, "slemerdy@xebia.com");
    }

    void startGame(int gridSize, String email) {
        register(email);

        Grid grid = new Grid(gridSize);
        int[][] coords = grid.getCardsToFlip();
        GameResponse response = play(coords);
        grid.update(coords, response);

        System.out.format("message : %s%n", response.turn.message == null ? "empty" : response.turn.message);
    }

    private void register(String email) {
        client.target("http://localhost:3000")
                .path("/scores/register")
                .request()
                .post(entity(email, APPLICATION_JSON_TYPE));
    }

    private GameResponse play(int[][] coords) {
        System.out.format("/play [[%d, %d], [%d, %d]] ", coords[0][0], coords[0][1], coords[1][0], coords[1][1]);
        GameResponse gameResponse = client.target("http://localhost:3000")
                .path("/play")
                .request()
                .post(entity(coords, APPLICATION_JSON_TYPE), GameResponse.class);
        System.out.format("%s%n", gameResponse);
        return gameResponse;
    }
}
