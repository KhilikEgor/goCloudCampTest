package com.example.gocloudcamptest.controller;
import com.example.gocloudcamptest.model.Song;
import com.example.gocloudcamptest.repository.PlaylistRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/playlists")
public class PlaylistController {

    @Autowired
    private PlaylistRepo playlistRepo;

    @GetMapping
    public List<Song> getAllPlaylists() {
        return playlistRepo.findAll();
    }

    @PostMapping
    public Song createPlaylist(@RequestBody Song song) {
        return playlistRepo.save(song);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Song> updatePlaylist(@PathVariable int id) {
        Optional<Song> existingPlaylist = playlistRepo.findById(id);
        if (existingPlaylist.isPresent()) {
            Song updatedPlaylist = existingPlaylist.get();
            playlistRepo.save(updatedPlaylist);
            return ResponseEntity.ok(updatedPlaylist);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlaylist(@PathVariable int id) {
        Optional<Song> playlist = playlistRepo.findById(id);
        if (playlist.isPresent()) {
            playlistRepo.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}