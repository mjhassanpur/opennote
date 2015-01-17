package com.github.mjhassanpur.opennote;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.ArrayList;
import java.util.Collections;


public class Note implements NoteItem {

    private int id;
    private String title;
    private String content;
    private Date created;
    private Date edited;
    private ArrayList<String> tags;

    public Note() {
        this.created = new Date();
        this.edited = this.created;
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        df.setTimeZone(TimeZone.getDefault());
        this.title = "Note - " + df.format(this.created);
        this.content = "Nothing...";
        this.tags = new ArrayList<String>();
        this.tags.add(0, "test");
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

    public ArrayList<String> getTags() {
        return this.tags;
    }

    public void setTags(ArrayList<String> tags) {
        Collections.copy(this.tags, tags);
    }

    public void addTag(String tag) {
        this.tags.add(tag);
    }
}
