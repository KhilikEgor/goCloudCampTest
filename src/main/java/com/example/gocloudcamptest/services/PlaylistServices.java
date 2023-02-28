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
        playlistRepo.save(song); // playlist and playlistRepo
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

//    public void updateSong(Song song) {};


    public void nextSong(Playlist playlist) {
        if (playlist == null || playlist.nextSong() == null) {
            return;
        }
        playlist.nextSong();
    }

    public void prevSong(Playlist playlist) {
        if (playlist == null || playlist.prevSong() == null) {
            return;
        }
        playlist.prevSong();
    }

    public void play(Playlist playlist) {
        Song currentSong = playlist.getCurrentSong();
        Thread thread = new Thread(() -> {
            playlist.play();
            System.out.println("Playing " + currentSong.getTitle() + " by " + currentSong.getArtist());
            try {
                Thread.sleep(currentSong.getDuration() * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(currentSong.getTitle() + " by " + currentSong.getArtist() + " has finished playing.");
        });
        thread.start();
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