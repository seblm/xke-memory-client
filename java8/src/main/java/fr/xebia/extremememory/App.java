package fr.xebia.extremememory;

import javax.ws.rs.WebApplicationException;
import java.util.Optional;

import static java.lang.Thread.sleep;
import static java.util.Optional.empty;

public class App {
    /**
     * Server name or ip address.
     */
    private static final String HOST = "localhost";

    /**
     * Number of seconds to wait before next request.
     */
    private static final long DELAY = 0;

    /**
     * Provide a gravatar email to identicate to server.
     */
    private static final Optional<String> EMAIL = empty();

    private ClientApi clientApi;

    App(String host) {
        this.clientApi = new ClientApi(host);
    }

    public static void main(String[] args) {
        new App(HOST).register(EMAIL).startGame();
    }

    private App register(Optional<String> email) {
        email.ifPresent(clientApi::register);
        return this;
    }

    private void startGame() {
        MemoryGame game = new MemoryGame();

        try {
            do {
                play(game);
            } while (!game.isFinished());
        } catch (WebApplicationException e) {
            System.out.println(e.getResponse().readEntity(String.class));
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    private void play(MemoryGame game) throws InterruptedException {
        game.update(clientApi.play(game.getCardCoordsToFlip()));

        if (DELAY > 0) {
            sleep(DELAY * 1000);
        }
    }
}
