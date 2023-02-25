package com.example.gocloudcamptest.repository;

import com.example.gocloudcamptest.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylistRepo extends JpaRepository<Song,Integer> {
}
