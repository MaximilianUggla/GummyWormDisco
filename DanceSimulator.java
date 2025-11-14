package GummyWormDisco;

import java.util.List;

public class DanceSimulator {
    String[][] board;
    Worm[] worms;
    List<Integer> beats;
    Integer lastBeat;
    
    public DanceSimulator(String[][] board, Worm[] worms, List<Integer> beats) {
        this.board = board;
        this.worms = worms;
        this.beats = beats;
    }

    public void start() {
        for (int beat = 0; beat < lastBeat; beat++) {
            if (!beats.contains(beat)) {
                for (Worm w : worms) {

                }

                
            } else {System.out.println("x");}
        }
    }
}
