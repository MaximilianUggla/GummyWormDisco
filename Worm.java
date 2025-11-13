package GummyWormDisco;

public class Worm {
    Coordinate head;
    Coordinate[] wormSegments;

    public Worm(String[] segments) {
        Coordinate[] wormSegments = new Coordinate[segments.length];
        int index = 0;
        for (String cordinate : segments) {
            String[] xy = cordinate.split(",");
            wormSegments[index] = new Coordinate(Integer.parseInt(xy[0]), Integer.parseInt(xy[1]));
            index += 1;
        }
        head = wormSegments[0];
        this.wormSegments = wormSegments;
    }

    public void move() {}

    @Override
    public String toString() {
        String str = "";
        for (Coordinate cordinate : wormSegments) {
            str = str + cordinate + " ";
        }
        return str;
    }
}
