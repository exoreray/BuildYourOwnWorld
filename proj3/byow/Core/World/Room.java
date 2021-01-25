package byow.Core.World;

import byow.Core.RandomUtils;

import java.io.Serializable;
import java.util.Random;

public class Room implements Serializable {
    private int height;
    private int length;
    private Point bottomLeft;
    private Point randomPoint;
    private Random r;


    Room(Point bottomLeft, int length, int height) {
        this.bottomLeft = bottomLeft;
        this.length = length;
        this.height = height;
    }

    public Point getBottomLeft() {
        return new Point(this.bottomLeft.getX(), this.bottomLeft.getY());
    }

    public int getLength() {
        return this.length;
    }

    public int getHeight() {
        return this.height;
    }

    public void setRandomPoint(Random r) {
        this.r = r;
        int x = RandomUtils.uniform(r, this.bottomLeft.getX(), this.bottomLeft.getX() + length);
        int y = RandomUtils.uniform(r, this.bottomLeft.getY(), this.bottomLeft.getY() + height);
        this.randomPoint = new Point(x, y);
    }

    public Point getRandomPoint() {
        return new Point(this.randomPoint.getX(), this.randomPoint.getY());
    }


}
