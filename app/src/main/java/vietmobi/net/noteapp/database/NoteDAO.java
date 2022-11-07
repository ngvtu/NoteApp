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

    @Query("select * from all_note where id = :id")
    Note getNote(int id);

    @Update
    void updateNote(Note note);

    @Delete
    void deleteNote(Note note);

    @Query("delete from all_note")
    void deleteAllNote();

    @Query("select * from all_note where (title like '%' || :title || '%') and isLocked = 0")
    List<Note> findNote(String title);

    @Query("select * from all_note where isFavorite = 1")
    List<Note> listFavoriteNote();

    @Query("select * from all_note where ofFolder = :id")
    List<Note> listNoteOfFolder(int id);

    @Query("select count(ofFolder) from all_note where ofFolder =:id")
    int getCountNoteOfFolder(int id);

    @Query("select * from all_note where isLocked = 1")
    List<Note> listNoteIsLocked();
}
