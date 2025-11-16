package GummyWormDisco;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

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
                Worm firstToColide = firstToColide();

                if (firstToColide == null) {
                    Worm wormNotOnBoard = wormNotOnBoard();

                    if (wormNotOnBoard != null) {
                        turnWormToBoard(wormNotOnBoard);

                    } else {appendOutput("x");}

                } else {turn(firstToColide);}
                
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

    private Worm firstToColide() {
        busy = getBusy();
        Queue<Pair<Worm, Integer>> colisionOrder = new PriorityQueue<>((p1, p2) -> p2._2() - p1._2());

        for (Worm w : worms) {
            Pair<Boolean, Integer> colision = getColision(w, w.currentDirection());
            if (colision._1()) {
                colisionOrder.offer(new Pair<Worm,Integer>(w, colision._2()));
            }
        }
        return colisionOrder.poll()._1();
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