package byow.Core.World;

import byow.Core.RandomUtils;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.io.Serializable;
import java.util.Random;

public class WorldGenerator implements Serializable {
    private int width;
    private int height;
    private Random r;
    private TERenderer ter;
    public TETile[][] worldFrame;
    private Room[] rooms;
    private int roomsMin = 2, roomsMax;
    private int numberOfRooms;
    private int roomCanvasXMin = 2, roomCanvasXMax;
    private int roomCanvasYMin = 2, roomCanvasYMax;
    private final int roomLengthMin = 3, roomLengthMax = 8;
    public Person defaultCharacter;


    public WorldGenerator(int width, int height, Random r) {
        this.width = width;
        this.height = height;
        this.r = r;
        this.worldFrame = new TETile[this.width][this.height];
        this.roomsMax = (this.width * this.height) / (16 * 5);
        this.roomCanvasXMax = this.width - 1 - this.roomCanvasXMin;
        this.roomCanvasYMax = this.height - 3 - this.roomCanvasYMin;
    }
    public void generateNewWorld() {
        createCanvas();
        generateRooms();
        generateHallways();
        generateWalls();
        generateDefaultCharacter();
    }

    private void createCanvas() {
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                this.worldFrame[i][j] = Tileset.NOTHING;
            }
        }
    }

    private void generateRooms() {
        this.numberOfRooms = RandomUtils.uniform(this.r, this.roomsMin, this.roomsMax);
        this.rooms = new Room[this.numberOfRooms];
        Point roomCanvasMax = new Point(this.roomCanvasXMax, this.roomCanvasYMax);
        for (int i = 0; i < this.numberOfRooms; i++) {
            while (true) {
                int bottomLeftX = RandomUtils.uniform(this.r, this.roomCanvasXMin, this.roomCanvasXMax + 1);
                int bottomLeftY = RandomUtils.uniform(this.r, this.roomCanvasYMin, this.roomCanvasYMax + 1);
                int xLength = RandomUtils.uniform(this.r, this.roomLengthMin, this.roomLengthMax + 1);
                int yLength = RandomUtils.uniform(this.r, this.roomLengthMin, this.roomLengthMax + 1);
                Point bottomLeft = new Point(bottomLeftX, bottomLeftY);
                Point lengthXY = new Point(xLength, yLength);
                boolean isValid = isValid(bottomLeft, lengthXY, roomCanvasMax);
                if (isValid) {
                    boolean isCollide = checkCollision(bottomLeft, lengthXY);
                    if (!isCollide) {
                        Room newRoom = new Room(new Point(bottomLeftX, bottomLeftY),
                                xLength, yLength);
                        tileRoomFloor(newRoom);
                        newRoom.setRandomPoint(this.r);
                        this.rooms[i] = newRoom;
                        break;
                    }
                }
            }
        }
    }

    private boolean isValid(Point bottomLeft, Point lengthXY, Point roomCanvasMax) {
        return (bottomLeft.getX() + lengthXY.getX() - 1) <= roomCanvasMax.getX()
                && (bottomLeft.getY() + lengthXY.getY() - 1) <= roomCanvasMax.getY();
    }

    private boolean checkCollision(Point bottomLeft, Point lengthXY) {
        for (int i = bottomLeft.getX() - 2; i <= bottomLeft.getX() + lengthXY.getX(); i++) {
            for (int j = bottomLeft.getY() - 2; j <= bottomLeft.getY() + lengthXY.getY(); j++) {
                if (this.worldFrame[i][j].description().equals("floor")) {
                    return true;
                }
            }
        }
        return false;
    }

    private void tileRoomFloor(Room room) {
        for (int i = room.getBottomLeft().getX(); i < room.getBottomLeft().getX() + room.getLength(); i++) {
            for (int j = room.getBottomLeft().getY(); j < room.getBottomLeft().getY() + room.getHeight(); j++) {
                this.worldFrame[i][j] = Tileset.FLOOR;
            }
        }
    }

    private void generateHallways() {
        for (int i = 0, j = 1; j < this.rooms.length; i++, j++) {
            Point start = this.rooms[i].getRandomPoint();
            Point end = this.rooms[j].getRandomPoint();
            int yStart = start.getY(), yEnd = end.getY(), xStart = start.getX(), xEnd = end.getX();
            if (start.getY() <= end.getY()) {
                for (int a = yStart; a <= yEnd; a++) {
                    this.worldFrame[xStart][a] = Tileset.FLOOR;
                }
            } else {
                for (int a = yStart; a >= yEnd; a--) {
                    this.worldFrame[xStart][a] = Tileset.FLOOR;
                }
            }
            if (start.getX() <= end.getX()) {
                for (int b = xStart; b <= xEnd; b++) {
                    this.worldFrame[b][yEnd] = Tileset.FLOOR;
                }
            } else {
                for (int b = xStart; b >= xEnd; b--) {
                    this.worldFrame[b][yEnd] = Tileset.FLOOR;
                }
            }


        }
    }

    private void generateWalls() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (this.worldFrame[i][j].description().equals("floor")) {
                    generateWallsHelper(i, j);
                }
            }
        }
    }

    private void generateWallsHelper(int i, int j) {
        for (int a = i - 1; a <= i + 1; a++) {
            for (int b = j - 1; b <= j + 1; b++) {
                if (this.worldFrame[a][b].description().equals("nothing")) {
                    this.worldFrame[a][b] = Tileset.WALL;
                }
            }
        }
    }

    private void generateDefaultCharacter() {
        this.defaultCharacter = new Person("avatar", Tileset.AVATAR, this.r);
        this.defaultCharacter.appearRandomLocation(this.worldFrame);
    }



}
