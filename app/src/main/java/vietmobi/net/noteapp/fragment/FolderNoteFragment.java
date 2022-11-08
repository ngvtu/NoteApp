package vietmobi.net.noteapp.fragment;

import android.content.Context;
import android.os.Bundle;
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
import vietmobi.net.noteapp.adapter.FolderAdapter;
import vietmobi.net.noteapp.database.FolderNoteDatabase;
import vietmobi.net.noteapp.model.Folder;

public class FolderNoteFragment extends Fragment {
    RecyclerView rcvFolderNote;
    FolderAdapter noteAdapter;
    List<Folder> listFolder;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_folder, container, false);

        rcvFolderNote = view.findViewById(R.id.rcvFolderNote);
        addData();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        addData();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    public void addData() {
        noteAdapter = new FolderAdapter(listFolder, getContext());
        listFolder = new ArrayList<>();

        listFolder = FolderNoteDatabase.getInstance(getContext()).folderNoteDAO().getListFolder();
        noteAdapter.setData(listFolder);

        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getActivity(), 2);
        rcvFolderNote.setLayoutManager(linearLayoutManager);
        rcvFolderNote.setAdapter(noteAdapter);
    }
}