package byow.Core.Input;

import byow.Core.Input.InputSource;

public class StringInputSource implements InputSource {
    private String input;
    private int index;

    public StringInputSource(String s) {
        index = 0;
        input = s;
    }

    public boolean possibleNextInput() {
        return index < input.length();
    }

    public char getNextKey() {
        char returnChar = input.charAt(index);
        index += 1;
        return Character.toLowerCase(returnChar);
    }


}
