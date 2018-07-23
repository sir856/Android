package com.example.admin.note;

import com.google.gson.annotations.*;

public class Note{
    public static int idCount;

    @SerializedName("id")
    int id;

    @SerializedName("title")
    private String title;

    @SerializedName("text")
    private String text;

    @SerializedName("author")
    private String author;

    public Note(String author){
        this.author = author;
        id = ++idCount;
    }

    public int getId() {
        return id;
    }

    public void setText (String text){
        this.text = text;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public String getText() {
        return text;
    }

    public String getTitle() {
        return title;
    }
}
