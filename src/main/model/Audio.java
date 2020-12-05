package model;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import java.io.IOException;
import java.io.InputStream;

//Represents the audio that is being outputted
public class Audio {
    private AudioStream audio;

    //Creates an audiotream
    public Audio() {
        audio = null;
    }

    //MODIFIES: this
    //EFFECTS: creates a new audiostream
    public void setCurrentAudioStream(InputStream music) throws IOException {
        audio = new AudioStream(music);
    }

    //MODIFIES: this
    //EFFECTS: plays the current audio
    public void playCurrentSong()  {
        AudioPlayer.player.start(audio);
    }

    //MODIFIES: this
    //EFFECTS: pauses the current audio
    public void pauseCurrentSong() {
        AudioPlayer.player.stop(audio);
    }

    //EFFECTS: only for testing, checks to makes sure audio thread is still alive
    public boolean checkIfSongIsAlive() {
        return AudioPlayer.player.isAlive();
    }
}
