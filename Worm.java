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

    public String move(String direction) {
        switch(direction.toLowerCase()) {
            case "a":
            case "l":
                shiftCoordinates(new Coordinate(-1, 0));
                return "l";
            case "d":
            case "r":
                shiftCoordinates(new Coordinate(1, 0));
                return "r";
        }
        return "x";
    }

    private void shiftCoordinates(Coordinate dir) {
        Coordinate toShift = head().add(dir);

        for (int i = 0; i < wormSegments.length; i++) {
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
