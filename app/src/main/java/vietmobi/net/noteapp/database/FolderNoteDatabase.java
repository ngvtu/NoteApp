package vietmobi.net.noteapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import vietmobi.net.noteapp.model.Folder;

@Database(entities = {Folder.class}, version = 1)
public abstract class FolderNoteDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "folder.db";
    private static FolderNoteDatabase instance;
//    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
//        @Override
//        public void migrate(@NonNull SupportSQLiteDatabase database) {
//            database.execSQL("create table if not exists folder_note ('id' integer, 'nameFolder' text, 'timeLastEditFolder' text)");
//        }
//    };

    public static synchronized FolderNoteDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), FolderNoteDatabase.class, DATABASE_NAME)
//                    .addMigrations(MIGRATION_1_2)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
    public abstract FolderNoteDAO folderNoteDAO();

}
