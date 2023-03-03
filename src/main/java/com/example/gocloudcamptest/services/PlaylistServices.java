package com.example.gocloudcamptest.services;

import com.example.gocloudcamptest.model.Playlist;
import com.example.gocloudcamptest.model.Song;
import com.example.gocloudcamptest.repository.PlaylistRepo;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class PlaylistServices {

    @Inject
    PlaylistRepo playlistRepo;

    public void addSong(Song song, Playlist playlist) {
        playlist.addSong(song);
        playlistRepo.save(song);
    }

    public void deleteSong(Song song, Playlist playlist) {
        if (playlist.isPlaying() && playlist.getCurrentSong() != song) {
            playlist.deleteSong(song);
            playlistRepo.deleteById(song.getId());
        } else if (!playlist.isPlaying()) {
            playlist.deleteSong(song);
            playlistRepo.deleteById(song.getId());
        }
    }

    public void nextSong(Playlist playlist) {
        if (playlist.isPlaying()) {
            playlist.pause();
            playlist.nextSong();
            playlist.play();
        } else {
            playlist.nextSong();
        }
    }

    public void prevSong(Playlist playlist) {
        if (playlist.isPlaying()) {
            playlist.pause();
            playlist.prevSong();
            playlist.play();
        } else {
            playlist.prevSong();
        }
    }

    public void play(Playlist playlist) {
        Song currentSong = playlist.getCurrentSong();
        if (currentSong != null) {
            Thread thread = new Thread(() -> {
                int currentDuration = 0;
                playlist.play();
                System.out.println("Playing " + currentSong.getTitle() + " by " + currentSong.getArtist());
                int songDuration = currentSong.getDuration();
                boolean isPaused = false; // flag to check if the song is paused
                while (songDuration > 0 && currentDuration != songDuration) {
                    if (!isPaused) {
                        int minutes = currentDuration / 60;
                        int seconds = currentDuration % 60;
                        currentDuration += 1;
                        String time = String.format("%02d:%02d", minutes, seconds);
                        System.out.println("Time elapsed: " + time);
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (currentSong != playlist.getCurrentSong()) {
                        playlist.pause();
                        System.out.println("Stopped playing " + currentSong.getTitle() + " by " + currentSong.getArtist());
                        System.out.println();
                        play(playlist);
                        return;
                    }
                    if (!playlist.isPlaying()) { // check if the playlist is paused
                        isPaused = true; // set flag to true
                        System.out.println("Paused " + currentSong.getTitle() + " by " + currentSong.getArtist());
                    } else {
                        isPaused = false; // set flag to false
                    }
                    if (currentDuration == songDuration) { // check if the song is over
                        if (currentSong == playlist.getCurrentSong()) {
                            playlist.pause();
                            isPaused = true;
                        } else {
                            playlist.pause(); // pause the playlist
                            System.out.println("Stopped playing " + currentSong.getTitle() + " by " + currentSong.getArtist());
                            System.out.println();
                            playlist.nextSong(); // go to the next song in the playlist
                            play(playlist); // start playing the next song recursively
                            return;
                        }
                    }
                    if (playlist.nextSong() == playlist.getCurrentSong() && currentDuration == songDuration) {
                        System.out.println();
                        System.out.println("The songs in the playlist are over!");
                        pause(playlist);
                    }
                }
                playlist.pause();
                System.out.println("Stopped playing " + currentSong.getTitle() + " by " + currentSong.getArtist());
            });
            thread.start();
        }
    }


    public void pause(Playlist playlist) {
        playlist.pause();
    }

    public Song currentSong(Playlist playlist) {
        return playlist.getCurrentSong();
    }

}

//todo №1
// поток для проигывания музыки поток будет длиться durationSong. Песня заканчивается поток закрывется,
// начиается след песня под которую создается отдельный поток

//todo №2
// сделать отдельную таблицу song и playlist. Связать данные таблицы

//todo №3
// update допилить playlistService, songRepo

//todo №4
// написать playlistController

//todo №5
// gRPC