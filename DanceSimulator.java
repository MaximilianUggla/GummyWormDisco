package GummyWormDisco;

import java.util.ArrayList;
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

    public void start() {
        Iterator<Integer> spotlights = beats.iterator();
        Integer nextStop = spotlights.next();

        for (int i = 0; i < beats.getLast(); i++) {
            if (i != nextStop) {
                List<Coordinate> busy = new ArrayList<>();
                for (Worm w : worms) {
                    busy.addAll(w.getBodyCoordinates());
                }

                for (Worm w : worms) {
                    
                }

                
            } else {
                nextStop = spotlights.next();
                System.out.println("x");
            }
        }
    }
}
