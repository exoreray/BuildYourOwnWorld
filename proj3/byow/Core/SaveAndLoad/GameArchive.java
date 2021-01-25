package byow.Core.SaveAndLoad;

import byow.Core.Game;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class GameArchive implements Serializable {
    private Map<String, Game> archive;

    public GameArchive() {
        archive = new HashMap<>();
    }

    public void addToArchive(Game game) {
        archive.put(game.getName(), game);
    }

    public Game loadFromArchive() {
        return archive.get("default");
    }

}
