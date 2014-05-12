package fr.xebia;

import java.util.Optional;

import static java.lang.Long.parseLong;
import static java.util.Optional.empty;

public class App {
    public static void main(String[] args) {
        new MemoryGame().startGame(readGridSize(args), readEmail(args), readDelay(args));
    }

    private static long readGridSize(String[] args) {
        return readLong(args, 0, 2);
    }

    private static Optional<String> readEmail(String[] args) {
        try {
            if (args[1].length() > 0) {
                return Optional.of(args[1]);
            }
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }
        return empty();
    }

    private static long readDelay(String[] args) {
        return readLong(args, 2, 0);
    }

    private static long readLong(String[] args, int index, long defaultValue) {
        try {
            return parseLong(args[index]);
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            return defaultValue;
        }
    }
}
