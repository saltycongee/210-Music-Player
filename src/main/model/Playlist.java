package model;


import exceptions.PlaylistEmptyException;
import java.util.ArrayList;
import java.util.Collections;

//represents  a playlist with the both a playlists and the played history

public class Playlist {
    private ArrayList<Song> playlist;
    private ArrayList<Song> playHistory;

    //EFFECTS: creates an empty playlist and empty played history list
    public Playlist() {
        playlist = new ArrayList<>();
        playHistory = new ArrayList<>();
    }

    //MODIFIES: this
    //EFFECTS: checks the loaded playlist for any finished songs and adds them to play history.
    public void updateLoadedPlaylist() {
        for (int k = 0; k < playlist.size() + playHistory.size(); k++) {
            if (playlist.get(0).checkSongOver()) {
                playHistory.add(playlist.get(0));
                playlist.remove(0);
            }
        }
    }

    //EFFECTS: returns the first song in the playlist which needs to be played
    public Song getCurrentSong() throws PlaylistEmptyException {
        if (playlist.size() == 0) {
            throw new PlaylistEmptyException();
        }
        return playlist.get(0);
    }

    //EFFECTS: gets song in playlist given an index
    public Song getSongInPlaylist(int index) throws PlaylistEmptyException {
        if (playlist.size() == 0) {
            throw new PlaylistEmptyException();
        }
        return playlist.get(index);
    }

    //EFFECTS: gets song in play history given an index
    public Song getSongInPlayHistory(int index) throws PlaylistEmptyException {
        if (playHistory.size() == 0) {
            throw new PlaylistEmptyException();
        }
        return playHistory.get(index);
    }

    public ArrayList<Song> getPlaylist() {
        return playlist;
    }

    public ArrayList<Song> getPlayHistory() {
        return playHistory;
    }

    //MODIFIES: this
    //EFFECTS: adds the given song into the playlist
    public void addToPlaylist(Song song) {
        playlist.add(song);
    }

    //MODIFIES: this
    //EFFECTS: shuffles the playlist
    public void shufflePlaylist() throws PlaylistEmptyException {
        if (playlist.size() == 0) {
            throw new PlaylistEmptyException();
        }
        Collections.shuffle(playlist);
    }

    //MODIFIES: this
    //EFFECTS: skips to the next song on the playlist.
    public void skipSong() throws PlaylistEmptyException {
        if (playlist.size() <= 1) {
            throw new PlaylistEmptyException();
        }
        playHistory.add(playlist.get(0));
        playlist.get(0).setIsSongOver("true");
        playlist.remove(0);
    }

    //MODIFIES: this
    //EFFECTS: goes back to the last played song
    public void previousSong() throws PlaylistEmptyException {
        if (playHistory.size() == 0) {
            throw new PlaylistEmptyException();
        }
        playlist.add(0, playHistory.get(playHistory.size() - 1));
        playHistory.get(playHistory.size() - 1).setIsSongOver("false");
        playHistory.remove(playHistory.size() - 1);
    }

    //EFFECTS: returns the position of a song in the playlist
    public int getPositionOfSong(Song song) {
        return playlist.indexOf(song) + 1;
    }

    //EFFECTS: returns the position of a song in the playlist history
    public int getPositionOfSongInHistory(Song song) {
        return playHistory.indexOf(song) + 1;
    }
}
