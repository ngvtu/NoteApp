package vietmobi.net.noteapp.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;


@Entity(tableName = "all_note")
public class Note implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    //ColumnInfo(title = "title_column")
    private String title;
    private String content;
    private String ofFolder;
    private boolean isFavorite;
    private String time;
    private boolean isLocked;

    public Note(String title, String content, String ofFolder, boolean isFavorite, String time, boolean isLocked) {
        this.title = title;
        this.content = content;
        this.ofFolder = ofFolder;
        this.isFavorite = isFavorite;
        this.time = time;
        this.isLocked = isLocked;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public Note(String title, String content, String ofFolder, boolean isFavorite, String time) {
        this.title = title;
        this.content = content;
        this.ofFolder = ofFolder;
        this.isFavorite = isFavorite;
        this.time = time;
    }

    public String getOfFolder() {
        return ofFolder;
    }

    public void setOfFolder(String ofFolder) {
        this.ofFolder = ofFolder;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
