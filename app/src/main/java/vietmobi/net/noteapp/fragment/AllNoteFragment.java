package vietmobi.net.noteapp.fragment;

import static vietmobi.net.noteapp.adapter.NoteAdapter.MY_REQUEST_CODE;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vietmobi.net.noteapp.R;
import vietmobi.net.noteapp.RecyclerViewInterface;
import vietmobi.net.noteapp.adapter.NoteAdapter;
import vietmobi.net.noteapp.database.NoteDatabase;
import vietmobi.net.noteapp.model.Note;

public class AllNoteFragment extends Fragment implements RecyclerViewInterface{
    RecyclerView rcvListAllNote;
    NoteAdapter noteAdapter;
    List<Note> listNote;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_note, container, false);
        initViews(view);
        configNote(view);
        return view;
    }

    private void initViews(View view) {
        rcvListAllNote = view.findViewById(R.id.rcvListAllNote);

    }

    private void configNote(View view) {
        noteAdapter = new NoteAdapter(listNote, view.getContext());
        listNote = new ArrayList<>();

        loadData(view);

        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getActivity(), 2);
        rcvListAllNote.setLayoutManager(linearLayoutManager);
        rcvListAllNote.setAdapter(noteAdapter);

    }

    private void loadData(View view) {
        listNote = NoteDatabase.getInstance(view.getContext()).noteDAO().getListNote();
        noteAdapter.setData(listNote);
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onItemLongClick(int position) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data, View view) {
        super.onActivityResult(requestCode, resultCode, data);
            loadData(view);
    }

}