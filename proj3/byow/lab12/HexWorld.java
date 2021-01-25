package byow.lab12;
import org.junit.Test;
import static org.junit.Assert.*;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    public void addHexagon(int size) {
        String[] base = new String[size];
        fillBase(base);
        for (int i = 0; i < size; i++) {
            System.out.println(base[i]);
        }
        for (int j = size - 1; j > -1; j--) {
            System.out.println(base[j]);
        }
    }

    public void fillBase(String[] base) {

    }

}
