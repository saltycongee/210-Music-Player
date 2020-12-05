package persistence;

import exceptions.SongCreationException;
import model.Playlist;
import model.Song;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//ADAPTED FROM TELLERAPP
//a reader that reads playlist data from a text file
public class PlaylistReader {
    public static final String DELIMITER = ",";

    // EFFECTS: returns a playlist parsed from file; throws
    // IOException if an exception is raised when opening / reading from file
    public static Playlist readPlaylist(File file) throws IOException, SongCreationException {
        List<String> fileContent = readFile(file);
        return parseContent(fileContent);
    }

    // EFFECTS: returns content of file as a list of strings, each string
    // containing the content of one row of the file
    private static List<String> readFile(File file) throws IOException {
        return Files.readAllLines(file.toPath());
    }

    // EFFECTS: returns a list of accounts parsed from list of strings
    // where each string contains data for one song
    private static Playlist parseContent(List<String> fileContent) throws SongCreationException {
        Playlist playlist = new Playlist();

        for (String line : fileContent) {
            ArrayList<String> lineComponents = splitString(line);
            playlist.addToPlaylist(parseSong(lineComponents));
        }
        return playlist;
    }

    // EFFECTS: returns a list of strings obtained by splitting line on DELIMITER
    private static ArrayList<String> splitString(String line) {
        String[] splits = line.split(DELIMITER);
        return new ArrayList<>(Arrays.asList(splits));
    }

    // REQUIRES: components has size 3 where element 0 represents the
    // song name,
    // element 1 represents the name of the artist and element 2 represents
    // whether the song is over.
    // EFFECTS: returns a Song constructed from components
    private static Song parseSong(List<String> components)  throws SongCreationException {
        String songName = components.get(0);
        String artist = components.get(1);
        String isSongOver = components.get(2);
        return new Song(songName, artist, isSongOver);
    }
}
