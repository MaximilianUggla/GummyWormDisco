package GummyWormDisco;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Simulation {

/* 
5 5
2 2 4
2
R R B R B
B R B R R
B B R B R
R B B R B
B R R B B
0,1 0,0 1,0 1,1 2,1 3,1
1,3 1,4 2,4 2,5 2,6 2,7
*/    

    public static void main(String[] args) throws IOException {
        int width, height, spotlights, nbrOfWorms;
        String[] beats;
        System.out.println("Give info (dubble press enter):");

        List<String[]> information = new ArrayList<>();

        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        

        while (true) {
            String line = read.readLine();
            if (line.isBlank()) {
                break;
            }
            information.add(line.split(" "));
        }

        String[] widthAndHeight = information.get(0);
        String[] spotlightsAndbeats = information.get(1);
        
        width = Integer.parseInt(widthAndHeight[0]);
        height = Integer.parseInt(widthAndHeight[1]);
        spotlights = Integer.parseInt(spotlightsAndbeats[0]);
        beats = Arrays.copyOfRange(spotlightsAndbeats, 1, spotlightsAndbeats.length-1);
        nbrOfWorms = Integer.parseInt(information.get(2)[0]);

        System.out.println(width);
        System.out.println(height);
        System.out.println(spotlights);
        for (String beat : beats) {              // beats is not correctly created
            System.out.println(beat);
        }
        System.out.println(nbrOfWorms);

        String[][] board = new String[height][];
        for (int i = 3; i < height+3; i++) {
            board[i-3] = information.get(i);
        }

        Worm[] worms = new Worm[nbrOfWorms];
        for (int i = height+3; i < nbrOfWorms+height+3; i++) {
            worms[i-height-3] = new Worm(information.get(i));
        }
        
        for (String[] strArray : board) {
            for (String str : strArray) {
                System.out.print(str);
            }
            System.out.println();
        }

        for (Worm w : worms) {
            System.out.println(w);
        }

    }

}