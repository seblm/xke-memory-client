package fr.xebia;

import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import java.util.Optional;

import static javax.ws.rs.client.ClientBuilder.newBuilder;
import static javax.ws.rs.client.Entity.entity;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;

public class MemoryGame {
    private final Client client;

    public MemoryGame() {
        this.client = newBuilder().register(JacksonFeature.class).build();
    }

    void startGame(long gridSize, Optional<String> email, long secondsToWait) {
        System.out.format("start with grid size %d, registering %s and waiting %ds%n", gridSize, email.orElse("no email"), secondsToWait);
        email.ifPresent(this::register);
        try {
            long[][] coords = new long[][]{new long[]{0, 0}, new long[]{0, 1}};
            GameResponse response = play(coords);

            System.out.format("message : %s%n", response.turn.message == null ? "empty" : response.turn.message);

            if (secondsToWait > 0) {
                Thread.sleep(secondsToWait * 1000);
            }
        } catch (WebApplicationException e) {
            System.out.println(e.getResponse().readEntity(String.class));
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    private void register(String email) {
        client.target("http://localhost:3000")
                .path("/scores/register")
                .request()
                .post(entity(email, APPLICATION_JSON_TYPE));
    }

    private GameResponse play(long[][] coords) throws WebApplicationException {
        System.out.format("/play [[%d, %d], [%d, %d]] ", coords[0][0], coords[0][1], coords[1][0], coords[1][1]);
        GameResponse gameResponse = client.target("http://localhost:3000")
                .path("/play")
                .request()
                .post(entity(coords, APPLICATION_JSON_TYPE), GameResponse.class);
        System.out.format("%s%n", gameResponse);
        return gameResponse;
    }

}
