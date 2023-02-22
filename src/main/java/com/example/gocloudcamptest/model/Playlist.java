package com.example.gocloudcamptest.model;

public class Playlist {

    private Node head;
    private Node tail;
    private Node current;
    private boolean isPlaying;

    private class Node {
        private Song song;
        private Node prev;
        private Node next;

        private Node(Song song) {
            this.song = song;
            this.prev = null;
            this.next = null;
        }
    }


    public Playlist(Node head, Node tail, Node current) {
        this.head = head;
        this.tail = tail;
        this.current = current;
        this.isPlaying = false;
    }

    public void play(Song song) {
        if (current != null) {
            isPlaying = true;
        }
    }

    public void pause(Song song) {
        if (current != null) {
            isPlaying = false;
        }
    }

    public void addSong(Song song) {
        Node newNode = new Node(song);
        if (this.head == null) {
            this.head = newNode;
        } else {
            this.tail = newNode;
        }
    }

    public void next(Song song) {
        if (current != null && current.next != null) {
            current = current.next;
            isPlaying = true;
        }
    }

    public void prev(Song song) {
        if (current != null && current.prev != null) {
            current = current.prev;
            isPlaying = true;
        }
    }


}
