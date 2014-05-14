package fr.xebia.extremememory.engine;

import org.junit.Test;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GridTest {
    @Test
    public void should_have_a_string_representation_and_initial_values() {
        Grid grid = new Grid(2);

        String gridAsString = grid.toString();

        assertThat(gridAsString).isEqualTo("" +
                "?[] ?[]\n" +
                "?[] ?[]" +
                "");
    }

    @Test
    public void should_flip_cards() {
        Grid grid = new Grid(2);
        grid.flipped(0, 0, "blue umbrella", false);

        String gridAsString = grid.toString();

        assertThat(gridAsString).isEqualTo("" +
                "⨯[blue umbrella] ?[]\n" +
                "?[             ] ?[]" +
                "");
    }

    @Test
    public void should_flip_cards_and_tell_if_already_found() {
        Grid grid = new Grid(2);
        grid.flipped(0, 0, "blue umbrella", true);

        String gridAsString = grid.toString();

        assertThat(gridAsString).isEqualTo("" +
                "✓[blue umbrella] ?[]\n" +
                "?[             ] ?[]" +
                "");
    }

    @Test
    public void should_tell_to_flip_same_cards() {
        Grid grid = new Grid(2);
        grid.flipped(0, 0, "blue umbrella", false);
        grid.flipped(1, 1, "blue umbrella", false);

        long[][] coords = grid.getCardsToFlip();

        assertThat(coords).containsExactly(new long[]{0, 0}, new long[]{1, 1});
    }

    @Test
    public void should_tell_to_flip_random_cards() {
        Random random = mock(Random.class);
        when(random.nextInt(eq(4))).thenReturn(0);
        when(random.nextInt(eq(3))).thenReturn(0);
        Grid grid = new Grid(2);

        long[][] coords = grid.getCardsToFlip(random);

        assertThat(coords).containsExactly(new long[]{0, 0}, new long[]{1, 0});
    }
}