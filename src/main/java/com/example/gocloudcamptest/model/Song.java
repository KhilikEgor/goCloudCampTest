package com.example.gocloudcamptest.model;

import jakarta.persistence.*;

@Entity(name = "song")
@Table(name = "playlist")
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "duration")
    private int duration;

    @Column(name = "artist")
    private String artist;

    public Song(String title, String artist, int duration) {
        this.title = title;
        this.duration = duration;
        this.artist = artist;
    }

    public Song() {
    }

    public int getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public int getDuration() {
        return duration;
    }

}
