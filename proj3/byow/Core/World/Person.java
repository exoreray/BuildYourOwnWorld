package byow.Core.World;

import byow.Core.RandomUtils;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.io.Serializable;
import java.util.*;

public class Person implements Serializable {
    private String name;
    private TETile skin;
    private Random r;
    private Point appearLoc;
    

    Person(String name, TETile skin, Random r) {
        this.name = name;
        this.skin = skin;
        this.r = r;
    }

    public void appearRandomLocation(TETile[][] worldFrame) {
        while (true) {
            int x = RandomUtils.uniform(this.r, 0, worldFrame.length);
            int y = RandomUtils.uniform(this.r, 0, worldFrame[0].length);
            if (worldFrame[x][y].description().equals("floor")) {
                worldFrame[x][y] = this.skin;
                this.appearLoc = new Point(x, y);
                break;
            }
        }
    }
    public void movement(char move, TETile[][] worldFrame) {
        int nextX = this.appearLoc.getX();
        int nextY = this.appearLoc.getY();
        if (move == 'w') {
            nextY++;
        } else if (move == 's') {
            nextY--;
        } else if (move == 'a') {
            nextX--;
        } else if (move == 'd') {
            nextX++;
        }
        if (worldFrame[nextX][nextY].description().equals("floor")) {
            worldFrame[this.appearLoc.getX()][this.appearLoc.getY()] = Tileset.FLOOR;
            worldFrame[nextX][nextY] = this.skin;
            this.appearLoc.setX(nextX);
            this.appearLoc.setY(nextY);
        }
    }



}
