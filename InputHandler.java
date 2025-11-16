package GummyWormDisco;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class InputHandler {

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
        int height, width, nbrOfWorms;

        // System.out.println("--- --- --- --- --- --- --- --- --- --- --- --- --- ---");
        // System.out.println("Either paste all input lines at once or one at a time,\nthen on the final line press enter twice to run the program:");
        // System.out.println("--- --- --- --- --- --- --- --- --- --- --- --- --- ---");

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
        width = Integer.parseInt(widthAndHeight[0]);         
        height = Integer.parseInt(widthAndHeight[1]);


        List<Integer> beats = new ArrayList<>();
        String[] spotlightsAndbeats = information.get(1);
        for (String str : spotlightsAndbeats) {
            beats.add(Integer.parseInt(str));
        }
        beats.removeFirst();


        nbrOfWorms = Integer.parseInt(information.get(2)[0]);           

        String[][] board = new String[height][];                        
        for (int i = 3; i < height+3; i++) {
            board[i-3] = information.get(i);
        }

        Worm[] worms = new Worm[nbrOfWorms];                            
        for (int i = height+3; i < nbrOfWorms+height+3; i++) {
            worms[i-height-3] = new Worm(information.get(i), i-height-3);
        }

        DanceSimulator ds = new DanceSimulator(board, width, height, worms, beats);
        ds.simulation();
    }
}