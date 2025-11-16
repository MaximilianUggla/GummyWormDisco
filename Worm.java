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

    public int id() {
        return id;
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

    public void move(String direction) {
        switch(direction.toLowerCase()) {
            case "left":
                shiftCoordinates(new Coordinate(-1, 0));
            case "right":
                shiftCoordinates(new Coordinate(1, 0));
            default:
                Coordinate currdir = currentDirection();  
                shiftCoordinates(new Coordinate(currdir.x(), currdir.y()));
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
    public boolean equals(Object obj) {
        if (obj instanceof Worm w) {
            return Arrays.equals(wormSegments, w.wormSegments);
        } else {return false;}
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
