package vietmobi.net.noteapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import vietmobi.net.noteapp.model.Folder;
import vietmobi.net.noteapp.model.Note;

@Database(entities = {Folder.class, Note.class}, version = 3)
public abstract class FolderNoteDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "note.db";
    private static FolderNoteDatabase instance;

    public static synchronized FolderNoteDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), FolderNoteDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
    public abstract FolderNoteDAO folderNoteDAO();

}
