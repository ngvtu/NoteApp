package vietmobi.net.noteapp.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "folder_note")
public class Folder implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String nameFolder;
    private String timeLastEditFolder;

    public Folder(String nameFolder, int countNote, String timeLastEditFolder, String title, String content, String timeLastEditNote) {
        this.nameFolder = nameFolder;
        this.timeLastEditFolder = timeLastEditFolder;
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


    public String getTimeLastEditFolder() {
        return timeLastEditFolder;
    }

    public void setTimeLastEditFolder(String timeLastEditFolder) {
        this.timeLastEditFolder = timeLastEditFolder;
    }
}
