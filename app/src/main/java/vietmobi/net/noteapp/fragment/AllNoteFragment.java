package vietmobi.net.noteapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import vietmobi.net.noteapp.R;
import vietmobi.net.noteapp.adapter.NoteAdapter;
import vietmobi.net.noteapp.model.Note;

public class AllNoteFragment extends Fragment {
    RecyclerView rcvListAllNote;
    NoteAdapter noteAdapter;
    ArrayList<Note> listNote;
    String title, content, time;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_note, container, false);
        initViews(view);
        addNote();
        return view;
    }

    private void initViews(View view) {
        rcvListAllNote = view.findViewById(R.id.rcvListAllNote);

    }

    private void addNote() {
        listNote = new ArrayList<>();
        listNote.add(new Note("Test","Aaaaaaaaaaaaaa","12/12/2001"));
        listNote.add(new Note("Test","Aaaaaaaaaaaaaa","12/12/2001"));
        listNote.add(new Note("Test","Aaaaaaaaaaaaaa","12/12/2001"));
        listNote.add(new Note("Test","Aaaaaaaaaaaaaa","12/12/2001"));
        listNote.add(new Note("Test","Aaaaaaaaaaaaaa","12/12/2001"));
        listNote.add(new Note("Test","Aaaaaaaaaaaaaa","12/12/2001"));
        listNote.add(new Note("Test","AaaaaaaaaaaaaaAaaaaaaaaaaaaaAaaaaaaaaaaaaaAaaaaaaaaaaaaaAaaaaaaaaaaaaaAaaaaaaaaaaaaaAaaaaaaaaaaaaaAaaaaaaaaaaaaaAaaaaaaaaaaaaa","12/12/2001"));listNote.add(new Note("Test","Aaaaaaaaaaaaaa","12/12/2001"));
        listNote.add(new Note("Test","AaaaaaaaaaaaaaAaaaaaaaaaaaaaAaaaaaaaaaaaaaAaaaaaaaaaaaaaAaaaaaaaaaaaaaAaaaaaaaaaaaaaAaaaaaaaaaaaaaAaaaaaaaaaaaaaAaaaaaaaaaaaaa","12/12/2001"));listNote.add(new Note("Test","Aaaaaaaaaaaaaa","12/12/2001"));
        listNote.add(new Note("Test","AaaaaaaaaaaaaaAaaaaaaaaaaaaaAaaaaaaaaaaaaaAaaaaaaaaaaaaaAaaaaaaaaaaaaaAaaaaaaaaaaaaaAaaaaaaaaaaaaaAaaaaaaaaaaaaaAaaaaaaaaaaaaa","12/12/2001"));listNote.add(new Note("Test","Aaaaaaaaaaaaaa","12/12/2001"));
        listNote.add(new Note("Test","AaaaaaaaaaaaaaAaaaaaaaaaaaaaAaaaaaaaaaaaaaAaaaaaaaaaaaaaAaaaaaaaaaaaaaAaaaaaaaaaaaaaAaaaaaaaaaaaaaAaaaaaaaaaaaaaAaaaaaaaaaaaaa","12/12/2001"));
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getActivity(), 2);

        noteAdapter = new NoteAdapter(listNote, getActivity());
        rcvListAllNote.setAdapter(noteAdapter);
        rcvListAllNote.setLayoutManager(linearLayoutManager);
    }
}