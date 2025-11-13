package GummyWormDisco;

public class Worm {
    private Coordinate[] wormSegments;

    public Worm(String[] segments) {
        Coordinate[] wormSegments = new Coordinate[segments.length];
        int index = 0;
        for (String cordinate : segments) {
            String[] xy = cordinate.split(",");
            wormSegments[index] = new Coordinate(Integer.parseInt(xy[0]), Integer.parseInt(xy[1]));
            index += 1;
        }
        this.wormSegments = wormSegments;
    }

    public Coordinate head() {
        return wormSegments[0];
    }

    public void move(String direction) {
        Coordinate dir;

        switch(direction) {
            case "u":
                dir = new Coordinate(0, -1);
                break;
            case "d":
                dir = new Coordinate(0, 1);
                break;
            case "l":
                dir = new Coordinate(-1, 0);
                break;
            case "r":
                dir = new Coordinate(1, 0);
                break;
            default:
                dir = new Coordinate(0, 0);
        }

        Coordinate toShift = head().add(dir);

        for (int i = 0; i<wormSegments.length; i++) {
            Coordinate temporary = wormSegments[i];
            wormSegments[i] = toShift;
            toShift = temporary;
        }
    }

    @Override
    public String toString() {
        String str = "";
        for (Coordinate cordinate : wormSegments) {
            str = str + cordinate + " ";
        }
        return str;
    }
}
