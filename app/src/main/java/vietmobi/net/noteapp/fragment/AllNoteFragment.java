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
import vietmobi.net.noteapp.RecyclerViewInterface;
import vietmobi.net.noteapp.adapter.NoteAdapter;
import vietmobi.net.noteapp.database.NoteDatabase;
import vietmobi.net.noteapp.model.Note;

public class AllNoteFragment extends Fragment implements RecyclerViewInterface {
    RecyclerView rcvListAllNote;
    NoteAdapter noteAdapter;
    List<Note> listNote;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_note, container, false);
        initViews(view);
        configNote();
        return view;
    }

    private void initViews(View view) {
        rcvListAllNote = view.findViewById(R.id.rcvListAllNote);

    }

    public void configNote() {
        noteAdapter = new NoteAdapter(listNote, getContext());
        listNote = new ArrayList<>();

        loadData();

        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getActivity(), 2);
        rcvListAllNote.setLayoutManager(linearLayoutManager);
        rcvListAllNote.setAdapter(noteAdapter);
    }

    @Override
    public void loadFragment(Fragment fragment) {

    }

    public void loadData() {
        listNote = NoteDatabase.getInstance(getContext()).noteDAO().getListNote();
        noteAdapter.setData(listNote);
    }

}