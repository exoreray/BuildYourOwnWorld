package byow.Core;

import byow.Core.SaveAndLoad.GameIO;
import byow.Core.World.Person;
import byow.Core.World.WorldGenerator;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.*;
import java.util.Random;

public class Game implements Serializable {
    private int width, height;
    private Random r;
    private TERenderer ter;
    public TETile[][] worldFrame;
    private WorldGenerator worldGenerator;
    private boolean activateUI;
    private Person defaultCharacter;
    private String name = "default";
    private GameIO gameIO;


    Game(int width, int height, String name, GameIO gameIO, TERenderer ter) {
        this.gameIO = gameIO;
        this.width = width;
        this.height = height;
        this.ter = ter;
        this.worldFrame = new TETile[this.width][this.height];
    }

    public void initGameEnviron(String input, boolean activateUI) {
        long seed = Long.parseLong(input);
        r = new Random(seed);
        this.activateUI = activateUI;
        this.worldGenerator = new WorldGenerator(this.width, this.height, this.r);
        this.worldGenerator.generateNewWorld();
        this.defaultCharacter = this.worldGenerator.defaultCharacter;
        this.worldFrame = this.worldGenerator.worldFrame;
    }

    public void runGame(boolean activateUI) {
        this.activateUI = activateUI;
        this.ter.initialize(this.width, this.height);
        this.ter.renderFrame(this.worldFrame);
        if (this.activateUI) {
            int newX = (int) StdDraw.mouseX();
            int newY = (int) StdDraw.mouseY();
            while (true) {
                HUD(newX, newY);
                newX = (int) StdDraw.mouseX();
                newY = (int) StdDraw.mouseY();
                if (StdDraw.hasNextKeyTyped()) {
                    char c = Character.toLowerCase(StdDraw.nextKeyTyped());
                    moveCharacter(c, this.defaultCharacter);
                }
            }
        }
    }

    private void HUD(int newX, int newY) {
        if ((int) StdDraw.mouseX() != newX || (int) StdDraw.mouseY() != newY) {
            this.ter.renderFrame(this.worldFrame);
            try {
                Font font = new Font("Monaco", Font.BOLD, 15);
                StdDraw.setFont(font);
                StdDraw.setPenColor(Color.WHITE);
                StdDraw.textLeft(this.width / 20, this.height - 2,
                        this.worldFrame[(int) StdDraw.mouseX()][(int) StdDraw.mouseY()].description());
            } catch (ArrayIndexOutOfBoundsException e) {

            }
            StdDraw.show();
        }
    }

    public void moveCharacter(char c, Person current) {
        if (c == 'w' || c == 'a' || c == 's' || c == 'd') {
            current.movement(c, this.worldFrame);
        } else if (c == 'q') {
            this.gameIO.saveGame(this);
            System.exit(0);
        }
        if (this.activateUI) {
            ter.renderFrame(this.worldFrame);
        }
    }

    public void moveCharacter(char c) {
        moveCharacter(c, this.defaultCharacter);
    }

    public String getName() {
        return name;
    }


}
