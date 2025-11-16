package GummyWormDisco;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
                Worm turned = null;
                Worm firstToColide = firstToColide();

                if (firstToColide == null) {
                    Worm wormNotOnBoard = wormNotOnBoard();

                    if (wormNotOnBoard != null) {
                        turnWormToBoard(wormNotOnBoard);
                        turned = wormNotOnBoard;

                    } else {appendOutput("x");}

                } else {
                    turn(firstToColide);
                    turned = firstToColide;
                }

                for (Worm w : worms) {
                    if (w != turned) {
                        w.shiftCoordinates(w.currentDirection());
                    }
                    System.out.println(w);
                }
                
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
        Pair<Worm, Integer> first = colisionOrder.poll();
        System.out.println(colisionOrder);
        if (first != null) {
            return first._1();
        } else {return null;}
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
        Coordinate leftDirection = leftTurnDirection(w.currentDirection());
        Coordinate rightDirection = rightTurnDirection(w.currentDirection());
        Pair<Boolean, Integer> leftTurn = getColision(w, leftDirection);
        Pair<Boolean, Integer> rightTurn = getColision(w, rightDirection);

        if (leftTurn._1() && rightTurn._1()) {
            if (leftTurn._2() < rightTurn._2()) {
                turnAndAppend(w, leftDirection, "l");
            } else {turnAndAppend(w, rightDirection, "r");}

        } else if (leftTurn._1()) {
            turnAndAppend(w, rightDirection, "r");

        } else {turnAndAppend(w, leftDirection, "l");}
    }

    private Coordinate rightTurnDirection(Coordinate currDir) {
        Coordinate leftToRight = new Coordinate(1, 0);
        Coordinate rightToLeft = new Coordinate(-1, 0);
        Coordinate topToDown = new Coordinate(0, 1);
        Coordinate downTotop = new Coordinate(0, -1);

        if (currDir == leftToRight) {
            return topToDown;
        } else if (currDir == rightToLeft) {
            return downTotop;
        } else if (currDir == topToDown) {
            return rightToLeft;
        } else {return leftToRight;}
    }

    private Coordinate leftTurnDirection(Coordinate currDir) {        
        Coordinate leftToRight = new Coordinate(1, 0);
        Coordinate rightToLeft = new Coordinate(-1, 0);
        Coordinate topToDown = new Coordinate(0, 1);
        Coordinate downTotop = new Coordinate(0, -1);

        if (currDir == leftToRight) {
            return downTotop;
        } else if (currDir == rightToLeft) {
            return topToDown;
        } else if (currDir == topToDown) {
            return leftToRight;
        } else {return rightToLeft;}
    }

    private void turnAndAppend(Worm w, Coordinate dir, String turn) {
        w.shiftCoordinates(dir);
        appendOutput(w.id() + " " + turn);
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
        Coordinate currDir = w.currentDirection();
        Coordinate nextHead = head.add(currDir);
        if (head.x() < 0 || head.y() < 0) {
            if (!(nextHead.x() > head.x() || nextHead.y() > head.y())) {
                turnAndAppend(w, leftTurnDirection(currDir), "l");
            }
        } else {
            if (!(nextHead.x() < head.x() || nextHead.y() < head.y())) {
                turnAndAppend(w, leftTurnDirection(currDir), "l");
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