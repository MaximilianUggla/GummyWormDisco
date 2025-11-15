package GummyWormDisco;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class DanceSimulator {
    String[][] board;
    Worm[] worms;
    List<Integer> beats;
    
    public DanceSimulator(String[][] board, Worm[] worms, List<Integer> beats) {
        this.board = board;
        this.worms = worms;
        this.beats = beats;
    }

    public void startSim() {
        Iterator<Integer> spotlights = beats.iterator();
        Integer nextStop = spotlights.next();

        for (int i = 0; i < beats.getLast(); i++) {
            if (i != nextStop) {
                HashSet<Coordinate> busy = getBusy();

                List<Worm> colisionOrder = getColisionOrder(busy);
                
            } else {
                nextStop = spotlights.next();
                System.out.println("x");
            }
        }
    }

    private HashSet<Coordinate> getBusy() {
        HashSet<Coordinate> busy = new HashSet<>();
        for (Worm w : worms) {
            busy.addAll(w.getBodyCoordinates());
        }
        return busy;
    }

    private List<Worm> getColisionOrder(HashSet<Coordinate> busy) {
        List<Worm> colisionOrder = new ArrayList<>();
        for (Worm w : worms) {
            Coordinate head = w.head();
            Coordinate currDir = w.currentDirection();
            for (int j = 0; j < worms.length; j++) {
                head = head.add(currDir);
                if (busy.contains(head)) {
                    colisionOrder.add(j, w);
                }
            }
        }
        return colisionOrder;
    }
}
