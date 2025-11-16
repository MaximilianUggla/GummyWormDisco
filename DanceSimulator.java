package GummyWormDisco;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class DanceSimulator {
    private String[][] board;
    private int width, height;
    private Worm[] worms;
    private List<Integer> beats;
    private HashSet<Coordinate> busy;
    
    public DanceSimulator(String[][] board, int width, int height, Worm[] worms, List<Integer> beats) {
        this.board = board;
        this.width = width;
        this.height = height;
        this.worms = worms;
        this.beats = beats;
    }

    public void simulation() {
        Iterator<Integer> spotlights = beats.iterator();
        Integer nextStop = spotlights.next();

        for (int i = 0; i <= beats.getLast(); i++) {
            if (i != nextStop) {
                List<Worm> colisionOrder = getColisionOrder();
                if (colisionOrder.isEmpty()) {
                    turnAWorm();
                } else {turn(colisionOrder.getFirst());}
                
            } else {
                nextStop = spotlights.next();
                System.out.println("x");
            }
        }
    }

    private List<Worm> getColisionOrder() {
        busy = getBusy();
        List<Worm> colisionOrder = new ArrayList<>();
        for (Worm w : worms) {
            Pair<Boolean, Integer> colision = getColision(w, w.currentDirection());
            if (colision.getFirst()) {
                colisionOrder.add(colision.getSecound(), w);
            }
        }
        return colisionOrder;
    }    
    
    private Pair<Boolean, Integer> getColision(Worm w, Coordinate direction) {
        Coordinate head = w.head();
        for (int j = 0; j < worms.length; j++) {
            head = head.add(direction);
            if (busy.contains(head)) {
                return new Pair<Boolean,Integer>(true, j);
            }
        }  
        return new Pair<Boolean,Integer>(false, null);
    }

    private HashSet<Coordinate> getBusy() {
        HashSet<Coordinate> busy = new HashSet<>();
        for (Worm w : worms) {
            busy.addAll(w.getBodyCoordinates());
        }
        return busy;
    }

    private void turn(Worm w) {
        Pair<Boolean, Integer> leftTurn = getColision(w, new Coordinate(-1, 0));
        Pair<Boolean, Integer> rightTurn = getColision(w, new Coordinate(1, 0));
        if (leftTurn.getFirst() && rightTurn.getFirst()) {
            if (leftTurn.getSecound() > rightTurn.getSecound()) {
                w.move("left");
            } else {w.move("right");}
        } else if (leftTurn.getFirst()) {
            w.move("right");
        } else {w.move("left");}

    }

    private void turnAWorm() {
        Worm outOfBounds = outOfBounds();
        if (outOfBounds != null) {
            turn(outOfBounds);
        } else {goToBlue();}
    }

    private void goToBlue() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'goToBlue'");
    }

    private Worm outOfBounds() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'outOfBounds'");
    }
}
