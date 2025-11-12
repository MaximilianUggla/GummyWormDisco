package GummyWormDisco;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Simulation {

    public static void main(String[] args) throws IOException {
        int width, height, spotlights, nbrOfWorms;
        String[] beats;
        
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        String[] information = r.readLine().split("\n");


        String[] widthAndHeight = information[0].split(" ");
        String[] spotlightsAndbeats = information[1].split(" ");
        
        width = Integer.parseInt(widthAndHeight[0]);
        height = Integer.parseInt(widthAndHeight[2]);
        spotlights = Integer.parseInt(spotlightsAndbeats[0]);
        beats = Arrays.copyOfRange(spotlightsAndbeats, 1, spotlightsAndbeats.length-1);
        nbrOfWorms = Integer.parseInt(information[2]);


        String[][] board = new String[height][];
        for (int i = 3; i < height+3; i++) {
            board[i] = information[i].split(" ");
        }

        Worm[] worms = new Worm[nbrOfWorms];
        for (int i = height+4; i < nbrOfWorms+height+4; i++) {
            worms[i] = new Worm(information[i].split(" "));
        }

    }

}