package vietmobi.net.noteapp.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "folder_note")
public class Folder implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String nameFolder;
    private int countNote;
    private String timeLastEditFolder;
    private String title;
    private String content;
    private String timeLastEditNote;

    public Folder(String nameFolder, int countNote, String timeLastEditFolder, String title, String content, String timeLastEditNote) {
        this.nameFolder = nameFolder;
        this.countNote = countNote;
        this.timeLastEditFolder = timeLastEditFolder;
        this.title = title;
        this.content = content;
        this.timeLastEditNote = timeLastEditNote;
    }

    public Folder() {
    }

    public Folder(String nameFolder, String timeLastEditFolder) {
        this.nameFolder = nameFolder;
        this.timeLastEditFolder = timeLastEditFolder;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameFolder() {
        return nameFolder;
    }

    public void setNameFolder(String nameFolder) {
        this.nameFolder = nameFolder;
    }

    public int getCountNote() {
        return countNote;
    }

    public void setCountNote(int countNote) {
        this.countNote = countNote;
    }

    public String getTimeLastEditFolder() {
        return timeLastEditFolder;
    }

    public void setTimeLastEditFolder(String timeLastEditFolder) {
        this.timeLastEditFolder = timeLastEditFolder;
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

    public String getTimeLastEditNote() {
        return timeLastEditNote;
    }

    public void setTimeLastEditNote(String timeLastEditNote) {
        this.timeLastEditNote = timeLastEditNote;
    }
}
