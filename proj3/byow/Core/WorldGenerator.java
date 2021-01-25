package byow.Core;

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
    private TETile[][] worldFrame ;
    private String[][] grid;


    private class Point implements Serializable {
        private int x;
        private int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }


    private class Room implements Serializable {
        private int height;
        private int length;
        private Point bottomLeft;
        private Point randomPoint;

        Room(Point bottomLeft, int length, int height) {
            this.bottomLeft = bottomLeft;
            this.length = length;
            this.height = height;
        }

        public void setRandomPoint(Random rIn) {
            r = rIn;
            int x = RandomUtils.uniform(r, bottomLeft.x, bottomLeft.x + length);
            int y = RandomUtils.uniform(r, bottomLeft.y, bottomLeft.y + height);
            randomPoint = new Point(x, y);
        }

    }


    WorldGenerator(int width, int height, Random r, TERenderer ter) {
        this.width = width;
        this.height = height;
        this.r = r;
        this.ter = ter;
        this.worldFrame = new TETile[this.width][this.height];
    }
    
    public void generateNewWorld(String input, String[][] grid, boolean activateUI) {
        Room[] rooms;
        this.grid = grid;
        createCanvas(this.grid);
        rooms = generateRooms(this.grid);
        generateHallways(this.grid, rooms);
        generateWalls(this.grid);
        if (activateUI) {
            tileGrid();
            ter.renderFrame(worldFrame);
        }
    }


    private void createCanvas(String[][] grid) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                grid[j][i] = "Nothing";
            }
        }
    }

    private Room[] generateRooms(String[][] grid) {
        int roomsMax = (width * height) / (16 * 5);
        int numberOfRooms = RandomUtils.uniform(r, 2, roomsMax);
        Room[] rooms = new Room[numberOfRooms];
        int roomCanvasXMin = 2, roomCanvasXMax = width - 1 - roomCanvasXMin;
        int roomCanvasYMin = 2, roomCanvasYMax = height - 3 - roomCanvasYMin;
        int roomLengthMin = 3, roomLengthMax = 8;
        Point roomCanvasMax = new Point(roomCanvasXMax, roomCanvasYMax);
        for (int i = 0; i < numberOfRooms; i++) {
            while (true) {
                int bottomLeftX = RandomUtils.uniform(r, roomCanvasXMin, roomCanvasXMax + 1);
                int bottomLeftY = RandomUtils.uniform(r, roomCanvasYMin, roomCanvasYMax + 1);
                int xLength = RandomUtils.uniform(r, roomLengthMin, roomLengthMax + 1);
                int yLength = RandomUtils.uniform(r, roomLengthMin, roomLengthMax + 1);
                Point bottomLeft = new Point(bottomLeftX, bottomLeftY);
                Point lengthXY = new Point(xLength, yLength);
                boolean isValid = isValid(bottomLeft, lengthXY, roomCanvasMax);
                if (isValid) {
                    boolean isCollide = checkCollision(bottomLeft, lengthXY, grid);
                    if (!isCollide) {
                        Room newRoom = new Room(new Point(bottomLeftX, bottomLeftY),
                                xLength, yLength);
                        markRoom(newRoom, grid);
                        newRoom.setRandomPoint(r);
                        rooms[i] = newRoom;
                        break;
                    }
                }
            }
        }
        return rooms;
    }

    private boolean isValid(Point bottomLeft, Point lengthXY, Point roomCanvasMax) {
        return (bottomLeft.x + lengthXY.x - 1) <= roomCanvasMax.x
                && (bottomLeft.y + lengthXY.y - 1) <= roomCanvasMax.y;
    }

    private boolean checkCollision(Point bottomLeft, Point lengthXY, String[][] grid) {
        for (int i = bottomLeft.x - 2; i <= bottomLeft.x + lengthXY.x; i++) {
            for (int j = bottomLeft.y - 2; j <= bottomLeft.y + lengthXY.y; j++) {
                if (grid[j][i].equals("Floor")) {
                    return true;
                }
            }
        }
        return false;
    }

    private void markRoom(Room room, String[][] grid) {
        for (int i = room.bottomLeft.x; i < room.bottomLeft.x + room.length; i++) {
            for (int j = room.bottomLeft.y; j < room.bottomLeft.y + room.height; j++) {
                grid[j][i] = "Floor";
            }
        }
    }

    private void generateHallways(String[][] grid, Room[] rooms) {
        for (int i = 0, j = 1; j < rooms.length; i++, j++) {
            Point start = rooms[i].randomPoint;
            Point end = rooms[j].randomPoint;
            int yStart = start.y, yEnd = end.y, xStart = start.x, xEnd = end.x;
            if (start.y <= end.y) {
                for (int a = yStart; a <= yEnd; a++) {
                    grid[a][xStart] = "Floor";
                }
            } else {
                for (int a = yStart; a >= yEnd; a--) {
                    grid[a][xStart] = "Floor";
                }
            }
            if (start.x <= end.x) {
                for (int b = xStart; b <= xEnd; b++) {
                    grid[yEnd][b] = "Floor";
                }
            } else {
                for (int b = xStart; b >= xEnd; b--) {
                    grid[yEnd][b] = "Floor";
                }
            }


        }
    }

    private void generateWalls(String[][] grid) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (grid[j][i].equals("Floor")) {
                    generateWallsHelper(i, j, grid);
                }
            }
        }
    }

    private void generateWallsHelper(int i, int j, String[][] grid) {
        for (int a = i - 1; a <= i + 1; a++) {
            for (int b = j - 1; b <= j + 1; b++) {
                if (grid[b][a].equals("Nothing")) {
                    grid[b][a] = "Wall";
                }
            }
        }
    }

    private void tileGrid() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (grid[j][i].equals("Nothing")) {
                    worldFrame[i][j] = Tileset.NOTHING;
                } else if (grid[j][i].equals("Floor")) {
                    worldFrame[i][j] = Tileset.FLOOR;
                } else if (grid[j][i].equals("Wall")) {
                    worldFrame[i][j] = Tileset.WALL;
                } else if (grid[j][i].equals("Biden")) {
                    worldFrame[i][j] = Tileset.AVATAR;
                }
            }
        }
    }



}
