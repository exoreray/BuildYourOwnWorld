package byow.Core.Input;

import byow.Core.Input.InputSource;
import edu.princeton.cs.introcs.StdDraw;

public class KeyboardInputSource implements InputSource {
    public boolean possibleNextInput() {
        return true;
    }

    public char getNextKey() {
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char c = Character.toLowerCase(StdDraw.nextKeyTyped());
                return c;
            }
        }
    }

}
