package fr.xebia.extremememory;

import fr.xebia.extremememory.MemoryGame;
import fr.xebia.extremememory.model.Card;
import fr.xebia.extremememory.model.GameResponse;
import fr.xebia.extremememory.model.Turn;
import org.junit.Test;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class MemoryGameTest {
    @Test
    public void should_get_card_coors_to_flip() {
        MemoryGame game = new MemoryGame();

        long[][] coords = game.getCardCoordsToFlip();

        assertThat(coords).isEqualTo(new long[][]{new long[]{0, 0}, new long[]{0, 1}});
    }

    @Test
    public void should_update_with_game_response() {
        MemoryGame game = new MemoryGame();

        game.update(new GameResponse(0, .3, new Turn(2, asList(new Card("boat", "red", true), new Card("umbrella", "blue", false)), "message"), 12));

        assertThat(game).isNotNull();
    }

    @Test
    public void should_tell_if_game_is_finished() {
        MemoryGame game = new MemoryGame();

        boolean finished = game.isFinished();

        assertThat(finished).isTrue();
    }
}
