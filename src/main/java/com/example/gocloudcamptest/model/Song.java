package com.example.gocloudcamptest.model;

import jakarta.persistence.*;

@Entity(name = "song")
@Table(name = "playlist")
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "duration")
    private String duration;

    @Column(name = "artist")
    private String artist;

    public Song(Long id, String title, String duration, String artist) {
        this.id = id;
        this.title = title;
        this.duration = duration;
        this.artist = artist;
    }

    public Song() {

    }

    public String getSongName() {
        return title;
    }

    public void setSongName(String songName) {
        this.title = songName;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
