package vietmobi.net.noteapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
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
    List<Note> listNote;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        initViews(view);
        addData();
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.e("Vietmobi", "Reload Fragment1");
        noteAdapter = new NoteAdapter(listNote, getContext());
        listNote = new ArrayList<>();
        listNote = NoteDatabase.getInstance(getContext()).noteDAO().getListNote();
        noteAdapter.setData(listNote);
    }

    @Override
    public void onResume() {
        super.onResume();
        addData();
    }

    public void addData() {
        noteAdapter = new NoteAdapter(listNote, getContext());
        listNote = new ArrayList<>();

        listNote = NoteDatabase.getInstance(getContext()).noteDAO().listFavoriteNote();
        noteAdapter.setData(listNote);

        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getActivity(), 2);
        rcvFavoriteNote.setLayoutManager(linearLayoutManager);
        rcvFavoriteNote.setAdapter(noteAdapter);
    }

    private void initViews(View view) {
        rcvFavoriteNote = view.findViewById(R.id.rcvFavoriteNote);
    }
}