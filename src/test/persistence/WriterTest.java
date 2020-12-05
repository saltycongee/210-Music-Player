package persistence;

import exceptions.SongCreationException;
import model.Playlist;
import model.Song;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class WriterTest {
    private static final String TEST_FILE = "./data/PlaylistWriteTest.txt";
    private Writer testWriter;
    private Song song1;
    private Song song2;

    @BeforeEach
    void runBefore() throws FileNotFoundException, UnsupportedEncodingException, SongCreationException {
        testWriter = new Writer(new File(TEST_FILE));
        song1 = new Song("Flash Delirium", "MGMT", "true");
        song2 = new Song("Time Is Running Out", "Muse", "true");
    }

    @Test
    void testWriteAccounts() {
        // save 2 songs to file
        testWriter.write(song1);
        testWriter.write(song2);
        testWriter.close();

        // now read them back in and verify that the accounts have the expected values
        try {
            Playlist playlist = PlaylistReader.readPlaylist(new File(TEST_FILE));
            Song song1 = playlist.getCurrentSong();
            assertEquals("Flash Delirium", song1.getName());
            assertEquals("MGMT", song1.getArtist());
            assertEquals("true", song1.getIsSongOver());

            playlist.skipSong();
            Song song2 = playlist.getCurrentSong();
            assertEquals("Time Is Running Out", song2.getName());
            assertEquals("Muse", song2.getArtist());
            assertEquals("true", song2.getIsSongOver());
        } catch (Exception e) {
            fail("IOException should not have been thrown");
        }
    }
}
