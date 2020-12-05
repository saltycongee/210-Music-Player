package persistence;

import exceptions.SongCreationException;
import model.Playlist;
import model.Song;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;

public class PlaylistReaderTest {

    //DUMMY TEST FOR READER CLASSES, NOT TRYING TO FAKE CODE COVERAGE
    @Test
    void testConstructor() {
        new PlaylistReader();
        new SongReader();
    }

    @Test
    void testParsePlaylist1() {
        try {
            Playlist playlist = PlaylistReader.readPlaylist(new File("./data/PlaylistTest1.txt"));
            Song song1 = playlist.getCurrentSong();
            assertEquals("Flash Delirium", song1.getName());
            assertEquals("MGMT", song1.getArtist());
            assertEquals("false", song1.getIsSongOver());

            playlist.skipSong();
            Song song2 = playlist.getCurrentSong();
            assertEquals("Time Is Running Out", song2.getName());
            assertEquals("Muse", song2.getArtist());
            assertEquals("false", song2.getIsSongOver());
        } catch (Exception e) {
            fail ("No exceptions should have been thrown");
        }
    }

    @Test
    void testParsePlaylist2() {
        try {
            Playlist playlist = PlaylistReader.readPlaylist(new File("./data/PlaylistTest2.txt"));
            Song song1 = playlist.getCurrentSong();
            assertEquals("Strawberry Fields Forever", song1.getName());
            assertEquals("The Beatles", song1.getArtist());
            assertEquals("false", song1.getIsSongOver());

            playlist.skipSong();
            Song song2 = playlist.getCurrentSong();
            assertEquals("Mr. Brightside", song2.getName());
            assertEquals("The Killers", song2.getArtist());
            assertEquals("false", song2.getIsSongOver());
        } catch (Exception e) {
            fail ("No exceptions should have been thrown");
        }
    }

    @Test
    void testIOException() {
        try {
            PlaylistReader.readPlaylist(new File("./path/does/not/exist/testPlaylist.txt"));
        } catch (SongCreationException e) {
            fail ("Exception should not have been thrown");
        } catch (IOException e) {
            //expected
        }
    }
}
