package utils;

import org.powerbot.script.Tile;

public class Methods {
	public static Tile[] reverse(Tile[] tiles) {
        Tile[] reversed = new Tile[tiles.length];
        for(int i = 0; i < tiles.length; i++) {
        	reversed[i] = tiles[tiles.length-(1+i)];
        }
        return reversed;
    }
}
