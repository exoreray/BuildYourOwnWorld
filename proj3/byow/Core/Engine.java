package byow.Core;

import byow.Core.Input.InputSource;
import byow.Core.Input.KeyboardInputSource;
import byow.Core.Input.StringInputSource;
import byow.Core.Menus.MainMenu;
import byow.Core.SaveAndLoad.GameIO;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;

public class Engine {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 50;
    public static final int HEIGHT = 30;



    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        ter.initialize(WIDTH, HEIGHT);
        MainMenu mainMenu = new MainMenu();
        mainMenu.displayMainMenu();
        InputSource keyboard = new KeyboardInputSource();
        processSource(keyboard, true);
    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     *
     * In other words, both of these calls:
     *   - interactWithInputString("n123sss:q")
     *   - interactWithInputString("lww")
     *
     * should yield the exact same world state as:
     *   - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world

     */
    public TETile[][] interactWithInputString(String input) {
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.
        InputSource stringInput = new StringInputSource(input);
        Game curGame = processSource(stringInput, false);
        if (curGame == null) {
            return null;
        }
        return curGame.worldFrame;
    }

    private Game processSource(InputSource source, boolean activateUI) {
        Game curGame = null;
        GameIO gameIO = new GameIO();
        while (source.possibleNextInput()) {
            char c = source.getNextKey();
            if (c == 'n') {
                String validInput = "";
                if (activateUI) {
                    MainMenu.displayPromptSeed();
                }
                curGame = new Game(WIDTH, HEIGHT, "default", gameIO, ter); //ter should be inside by default
                while (source.possibleNextInput()) {
                    char d = source.getNextKey();
                    if (d == 's') {
                        curGame.initGameEnviron(validInput, activateUI);
                        if (activateUI) {
                            curGame.runGame(activateUI);
                        }
                        break;
                    }
                    validInput += d;
                }
            } else if (c == 'l') {
                curGame = gameIO.loadGame();
                if (curGame == null) {
                    if (activateUI) {
                        System.exit(0);
                    } else {
                        break;
                    }
                } else {
                    if (activateUI) {
                        curGame.runGame(activateUI);
                    }
                }
            }else if (c == 'q' && activateUI) {
                gameIO.saveGame(curGame);
                System.exit(0);
            } else if (c == ':') {
                if (source.possibleNextInput()) {
                    char d = source.getNextKey();
                    if (d == 'q') {
                        gameIO.saveGame(curGame);
                        break;
                    }
                }
            } else if (c == 'w' || c == 'a'
                    || c == 's' || c == 'd') {
                curGame.moveCharacter(c);
            } else if (c == 'f') { //fog mode for ami score

            }
        }
        return curGame;
    }



}
