package com.github.mjhassanpur.opennote;

public class Note {

    private int id;
    private String content;

    public Note() { }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }
}
