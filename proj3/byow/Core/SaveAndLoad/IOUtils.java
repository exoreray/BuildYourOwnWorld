package byow.Core.SaveAndLoad;


import byow.Core.SaveAndLoad.GameArchive;

import java.io.*;

public class IOUtils {
    public static GameArchive readObject(File archiverFile) {
        InputStream fileIs = null;
        ObjectInputStream objIs = null;
        GameArchive result = null;
        try {
            fileIs = new FileInputStream(archiverFile);
            objIs = new ObjectInputStream(fileIs);
            result = (GameArchive) objIs.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (EOFException e) { // This is used for the situation of loading game after the user force quit and no record

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (objIs != null) objIs.close();
            } catch (Exception ex) {

            }
        }
        return result;
    }

    public static void writeObject(File archiveFile, GameArchive gameArchive) {
        OutputStream ops = null;
        ObjectOutputStream objOps = null;
        try {
            ops = new FileOutputStream(archiveFile);
            objOps = new ObjectOutputStream(ops);
            objOps.writeObject(gameArchive);
            objOps.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            try{
                if(objOps != null) objOps.close();
            } catch (Exception ex){

            }
        }

    }
}
