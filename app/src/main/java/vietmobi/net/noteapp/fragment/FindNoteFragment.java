package vietmobi.net.noteapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vietmobi.net.noteapp.R;
import vietmobi.net.noteapp.activity.MainActivity;
import vietmobi.net.noteapp.adapter.NoteAdapter;
import vietmobi.net.noteapp.database.NoteDatabase;
import vietmobi.net.noteapp.model.Note;

public class FindNoteFragment extends Fragment {
    ImageView btnBack;
    EditText edtFindNote;
    RecyclerView rcvResultFind;

    NoteAdapter noteAdapter;
    List<Note> noteList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_find_note, container, false);
        initViews(view );


        addEvents();

        return view;
    }

    private void initViews(View view) {
        btnBack = view.findViewById(R.id.btnBack);
        edtFindNote = view.findViewById(R.id.edtFindNote);
        rcvResultFind = view.findViewById(R.id.rcvResultFind);
    }

    private void addEvents() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        edtFindNote.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    handleSearchNote();
                }
                return false;
            }
        });
    }

    private void handleSearchNote() {
        String strKeyWord = edtFindNote.getText().toString();
        noteAdapter = new NoteAdapter(noteList, getContext());
        noteList = new ArrayList<>();

        noteList = NoteDatabase.getInstance(getContext()).noteDAO().findNote(strKeyWord);
        noteAdapter.setData(noteList);

        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getActivity(), 2);
        rcvResultFind.setLayoutManager(linearLayoutManager);
        rcvResultFind.setAdapter(noteAdapter);
    }
}