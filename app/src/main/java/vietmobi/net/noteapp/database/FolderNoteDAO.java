package vietmobi.net.noteapp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import vietmobi.net.noteapp.model.Folder;
import vietmobi.net.noteapp.model.Note;

@Dao
public interface FolderNoteDAO {

    @Insert
    void insertFolder(Folder folder);

    @Query("select * from folder_note")
    List<Folder> getListNote();

    @Delete
    void deleteFolder(Folder folder);

    @Update
    void updateNote(Note note);
}
