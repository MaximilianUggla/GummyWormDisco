package GummyWormDisco;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

        List<String[]> information = new ArrayList<>();

        Scanner sc = new Scanner(System.in);
        
        String[] widthAndHeight = sc.nextLine().split(" ");
        width = Integer.parseInt(widthAndHeight[0]);         
        height = Integer.parseInt(widthAndHeight[1]);

        List<Integer> beats = new ArrayList<>();
        String[] spotlightsAndbeats = sc.nextLine().split(" ");
        for (String str : spotlightsAndbeats) {
            beats.add(Integer.parseInt(str));
        }
        beats.removeFirst();

        nbrOfWorms = Integer.parseInt(sc.nextLine());           

        for (int i = 0; i < height+nbrOfWorms; i++) {
            String line = sc.nextLine();
            information.add(line.split(" "));
        }
        sc.close();


        Worm[] worms = new Worm[nbrOfWorms];
        for (int i = 0; i < nbrOfWorms; i++) {
            worms[i] = new Worm(information.get(i+height), i);
        }

        DanceSimulator ds = new DanceSimulator(width, height, worms, beats);
        ds.simulation();
    }
}