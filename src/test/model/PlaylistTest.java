package model;

import exceptions.PlaylistEmptyException;
import exceptions.SongCreationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.SongReader;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class PlaylistTest {
    private Playlist playlistTest;
    private Playlist emptyPlaylist;
    private Song s1;
    private Song s2;
    private Song s3;
    private Song s4;

    @BeforeEach
    void runBefore() {
        playlistTest = new Playlist();
        emptyPlaylist = new Playlist();
        try {
            s1 = new Song("Say It Ain't So", "Weezer", "true");
        } catch (SongCreationException e) {
            fail("VALID SONG");
        }
        try {
            s2 = new Song("The Modern Age", "The Strokes", "true");
        } catch (SongCreationException e) {
            fail("VALID SONG");        }
        try {
            s3 = new Song("Don't Look Back In Anger", "Oasis", "false");
        } catch (SongCreationException e) {
            fail("VALID SONG");
        }
        try {
            s4 = new Song("", "", "yes");
            fail("INVALID SONG");
        } catch (SongCreationException e) {
        }
        playlistTest.addToPlaylist(s1);
        playlistTest.addToPlaylist(s2);
        playlistTest.addToPlaylist(s3);
    }

    @Test
    void testConstructor() {
        try {
            assertEquals(s1, playlistTest.getSongInPlaylist(0));
            assertEquals(s2, playlistTest.getSongInPlaylist(1));
            assertEquals(s3, playlistTest.getSongInPlaylist(2));
        } catch (PlaylistEmptyException e) {
            fail ("Should not have thrown exception");
        }
        try {
            emptyPlaylist.getSongInPlaylist(0);
            fail ("Exception should have been thrown");
        } catch (PlaylistEmptyException e) {
        }
        try {
            playlistTest.getSongInPlayHistory(0);
            fail ("Exception should have been thrown");
        } catch (PlaylistEmptyException e) {
        }
    }

    @Test
    void updateLoadedPlaylistTest() {
        playlistTest.updateLoadedPlaylist();
        try {
            assertEquals(s1, playlistTest.getSongInPlayHistory(0));
            assertEquals(s2, playlistTest.getSongInPlayHistory(1));
            assertEquals(s3, playlistTest.getSongInPlaylist(0));
            assertEquals(1, playlistTest.getPlaylist().size());
            assertEquals(2, playlistTest.getPlayHistory().size());
        } catch (PlaylistEmptyException e) {
            fail("Should not have thrown exception");
        }
    }

    @Test
    void addToPlaylistTest() {
        assertEquals(1, playlistTest.getPositionOfSong(s1));
        assertEquals(2, playlistTest.getPositionOfSong(s2));
        assertEquals(3, playlistTest.getPositionOfSong(s3));
    }

    @Test
    void getCurrentSongTest() {
        try {
            assertEquals(s1, playlistTest.getCurrentSong());
        } catch (PlaylistEmptyException e) {
            fail("NON EMPTY");
        }
        try {
            emptyPlaylist.getCurrentSong();
            fail("EMPTY PLAYLIST");
        } catch (PlaylistEmptyException e) {
        }
    }

    @Test
    void skipSongTest() {
        try {
            playlistTest.skipSong();
        } catch (PlaylistEmptyException e) {
            fail("NON EMPTY");
        }
        try {
            emptyPlaylist.skipSong();
            fail("EMPTY PLAYLIST");
        } catch (PlaylistEmptyException e) {
        }
        assertEquals(1, playlistTest.getPositionOfSongInHistory(s1));
        assertEquals(1, playlistTest.getPositionOfSong(s2));
        assertEquals(2, playlistTest.getPositionOfSong(s3));
    }

    @Test
    void previousSongTest() {
        try {
            playlistTest.skipSong();
        } catch (PlaylistEmptyException e) {
            fail("NON EMPTY");
        }
        try {
            playlistTest.previousSong();
        } catch (PlaylistEmptyException e) {
            fail("NON EMPTY");
        }
        try {
            emptyPlaylist.previousSong();
            fail("EMPTY");
        } catch (PlaylistEmptyException e) {
        }
        assertEquals(0, playlistTest.getPositionOfSongInHistory(s1));
        assertEquals(1, playlistTest.getPositionOfSong(s1));
        assertEquals(2, playlistTest.getPositionOfSong(s2));
        assertEquals(3, playlistTest.getPositionOfSong(s3));
    }

    @Test
    void shufflePlaylistTest() {
        Playlist copyPlaylist = new Playlist();
        copyPlaylist.addToPlaylist(s1);
        copyPlaylist.addToPlaylist(s2);
        copyPlaylist.addToPlaylist(s3);
        try {
            playlistTest.shufflePlaylist();
        } catch (PlaylistEmptyException e) {
            fail("NON EMPTY");
        }
        try {
            emptyPlaylist.shufflePlaylist();
            fail("EMPTY");
        } catch (PlaylistEmptyException e) {
        }
        assertFalse(copyPlaylist.equals(playlistTest));
    }
}
