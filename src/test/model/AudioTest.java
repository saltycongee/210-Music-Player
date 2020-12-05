package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.SongReader;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class AudioTest {
    private Audio audioTest;

    @BeforeEach
    void runBefore() {
        audioTest = new Audio();
    }

    @Test
    void playPauseTest() {
        try {
            audioTest.setCurrentAudioStream(SongReader.readSong("./data/Bodysnatchers.wav"));
            audioTest.playCurrentSong();
            assertTrue(audioTest.checkIfSongIsAlive());
            audioTest.pauseCurrentSong();
            assertTrue(audioTest.checkIfSongIsAlive());
        } catch (IOException e) {
            fail ("Should not have thrown exception");
        }
        try {
            audioTest.setCurrentAudioStream(SongReader.readSong("./data/Does Not Exist.wav"));
            fail ("Exception should have been thrown");
        } catch (IOException e) {
        }
    }
}
