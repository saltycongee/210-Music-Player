package model;

import exceptions.SongCreationException;
import persistence.PlaylistReader;
import persistence.Saveable;

import java.io.PrintWriter;

//Represents a song, with a name, duration, artist, and whether the song is over or not
public class Song implements Saveable {
    private String name;
    private String artist;
    private String isSongOver;


    //EFFECTS: sets name and artist to given values, given that name and artist and not empty strings.
    // Also sets isSongOver to "true" or "false". Any other strings are invalid
    public Song(String songName, String songArtist, String isSongOver) throws SongCreationException {
        if (songName.length() < 1 || songArtist.length() < 1 || !isSongOver.equals("true")
                && !isSongOver.equals("false")) {
            throw new SongCreationException();
        }
        this.name = songName;
        this.artist = songArtist;
        this.isSongOver = isSongOver;
    }

    public String getName() {
        return name;
    }

    public String getArtist() {
        return artist;
    }

    public String getIsSongOver() {
        return isSongOver;
    }

    public void setIsSongOver(String isSongOver) {
        this.isSongOver = isSongOver;
    }

    //EFFECTS: if the song is over, return true, false otherwise.
    public boolean checkSongOver() {
        return isSongOver.equals("true");
    }

    //EFFECTS: writes each instance of a song with proper formatting
    @Override
    public void save(PrintWriter printWriter) {
        printWriter.print(name);
        printWriter.print(PlaylistReader.DELIMITER);
        printWriter.print(artist);
        printWriter.print(PlaylistReader.DELIMITER);
        printWriter.print(isSongOver);
        printWriter.print("\n");
    }
}
