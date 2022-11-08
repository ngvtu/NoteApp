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

import vietmobi.net.noteapp.ListenerChangeData;
import vietmobi.net.noteapp.R;
import vietmobi.net.noteapp.adapter.NoteAdapter;
import vietmobi.net.noteapp.database.NoteDatabase;
import vietmobi.net.noteapp.model.Note;

public class AllNoteFragment extends Fragment implements ListenerChangeData {
    RecyclerView rcvListAllNote;
    NoteAdapter noteAdapter;
    List<Note> listNote;
    Context context;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_note, container, false);
        rcvListAllNote = view.findViewById(R.id.rcvListAllNote);
        configNote();
        return view;
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onResume() {
        super.onResume();
        configNote();
    }

    public void configNote() {
        noteAdapter = new NoteAdapter(listNote, getContext());
        listNote = new ArrayList<>();
        listNote = NoteDatabase.getInstance(getContext()).noteDAO().getListNote();
        noteAdapter.setData(listNote);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getActivity(), 2);
        rcvListAllNote.setLayoutManager(linearLayoutManager);
        rcvListAllNote.setAdapter(noteAdapter);
    }
}