package GummyWormDisco;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class DanceSimulator {
    private int width, height;
    private Worm[] worms;
    private List<Integer> beats;
    private HashSet<Coordinate> busy;
    private BufferedWriter writer;
    private File file = new File("output.txt");
    
    public DanceSimulator(int width, int height, Worm[] worms, List<Integer> beats) {
        this.width = width;
        this.height = height;
        this.worms = worms;
        this.beats = beats;

        try {writer = new BufferedWriter(new FileWriter(file.getName(), true));
        } catch (IOException e) {e.printStackTrace();}
    }

    public void simulation() {
        file.delete();

        Iterator<Integer> spotlights = beats.iterator();
        Integer nextStop = spotlights.next();

        for (int i = 0; i <= beats.getLast(); i++) {
            if (i != nextStop) {
                List<Worm> colisionOrder = getColisionOrder();

                if (colisionOrder.isEmpty()) {
                    Worm wormNotOnBoard = wormNotOnBoard();

                    if (wormNotOnBoard != null) {
                        turnWormToBoard(wormNotOnBoard);

                    } else {appendOutput("x");}

                } else {turn(colisionOrder.getFirst());}
                
            } else {
                if (spotlights.hasNext()) {
                    nextStop = spotlights.next();
                }
                appendOutput("x");
            }
        }
        try {writer.close();
        } catch (IOException e) {e.printStackTrace();}
    }

    private List<Worm> getColisionOrder() {
        busy = getBusy();
        List<Worm> colisionOrder = new ArrayList<>(Arrays.asList(new Worm[worms.length]));

        for (Worm w : worms) {
            Pair<Boolean, Integer> colision = getColision(w, w.currentDirection());
            if (colision.getFirst()) {
                colisionOrder.add(colision.getSecound(), w);
            }
        }
        colisionOrder.removeIf(w -> w == null);
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
                moveAndAppend(w, "left");
            } else {moveAndAppend(w, "right");}

        } else if (leftTurn.getFirst()) {
            moveAndAppend(w, "right");

        } else {moveAndAppend(w, "left");}
    }

    private void moveAndAppend(Worm w, String dir) {
        if (dir.equals("left")) {
            w.move("left");
            appendOutput(w.id() + " " + "l");
        } else if (dir.equals("right")) {
            w.move("right");
            appendOutput(w.id() + " " + "r");
        }
    }

    private Worm wormNotOnBoard() {
        Worm w = null;
        for (int i = 0; i < worms.length; i++) {
            Coordinate head = worms[i].head();
            if (head.x() < 0 || head.y() < 0 || head.x() > width-1 || head.y() > height-1) {
                w = worms[i];
            }
        }
        return w;
    }

    private void turnWormToBoard(Worm w) {
        Coordinate head = w.head();
        Coordinate nextHead = head.add(w.currentDirection());
        if (head.x() < 0 || head.y() < 0) {
            if (!(nextHead.x() > head.x() || nextHead.y() > head.y())) {
                moveAndAppend(w, "left");
            }
        } else {
            if (!(nextHead.x() < head.x() || nextHead.y() < head.y())) {
                moveAndAppend(w, "left");
            }
        }
    }

    private void appendOutput(String str) {
        try {
            writer.append(str);
            writer.append("\n");
        } catch (IOException e) {e.printStackTrace();}
    }
}