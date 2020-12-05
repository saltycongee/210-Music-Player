package ui;

import exceptions.PlaylistEmptyException;
import exceptions.SongCreationException;
import model.Audio;
import model.Playlist;
import model.Song;
import persistence.PlaylistReader;
import persistence.SongReader;
import persistence.Writer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//BASIC STRUCTURE TAKEN FROM TELLER APP
//Music player application
//IMPORTANT NOTE: MUSIC PLAYER WILL NOT AUTOMATICALLY START THE NEXT SONG AFTER THE PREVIOUS SONG HAS ENDED
//                THE USER MUST INPUT NEXT OR PREVIOUS TO CONTINUE PLAYING FROM THE PLAYLIST
//                THIS IS DUE TO LIMITATIONS OF THE API WHICH I USE TO PLAY THE MUSIC
public class MusicPlayer extends JFrame implements ActionListener {
    private static final String PLAYLIST_FILE = "./data/Playlist.txt";
    private static final String PLAYLIST_CHOICES = "./data/PlaylistChoices.txt";
    private Playlist playlist;
    private Playlist playlistChoices;
    private Audio currentAudio;
    private JLabel currentSong;
    private JLabel currentArtist;
    private JMenuBar saveLoadBar;
    private JMenu file;
    private JMenuItem viewQueue;
    private JMenuItem load;
    private JMenuItem save;
    private JMenu addSongs;
    private JMenuItem firstSong;
    private JMenuItem secondSong;
    private JMenuItem thirdSong;
    private JMenuItem fourthSong;
    private JMenuItem fifthSong;
    private Song songChoice1;
    private Song songChoice2;
    private Song songChoice3;
    private Song songChoice4;
    private Song songChoice5;

    //EFFECTS: creates the music player GUI and runs the music player
    public MusicPlayer() {
        super("Music Player");
        playlist = new Playlist();
        currentAudio = new Audio();
        loadPlaylistChoices();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(500, 300));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        setLayout(new FlowLayout());
        currentSong = new JLabel("Welcome to my music player,");
        currentArtist = new JLabel("start by loading a playlist");
        createButtons();
        createMoreButtons();
        add(currentSong);
        add(currentArtist);
        createSaveLoadMenu();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    //MODIFIES: this
    //EFFECTS: creates the basic buttons, play, pause, and so on
    private void createButtons() {
        JButton play = new JButton("Play");
        play.setActionCommand("play");
        play.addActionListener(this);
        JButton pause = new JButton("Pause");
        pause.setActionCommand("pause");
        pause.addActionListener(this);
        JButton next = new JButton("Next");
        next.setActionCommand("next");
        next.addActionListener(this);
        JButton previous = new JButton("Previous");
        previous.setActionCommand("previous");
        previous.addActionListener(this);
        JButton repeat = new JButton("Repeat");
        repeat.setActionCommand("repeat");
        repeat.addActionListener(this);
        add(repeat);
        add(previous);
        add(play);
        add(pause);
        add(next);
    }

    //MODIFIES: this
    //EFFECTS: creates the shuffle button, this is in another method due to checkstyle
    private void createMoreButtons() {
        JButton shuffle = new JButton("Shuffle");
        shuffle.setActionCommand("shuffle");
        shuffle.addActionListener(this);
        add(shuffle);
    }

    //MODIFIES: this
    //EFFECTS: creates the menu bar for saving and loading
    private void createSaveLoadMenu() {
        saveLoadBar = new JMenuBar();
        file = new JMenu("File");
        saveLoadBar.add(file);
        viewQueue = new JMenuItem("View Queue");
        viewQueue.setActionCommand("view");
        viewQueue.addActionListener(this);
        load = new JMenuItem("Load Playlist");
        load.setActionCommand("load");
        load.addActionListener(this);
        save = new JMenuItem("Save Playlist");
        save.setActionCommand("save");
        save.addActionListener(this);
        addSongs = new JMenu("Add Songs To Playlist");
        createAddableSongs();
        file.add(load);
        file.add(save);
        file.add(addSongs);
        file.add(viewQueue);
        setJMenuBar(saveLoadBar);
    }

    //MODIFIES: this
    //EFFECTS: creates the the addable songs in the drop down menu
    private void createAddableSongs() {
        firstSong = new JMenuItem(songChoice1.getName());
        firstSong.setActionCommand("song1");
        firstSong.addActionListener(this);
        secondSong = new JMenuItem(songChoice2.getName());
        secondSong.setActionCommand("song2");
        secondSong.addActionListener(this);
        thirdSong = new JMenuItem(songChoice3.getName());
        thirdSong.setActionCommand("song3");
        thirdSong.addActionListener(this);
        fourthSong = new JMenuItem(songChoice4.getName());
        fourthSong.setActionCommand("song4");
        fourthSong.addActionListener(this);
        fifthSong = new JMenuItem(songChoice5.getName());
        fifthSong.setActionCommand("song5");
        fifthSong.addActionListener(this);
        addSongs.add(firstSong);
        addSongs.add(secondSong);
        addSongs.add(thirdSong);
        addSongs.add(fourthSong);
        addSongs.add(fifthSong);
    }

    // MODIFIES: this
    // EFFECTS: loads playlist from PLAYLIST_FILE, if that file exists;
    // otherwise displays an error
    private void loadPlaylist() {
        try {
            currentAudio.pauseCurrentSong();
            playlist = PlaylistReader.readPlaylist(new File(PLAYLIST_FILE));
            playlist.updateLoadedPlaylist();
            currentAudio.setCurrentAudioStream(SongReader.readSong("./data/" + playlist.getCurrentSong().getName()
                    + ".wav"));
            currentSong.setText("Playlist Loaded, Ready to Play!");
            currentArtist.setText("");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Dude your playlist is empty",
                    "Empty Playlist Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //MODIFIES: this
    //EFFECTS: loads playlist choices from PLAYLIST_CHOICES, if that file exists
    // otherwise displays an error
    private void loadPlaylistChoices() {
        try {
            playlistChoices = PlaylistReader.readPlaylist(new File(PLAYLIST_CHOICES));
            songChoice1 = playlistChoices.getCurrentSong();
            songChoice2 = playlistChoices.getSongInPlaylist(1);
            songChoice3 = playlistChoices.getSongInPlaylist(2);
            songChoice4 = playlistChoices.getSongInPlaylist(3);
            songChoice5 = playlistChoices.getSongInPlaylist(4);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Cmon gimme some playlist choices",
                    "Empty Playlist Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // EFFECTS: saves state of five songs to PLAYLIST_FILE
    private void savePlaylist() {
        try {
            Writer writer = new Writer(new File(PLAYLIST_FILE));
            for (Song s: playlist.getPlayHistory()) {
                writer.write(s);
            }
            for (Song s: playlist.getPlaylist()) {
                writer.write(s);
            }
            writer.close();
            currentSong.setText("Playlist Saved!");
            currentArtist.setText("");
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "File Not Found",
                    "File Not Found Error", JOptionPane.ERROR_MESSAGE);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            // this is due to a programming error
        }
    }

    //EFFECTS: displays the current queue in a new GUI window
    private void viewQueue() {
        JTextArea queue = new JTextArea(20, 20);
        ArrayList<Song> playlistQueue = playlist.getPlaylist();
        for (Song s : playlistQueue) {
            String queueText = s.getName();
            queue.append(queueText + "\n");
        }
        JScrollPane scrollPane = new JScrollPane(queue);
        queue.setLineWrap(true);
        queue.setWrapStyleWord(true);
        queue.setEnabled(false);
        scrollPane.setPreferredSize(new Dimension(300, 300));
        JOptionPane.showMessageDialog(null, scrollPane, "Your Playlist Queue", JOptionPane.INFORMATION_MESSAGE);
    }

    //EFFECTS: handles the actions performed by the buttons
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("play")) {
            play();
        } else if (e.getActionCommand().equals("pause")) {
            pause();
        } else if (e.getActionCommand().equals("next")) {
            next();
        } else if (e.getActionCommand().equals("previous")) {
            previous();
        } else if (e.getActionCommand().equals("repeat")) {
            repeat();
        } else if (e.getActionCommand().equals("shuffle")) {
            shuffle();
        } else if (e.getActionCommand().equals("save")) {
            savePlaylist();
        } else if (e.getActionCommand().equals("load")) {
            loadPlaylist();
        } else if (e.getActionCommand().equals("view")) {
            viewQueue();
        } else {
            songAddingActionPerformed(e);
        }
    }

    //EFFECTS: handles the actions performed by buttons adding songs to the playlist
    public void songAddingActionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("song1")) {
            playlist.addToPlaylist(songChoice1);
            currentSong.setText(songChoice1.getName());
            currentArtist.setText("added to playlist!");
        } else if (e.getActionCommand().equals("song2")) {
            playlist.addToPlaylist(songChoice2);
            currentSong.setText(songChoice2.getName());
            currentArtist.setText("added to playlist!");
        } else if (e.getActionCommand().equals("song3")) {
            playlist.addToPlaylist(songChoice3);
            currentSong.setText(songChoice3.getName());
            currentArtist.setText("added to playlist!");
        } else if (e.getActionCommand().equals("song4")) {
            playlist.addToPlaylist(songChoice4);
            currentSong.setText(songChoice4.getName());
            currentArtist.setText("added to playlist!");
        } else if (e.getActionCommand().equals("song5")) {
            playlist.addToPlaylist(songChoice5);
            currentSong.setText(songChoice5.getName());
            currentArtist.setText("added to playlist!");
        }
    }

    //MODIFIES: this
    //EFFECTS: plays the first song in the playlist
    private void play() {
        try {
            currentAudio.playCurrentSong();
            currentSong.setText("NOW PLAYING: " + playlist.getCurrentSong().getName());
            currentArtist.setText("BY: " + playlist.getCurrentSong().getArtist());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Bro load a playlist please",
                    "Empty Playlist Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //MODIFIES: this
    //EFFECTS: pauses the current song.
    private void pause() {
        try {
            currentAudio.pauseCurrentSong();
            currentSong.setText("PAUSING: " + playlist.getCurrentSong().getName());
            currentArtist.setText("BY: " + playlist.getCurrentSong().getArtist());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Bro load a playlist please",
                    "Empty Playlist Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //MODIFIES: this
    //EFFECTS: skips to the next song
    private void next() {
        try {
            playlist.skipSong();
            currentAudio.pauseCurrentSong();
            currentAudio.setCurrentAudioStream(SongReader.readSong("./data/" + playlist.getCurrentSong().getName()
                    + ".wav"));
            currentAudio.playCurrentSong();
            currentSong.setText("SKIPPING TO: " + playlist.getCurrentSong().getName());
            currentArtist.setText("BY: " + playlist.getCurrentSong().getArtist());
        } catch (IOException e) {
            System.err.println("IO Exception!");
        } catch (PlaylistEmptyException e) {
            JOptionPane.showMessageDialog(null, "Your fire playlist is over :(",
                    "Empty Playlist Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //MODIFIES: this
    //EFFECTS: goes back to the last played song
    private void previous() {
        try {
            playlist.previousSong();
            currentAudio.pauseCurrentSong();
            currentAudio.setCurrentAudioStream(SongReader.readSong("./data/" + playlist.getCurrentSong().getName()
                    + ".wav"));
            currentAudio.playCurrentSong();
            currentSong.setText("GOING BACK TO: " + playlist.getCurrentSong().getName());
            currentArtist.setText("BY: " + playlist.getCurrentSong().getArtist());
        } catch (IOException e) {
            System.err.println("IO Exception!");
        } catch (PlaylistEmptyException e) {
            JOptionPane.showMessageDialog(null, "Chill you're at the beginning of the playlist",
                    "Empty Playlist Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //MODIFIES: this
    //EFFECTS: repeats the current song
    private void repeat() {
        try {
            currentAudio.pauseCurrentSong();
            currentAudio.setCurrentAudioStream(SongReader.readSong("./data/" + playlist.getCurrentSong().getName()
                    + ".wav"));
            currentAudio.playCurrentSong();
            currentSong.setText("PLAYING AGAIN: " + playlist.getCurrentSong().getName());
            currentArtist.setText("BY: " + playlist.getCurrentSong().getArtist());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Bro load a playlist please",
                    "Empty Playlist Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //MODIFIES: this
    //EFFECTS: shuffles the playlist.
    private void shuffle() {
        try {
            playlist.shufflePlaylist();
            currentAudio.pauseCurrentSong();
            currentAudio.setCurrentAudioStream(SongReader.readSong("./data/" + playlist.getCurrentSong().getName()
                    + ".wav"));
            currentAudio.playCurrentSong();
            currentSong.setText("NOW PLAYING: " + playlist.getCurrentSong().getName());
            currentArtist.setText("BY: " + playlist.getCurrentSong().getArtist());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Bro load a playlist please",
                    "Empty Playlist Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
