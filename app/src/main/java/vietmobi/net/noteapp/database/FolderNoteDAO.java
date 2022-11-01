package vietmobi.net.noteapp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import vietmobi.net.noteapp.model.Folder;

@Dao
public interface FolderNoteDAO {

    @Insert
    void insertFolder(Folder folder);

    @Query("select * from folder_note")
    List<Folder> getListFolder();

    @Delete
    void deleteFolder(Folder folder);

    @Update
    void updateFolder(Folder folder);
}
