package model;

import exceptions.SongCreationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.PlaylistReader;
import persistence.Writer;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class SongTest {
    private Song testSong;
    private Song invalidSong1;
    private Song invalidSong2;
    private Song invalidSong3;
    private static final String TEST_FILE = "./data/PlaylistWriteTest.txt";
    private Writer testWriter;
    private Song song1;
    private Song song2;

    @BeforeEach
    void runBefore() {
        try {
            testSong = new Song("Say It Ain't So", "Weezer", "false");
            testWriter = new Writer(new File(TEST_FILE));
            song1 = new Song("Flash Delirium", "MGMT", "true");
            song2 = new Song("Time Is Running Out", "Muse", "true");
        } catch (Exception e) {
            fail("VALID SONG");
        }
        try {
            invalidSong1 = new Song("", "Weezer", "false");
            fail("INVALID SONG");
        } catch (SongCreationException e) {
        }
        try {
            invalidSong2 = new Song("Say It Ain't So", "", "false");
            fail("INVALID SONG");
        } catch (SongCreationException e) {
        }
        try {
            invalidSong3 = new Song("Say It Ain't So", "Weezer", "yes");
            fail("INVALID SONG");
        } catch (SongCreationException e) {
        }
    }

    @Test
    void testConstructor() {
        assertEquals("Say It Ain't So", testSong.getName());
        assertEquals("Weezer", testSong.getArtist());
        assertEquals("false", testSong.getIsSongOver());
    }

    @Test
    void checkSongOverTest() {
        assertFalse(testSong.checkSongOver());
        testSong.setIsSongOver("true");
        assertTrue(testSong.checkSongOver());
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