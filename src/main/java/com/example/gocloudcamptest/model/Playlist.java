package com.example.gocloudcamptest.model;

public class Playlist {

    private songNode head;
    private songNode tail;
    private songNode current;
    private boolean isPlaying;

    public boolean isPlaying() {
        return isPlaying;
    }

    private static class songNode {
        private final Song song;
        private songNode prev;
        private songNode next;

        private songNode(Song song) {
            this.song = song;
            this.prev = null;
            this.next = null;
        }
    }

    public Playlist() {
    }

    public void play() {
        if (this.current == null && this.head != null) {
            this.current = this.head;
        }
        this.isPlaying = true;
    }

    public void pause() {
        if (current != null) {
            isPlaying = false;
        }
    }

    public void addSong(Song song) {
        songNode newSong = new songNode(song);
        if (this.head == null) {
            this.head = newSong;
            this.current = newSong;
        } else {
            newSong.prev = this.tail;
            this.tail.next = newSong;
        }
        this.tail = newSong;
    }


    public Song getCurrentSong() {
        return current != null ? current.song : null;
    }

    public Song nextSong() {
        if (current != null && current.next != null) {
            current = current.next;
        }
        return current != null ? current.song : null;
    }

    public Song prevSong() {
        if (current != null && current.prev != null) {
            current = current.prev;
        }
        return current != null ? current.song : null;
    }

    public Song getNextSong() {
        if (current != null && current.next != null) {
            return current.next.song;
        }
        return null;
    }

    public Song getPrevSong() {
        if (current != null && current.prev != null) {
            return current.prev.song;
        }
        return null;
    }


    public void deleteSong(Song song) {
        songNode node = head;
        while (node != null) {
            if (node.song.equals(song)) {
                if (node.prev == null) {
                    head = node.next;
                    if (head != null) {
                        head.prev = null;
                    } else {
                        tail = null;
                    }
                } else if (node.next == null) {
                    tail = node.prev;
                    tail.next = null;
                } else {
                    node.prev.next = node.next;
                    node.next.prev = node.prev;
                }
                if (current == node) {
                    current = null;
                    isPlaying = false;
                }
                break;
            }
            node = node.next;
        }
    }
}
