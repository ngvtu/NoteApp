package vietmobi.net.noteapp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import vietmobi.net.noteapp.model.Note;

@Dao
public interface NoteDAO {
    @Insert
    void insertNote(Note note);

    @Query("select * from all_note")
    List<Note> getListNote();

    @Query("select * from all_note where title = :title")
    List<Note> checkNote(String title);

    @Update
    void updateNote(Note note);

    @Delete
    void deleteNote(Note note);

    @Query("delete from all_note")
    void deleteAllNote();

    @Query("select * from all_note where title like '%' || :title || '%'")
    List<Note> findNote(String title);
}
