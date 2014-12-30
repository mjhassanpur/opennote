package com.github.mjhassanpur.opennote;

public class Notebook implements NoteItem {

    private int id;
    private String title;

    public Notebook(String title) {
        this.title = title;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }
}
