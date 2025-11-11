package GummyWormDisco;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Simulation {

    public static void main(String[] args) throws IOException {
        int width, height, spotlights, nbrOfWorms;
        String[] beats;
        
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        String[] widthAndHeight = r.readLine().split(" ");
        String spotlightsAndbeats = r.readLine();
        
        width = Integer.parseInt(widthAndHeight[0]);
        height = Integer.parseInt(widthAndHeight[2]);
        spotlights = Integer.parseInt(String.valueOf(spotlightsAndbeats.charAt(0)));
        beats = spotlightsAndbeats.substring(2).split(" ");
        nbrOfWorms = Integer.parseInt(r.readLine());

        String[][] board = new String[height][];
        for (int i = 0; i < height; i++) {
            board[i] = r.readLine().split(" ");
        }

        Worm[] worms = new Worm[nbrOfWorms];
        for (int i = 0; i < nbrOfWorms; i++) {
            worms[i] = new Worm(r.readLine().split(" "));
        }

    }

}