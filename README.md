# My Personal Project: Music Player

## Short Description

For my personal project, I have decided to make a music player, as playing music is something I do daily. I
think it would be really cool if I could make a program that could play all my favourite songs. The application will
contain a simple user interface, with standard functions such as:
 
 - play 
 - pause
 - skip
 - go back
 - shuffle
 - repeat
 
 Also, I would like to implement a load function, so that any audio file can be played, as well as any playlist. There
 will also be playlist functions, such as shuffle, and the ability to view and add to your queue.
 Essentially, anyone should be able to use this program, as long as they have some songs they would like to play.

## User Stories

- As a user, I want to play or pause a song.
- As a user, I want to be able to add a song to my queue (playlist).
- As a user, I want to skip over any songs, or go back to the previous song.
- As a user, I want to be given the option to shuffle or play a playlist in order.
- As a user, I want to load a new playlist, or keep playing from where they left off.
- As a user, I want to be able to save my place in the playlist, so I can continue later.

## Instructions For Grader

- To add songs, click "file", then hover over add songs, then add the songs you want. To view your queue, click file,
then view queue.
- Song operations such as play, pause, and so on, are processed through the 6 buttons on screen.
- Press play for music :)
- To save, click "file", then click "Save Playlist".
- To load, click "file", then click "Load Playlist."

OTHER IMPORTANT NOTES:

- After a song has ended, the next song will not automatically start, the user must click next or previous to continue
playing. This is due to limitations of the API I used to play music.
- The very first time a user loads the program, the playlist will be empty. You must first add at least one song, save
the playlist, then load the playlist.
- After adding a song, or make any other alterations to your playlist such as shuffling, if you do not save the 
playlist, the next time you load the playlist it will still be in the state it was before the alterations.

## Phase 4: Task 2
I have decided to make a couple classes more robust. I have added two exceptions: first of which is 
SongCreationException, which sits the Song class' constructor. It prevents invalid song titles or artists from being
passed into the constructor. Another exception is the PlaylistEmptyException, which prevents most of the methods in the 
Playlist class from being called on an empty playlist as that would break the program.

## Phase 4: Task 3
Current coupling and cohesion issues:

- The Playlist class has both playlist maintaining duties and audio playing capabilities.
- The MusicPlayer class has left over console running code from before the GUI phase.
- The Song Playlist and Song classes have left over code from when song durations were still being used.
- Long if elses in the Music Player could be changed to switch statements for clarity.

Issues I Fixed:
- Improved cohesion in the Playlist class by extracting all audio playing capabilities into a new class called Audio.
- Decreased coupling in Song and Playlist by removing all methods and fields pertaining to duration, as duration
is no longer used in my program.
- Cleaned up the code for my entire program, removing old bits of code and improving readability.
