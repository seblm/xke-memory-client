package fr.xebia.engine;

import java.util.Formatter;

public class Coordinate {
    public final long x;
    public final long y;

    public Coordinate(long x, long y) {
        this.x = x;
        this.y = y;
    }

    public long[] toArray() {
        return new long[]{x, y};
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coordinate that = (Coordinate) o;

        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        int result = (int) x;
        result = (int) (31 * result + y);
        return result;
    }

    @Override
    public String toString() {
        return new Formatter().format("(%d, %d)", x, y).toString();
    }
}
