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
import java.util.List;

import vietmobi.net.noteapp.R;
import vietmobi.net.noteapp.adapter.NoteAdapter;
import vietmobi.net.noteapp.database.NoteDatabase;
import vietmobi.net.noteapp.model.Note;

public class FavoriteFragment extends Fragment {
    RecyclerView rcvFavoriteNote;
    NoteAdapter noteAdapter;
    List<Note> noteList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        addEvents(view);
        addData();
        getNoteToEdit();
        return view;
    }

    private void getNoteToEdit() {

    }

    private void addData() {
        noteAdapter = new NoteAdapter(noteList, getContext());
        noteList = new ArrayList<>();

        noteList = NoteDatabase.getInstance(getContext()).noteDAO().listFavoriteNote();
        noteAdapter.setData(noteList);

        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getActivity(), 2);
        rcvFavoriteNote.setLayoutManager(linearLayoutManager);
        rcvFavoriteNote.setAdapter(noteAdapter);
    }

    private void addEvents(View view) {
        rcvFavoriteNote = view.findViewById(R.id.rcvFavoriteNote);
    }
}