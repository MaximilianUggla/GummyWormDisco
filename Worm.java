package GummyWormDisco;

import java.util.Arrays;
import java.util.List;

public class Worm {
    private Coordinate[] wormSegments;
    private final int id;

    public Worm(String[] segments, int i) {
        Coordinate[] wormSegments = new Coordinate[segments.length];
        int index = 0;
        for (String cordinate : segments) {
            String[] xy = cordinate.split(",");
            wormSegments[index] = new Coordinate(Integer.parseInt(xy[0]), Integer.parseInt(xy[1]));
            index += 1;
        }
        this.wormSegments = wormSegments;
        id = i;
    }

    public Coordinate head() {
        return wormSegments[0];
    }

    public List<Coordinate> getBodyCoordinates() {
        return Arrays.asList(wormSegments);
    }

    public Coordinate currentDirection() {
        int newX = head().x() - wormSegments[1].x();
        int newY = head().y() - wormSegments[1].y();
        return new Coordinate(newX, newY);
    }

    public String move(String direction) {
        switch(direction.toLowerCase()) {
            case "left":
                shiftCoordinates(new Coordinate(-1, 0));
                return id + "l";
            case "right":
                shiftCoordinates(new Coordinate(1, 0));
                return id + "r";
            default:
                Coordinate currdir = currentDirection();  
                shiftCoordinates(new Coordinate(currdir.x(), currdir.y()));
                return "x";
        }
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
