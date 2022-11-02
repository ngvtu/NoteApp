package vietmobi.net.noteapp.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vietmobi.net.noteapp.R;
import vietmobi.net.noteapp.adapter.NoteAdapter;
import vietmobi.net.noteapp.database.NoteDatabase;
import vietmobi.net.noteapp.model.Note;

public class NoteOfFolderActivity extends AppCompatActivity {
    RecyclerView rcvListAllNote;
    TextView tvNameFolder;
    NoteAdapter noteAdapter;
    List<Note> listNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_of_folder);

        initViews();
        addEvents();
        configNote();
    }

    private void configNote() {
        noteAdapter = new NoteAdapter(listNote, this);
        listNote = new ArrayList<>();

        tvNameFolder.getText().toString();
        listNote = NoteDatabase.getInstance(this).noteDAO().listNoteOfFolder(1);
        noteAdapter.setData(listNote);

        LinearLayoutManager linearLayoutManager = new GridLayoutManager(this, 2);
        rcvListAllNote.setLayoutManager(linearLayoutManager);
        rcvListAllNote.setAdapter(noteAdapter);
    }

    private void addEvents() {

    }

    private void initViews() {
        rcvListAllNote = findViewById(R.id.rcvListAllNote);
        tvNameFolder = findViewById(R.id.tvNameFolder);
    }

}