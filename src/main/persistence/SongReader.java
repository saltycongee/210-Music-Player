package persistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

//a reader than reads music file data
public class SongReader {

    //EFFECTS: returns an audio stream of a song given a file path
    public static InputStream readSong(String filepath) throws FileNotFoundException {
        InputStream music = new FileInputStream(new File(filepath));
        return music;
    }

}
