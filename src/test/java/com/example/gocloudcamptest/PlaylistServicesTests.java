package com.example.gocloudcamptest;

import com.example.gocloudcamptest.model.Playlist;
import com.example.gocloudcamptest.model.Song;
import com.example.gocloudcamptest.repository.PlaylistRepo;
import com.example.gocloudcamptest.services.PlaylistServices;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class PlaylistServicesTests {

    public void sleep(int time) {
        try {
            Thread.sleep(time * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Inject
    private PlaylistRepo playlistRepo;
    @Inject
    private PlaylistServices playlistServices;

    @Test
    public void shouldDeleteSongFromPlaylist() {
        // given
        Playlist playlist = new Playlist();
        Song song = new Song("Title", "Artist", 120);
        // when
        playlistServices.addSong(song, playlist);
        // then
        playlistServices.deleteSong(song, playlist);
        assertEquals(0, playlistRepo.count());
    }

    @Test
    public void shouldAddSongInPlaylist() {
        // given
        Playlist playlist = new Playlist();
        Song song = new Song("Title", "Artist", 120);
        // when
        playlistServices.addSong(song, playlist);
        // then
        assertEquals(120, playlist.getCurrentSong().getDuration());
        playlistServices.deleteSong(song, playlist);
    }

    @Test
    public void shouldntDeletePlayingSong() {
        // given
        Playlist playlist = new Playlist();
        Song song = new Song("Title", "Artist", 100);
        // when
        playlistServices.addSong(song, playlist);
        playlistServices.play(playlist);
        sleep(1);
        Song playingSong = playlistServices.currentSong(playlist);
        playlistServices.deleteSong(song, playlist);
        // then
        assertEquals(playingSong, playlistServices.currentSong(playlist));
        playlistServices.pause(playlist);
        playlistServices.deleteSong(song, playlist);
        assertNull(playlist.getCurrentSong());
    }

    @Test
    public void shouldDeleteNextSong() {
        // given
        Playlist playlist = new Playlist();
        Song song1 = new Song("Title song 1", "Artist 1", 120);
        Song song2 = new Song("Title song 2", "Artist 2", 180);
        // when
        playlistServices.addSong(song1, playlist);
        playlistServices.addSong(song2, playlist);
        playlistServices.play(playlist);
        sleep(1);
        playlistServices.deleteSong(playlist.getNextSong(), playlist);
        playlistServices.nextSong(playlist);
        // then
        assertEquals("Title song 1", playlist.getCurrentSong().getSongName());
        playlistServices.pause(playlist);
        playlistServices.deleteSong(playlist.getCurrentSong(), playlist);
        assertNull(playlist.getCurrentSong());
    }

    @Test
    public void shouldPlayNextSong() {
        // given
        Playlist playlist = new Playlist();
        Song song1 = new Song("Title song 1", "Artist 1", 120);
        Song song2 = new Song("Title song 2", "Artist 2", 180);
        // when
        playlistServices.addSong(song1, playlist);
        playlistServices.addSong(song2, playlist);
        playlistServices.play(playlist);
        sleep(3);
        playlistServices.nextSong(playlist);
        sleep(3);
        // then
        assertEquals("Title song 2", playlist.getCurrentSong().getTitle());
        playlistServices.pause(playlist);
        playlistServices.deleteSong(song1, playlist);
        playlistServices.deleteSong(song2, playlist);
    }

    @Test
    public void shouldPlayPrevSong() {
        // given
        Playlist playlist = new Playlist();
        Song song1 = new Song("Title song 1", "Artist 1", 120);
        Song song2 = new Song("Title song 2", "Artist 2", 180);
        // when
        playlistServices.addSong(song1, playlist);
        playlistServices.addSong(song2, playlist);
        playlistServices.play(playlist);
        sleep(3);
        Song currentSong = playlistServices.currentSong(playlist);
        assertEquals("Title song 1", currentSong.getTitle());

        playlistServices.nextSong(playlist);
        currentSong = playlistServices.currentSong(playlist);
        assertEquals("Title song 2", currentSong.getTitle());
        sleep(3);
        playlistServices.prevSong(playlist);
        currentSong = playlistServices.currentSong(playlist);
        assertEquals("Title song 1", currentSong.getTitle());
        sleep(3);
        playlistServices.pause(playlist);
        playlistServices.deleteSong(song1, playlist);
        playlistServices.deleteSong(song2, playlist);
    }

    @Test
    public void shouldPlayFromLastStop() {
        // given
        Playlist playlist = new Playlist();
        Song song1 = new Song("Title song 1", "Artist 1", 120);
        // when
        playlistServices.addSong(song1, playlist);
        playlistServices.play(playlist);
        sleep(5);
        Song currentSong = playlistServices.currentSong(playlist);
        assertEquals("Title song 1", currentSong.getTitle());
        playlistServices.pause(playlist);
        sleep(5);
        playlistServices.play(playlist);
        sleep(5);

        playlistServices.nextSong(playlist);
        currentSong = playlistServices.currentSong(playlist);
        assertEquals("Title song 2", currentSong.getTitle());

        playlistServices.prevSong(playlist);
        currentSong = playlistServices.currentSong(playlist);
        assertEquals("Title song 1", currentSong.getTitle());

        playlistServices.pause(playlist);
        playlistServices.deleteSong(song1, playlist);
    }

//    @Test
//    public void shouldUpdateSong() {
//        Playlist playlist = new Playlist();
//        Song song1 = new Song("Title song 1", "Artist 1", 120);
//
//        playlistServices.updateSong();
//    }
}
