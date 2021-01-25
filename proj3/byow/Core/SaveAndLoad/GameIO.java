package byow.Core.SaveAndLoad;

import byow.Core.Game;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Paths;

public class GameIO implements Serializable {
    public GameArchive gameArchive;
    public File archiveFile;

    public GameIO () {
        this.archiveFile = Paths.get("byow", "Core", "Archive", "archiveFile.txt").toFile();
        if (this.archiveFile.exists()) {
            this.gameArchive = IOUtils.readObject(archiveFile);
        } else {
            this.gameArchive = new GameArchive();
            try {
                this.archiveFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Game loadGame() {
        if (this.gameArchive == null) { //this situation only happens when user force quit.
            return null;
        }
        Game result = this.gameArchive.loadFromArchive();
        if (result == null) {
            IOUtils.writeObject(this.archiveFile, this.gameArchive);
        }
        return result;

    }

    public void saveGame(Game game) {
        if (game != null) {
            this.gameArchive.addToArchive(game);
        }
        IOUtils.writeObject(this.archiveFile, this.gameArchive);
    }
}
