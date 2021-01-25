package byow.Core;

import byow.TileEngine.TETile;

import java.io.Serializable;
import java.util.*;

public class Person implements Serializable {
    private Point coordinate;
    private TETile skin;
    private Random r;
    private String name;

    private class Point implements Serializable {
        private int x;
        private int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    Person(String name, Random r, TETile skin) {
        this.name = name;
        this.r = r;
        this.skin = skin;
    }

    public void randomGenerate(String[][] dynamicGrid) {
        while (true) {
            int x = RandomUtils.uniform(r, 0, dynamicGrid[0].length);
            int y = RandomUtils.uniform(r, 0, dynamicGrid.length);
            if (dynamicGrid[y][x].equals("Floor")) {
                dynamicGrid[y][x] = name;
                coordinate = new Point(x, y);
                break;
            }
        }
    }

    public void movement(String move, String[][] dynamicGrid) {
        int nextX = coordinate.x;
        int nextY = coordinate.y;
        if (move.equals("up")) {
            nextY++;
        } else if (move.equals("down")) {
            nextY--;
        } else if (move.equals("left")) {
            nextX--;
        } else if (move.equals("right")) {
            nextX++;
        }
        if (dynamicGrid[nextY][nextX].equals("Floor")) {
            dynamicGrid[coordinate.y][coordinate.x] = "Floor";
            dynamicGrid[nextY][nextX] = name;
            coordinate.x = nextX;
            coordinate.y = nextY;
        }
    }



}
