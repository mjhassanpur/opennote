package com.github.mjhassanpur.opennote;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


public class Note implements NoteItem {

    private int id;
    private String title;
    private String content;
    private Date created;
    private Date edited;

    public Note() {
        this.created = new Date();
        this.edited = this.created;
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        df.setTimeZone(TimeZone.getDefault());
        this.title = "Note - " + df.format(this.created);
        this.content = "Nothing...";
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

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }

    public Date getCreated() {
        return this.created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getEdited() {
        return this.edited;
    }

    public void setEdited(Date edited) {
        this.edited = edited;
    }

}
