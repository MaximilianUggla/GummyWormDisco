package GummyWormDisco;

public class Coordinate {
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
    public String toString() {
        return x + "," + y;
    }

}