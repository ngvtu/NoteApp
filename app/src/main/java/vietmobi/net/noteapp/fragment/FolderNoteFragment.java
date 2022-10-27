package vietmobi.net.noteapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import vietmobi.net.noteapp.R;

public class FolderNoteFragment extends Fragment {
    RecyclerView rcvFolderNote;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_folder, container, false);

        rcvFolderNote = view.findViewById(R.id.rcvFolderNote);
        return view;
    }
}