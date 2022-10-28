package vietmobi.net.noteapp.model;

import java.io.Serializable;

public class Note implements Serializable {
    private String id;
    private String title;
    private String content;
    private boolean isFavorite;
    private String time;

    public Note(String title, String content, boolean isFavorite, String time) {
        this.title = title;
        this.content = content;
        this.isFavorite = isFavorite;
        this.time = time;
    }

    public Note(String title, String content, String time) {
        this.title = title;
        this.content = content;
        this.time = time;
    }

    public Note(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Note() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
