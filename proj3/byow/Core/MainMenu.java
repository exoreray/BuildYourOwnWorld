package byow.Core;

import edu.princeton.cs.introcs.StdDraw;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MainMenu {
    private final String[] menu = {"CS61B: THE ROOM", "New Game (N)", "Load Game (L)", "Quit (Q)", "Fog Mode (F)"};
    public static final int WIDTH = 50;
    public static final int HEIGHT = 30;
    private static Font lionKing25;
    private static Font lionKing35;
    private static Color neonYellow, neonBlue;

    public MainMenu() {
        try {
            lionKing25 = Font.createFont(Font.TRUETYPE_FONT, new File("byow/Core/lion_king.ttf"))
                    .deriveFont(Font.BOLD, 25);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(lionKing25);
            lionKing35 = Font.createFont(Font.TRUETYPE_FONT, new File("byow/Core/lion_king.ttf"))
                    .deriveFont(Font.BOLD, 35);
            ge.registerFont(lionKing35);
        } catch (IOException e) {
            e.printStackTrace();
        } catch(FontFormatException e) {
            e.printStackTrace();
        }
        neonYellow = new Color(241, 241, 59);
        neonBlue = new Color(15,219,226);
    }

    public void displayMainMenu() {
        StdDraw.setFont(lionKing35);
        StdDraw.clear(neonYellow);
        StdDraw.setPenColor(neonBlue);
        StdDraw.text(WIDTH / 2, HEIGHT * 4 / 5, menu[0]);
        StdDraw.setFont(lionKing25);
        StdDraw.text(WIDTH / 2, HEIGHT * 4 / 8, menu[1]);
        StdDraw.text(WIDTH / 2, HEIGHT * 3 / 8, menu[2]);
        StdDraw.text(WIDTH / 2, HEIGHT * 2 / 8, menu[3]);
        StdDraw.text(WIDTH / 2, HEIGHT * 1 / 8, menu[4]);
        StdDraw.show();
    }

    public static void displayPromptSeed() {
        StdDraw.setFont(lionKing25);
        StdDraw.clear(neonYellow);
        StdDraw.setPenColor(neonBlue);
        StdDraw.text(WIDTH / 2, HEIGHT / 2, "Please enter a positive seed, press S to complete");
        StdDraw.show();
    }
}

    
