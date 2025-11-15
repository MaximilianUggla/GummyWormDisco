package GummyWormDisco;

public class Coordinate implements Comparable<Coordinate> {
    private int x, y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void update(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coordinate add(Coordinate other) {
        return new Coordinate(x + other.x(), y + other.y());
    }

    public int x() {return x;}

    public int y() {return y;}

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Coordinate other) {
            return x() == other.x() && y() == other.y();

        } else {return false;}
    }
    @Override
    public int hashCode() {
        return x()*821^2 + y()*997^2;
    }

    @Override
    public String toString() {
        return x + "," + y;
    }

    @Override
    public int compareTo(Coordinate other) {
        int comparisionX = x() - other.x();
        if (comparisionX == 0) {
            return y() - other.y();
        } else {return comparisionX;}
    }

}